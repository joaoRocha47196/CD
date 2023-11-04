package serverapp;

import io.grpc.stub.StreamObserver;
import rpcClientStub.Value;
import rpcKvServerStub.Pair;
import rpcRegisterStub.RegisterServiceGrpc;

import java.util.HashMap;
import java.util.LinkedList;

public class Repo {
    public static class ServerInfo {
        public String ip;
        public int port;

        public ServerInfo(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }

    public ServerInfo nextServer;
    public ServerInfo server;
    public ServerInfo redis;
    public String serverId;

    public RegisterServiceGrpc.RegisterServiceBlockingStub ringManagerServiceBlockingStub;
    public StreamObserver<Pair> nextWrite;
    public StreamObserver<Pair> nextRead;

    private final HashMap<String, LinkedList<StreamObserver<Value>>> pendingRequests = new HashMap<>();

    public synchronized void addRequest(String key, StreamObserver<Value> waiter) {
        if (pendingRequests.containsKey(key))
            pendingRequests.get(key).add(waiter);
        else
            pendingRequests.put(key, new LinkedList<>(){{add(waiter);}});
    }

    public synchronized LinkedList<StreamObserver<Value>> removeRequest(String key) {
        return pendingRequests.remove(key);
    }
}
