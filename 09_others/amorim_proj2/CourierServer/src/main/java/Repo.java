import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import rpcCourierStub.Work;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Repo {

    private final LinkedList<Request> pendingRequests = new LinkedList<>();
    private final HashMap<String, Request> mappingRequests = new HashMap<>();

    public SpreadGroup group = new SpreadGroup();
    public SpreadConnection connection;

    public ResponseSender responseSender;

    public final Gson gson;

    private final Object busyLock = new Object();
    private boolean busy = false;
    public boolean canCandidate = true;

    public StreamObserver<Work> client;

    public Repo(SpreadConnection spreadConnection, ResponseSender responseSender, Gson gson) {
        this.responseSender = responseSender;
        this.connection = spreadConnection;
        this.gson = gson;
    }

    public synchronized void addRequest(Request req) {
        mappingRequests.put(req.requestID, req);
        pendingRequests.add(req);
    }


    public synchronized void addRequests(List<Request> reqs) {
        pendingRequests.addAll(reqs);
        for (Request req : reqs) {
            mappingRequests.put(req.requestID, req);
        }
    }

    public synchronized Request removeRequest(String requestID) {
        Request req = mappingRequests.remove(requestID);
        if (req == null)
            return null;
        pendingRequests.remove(req);
        return req;
    }

    public synchronized List<Request> getPendingRequests() {
        return pendingRequests;
    }

    public synchronized String getPendingRequestID() {
        return pendingRequests.isEmpty() ? null : pendingRequests.getFirst().requestID;
    }


    public SpreadMessage getSpreadMessage(SpreadGroup group, Message message) {
        String jsonString = gson.toJson(message);

        SpreadMessage msg = new SpreadMessage();
        msg.setSafe();
        msg.addGroup(group);
        msg.setData(jsonString.getBytes(StandardCharsets.UTF_8));
        return msg;
    }

    public void setBusy() {
        synchronized (busyLock) {
            busy = true;
        }
        canCandidate = false;
    }

    public void setFree() {
        synchronized (busyLock) {
            busy = false;
        }
        canCandidate = true;
    }

    public boolean isBusy() {
        synchronized (busyLock) {
            return busy;
        }
    }

    public void election(String requestID) {
        Message message = new Message();
        message.type = MessageType.LEADER;
        message.requestID = requestID;

        SpreadMessage msg = getSpreadMessage(group, message);

        try {
            long time = System.currentTimeMillis() + (long)(Math.random() * 250);
            while (time > System.currentTimeMillis())
                Thread.yield();
            connection.multicast(msg);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
        System.out.println("election");
    }
}

