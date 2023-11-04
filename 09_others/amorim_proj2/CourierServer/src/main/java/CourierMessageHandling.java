import rpcCourierStub.Work;
import spread.*;

import java.nio.charset.StandardCharsets;

public class CourierMessageHandling implements AdvancedMessageListener {
    private boolean firstRun;
    private final Repo repo;

    public CourierMessageHandling(Repo repo) {
        System.out.println("my private group = " + repo.connection.getPrivateGroup());
        this.firstRun = true;
        this.repo = repo;
    }

    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        // get data into object
        String data = new String(spreadMessage.getData(), StandardCharsets.UTF_8);
        Message message = repo.gson.fromJson(data, Message.class);

        if (message.isRequest()) {
            System.out.println("Handling request");
            repo.addRequest(message.request);
            if (!repo.isBusy() && repo.canCandidate) {
                repo.canCandidate = false;
                repo.election(message.request.requestID);
            }
        } else if (message.isLeader()) {
            Request req = repo.removeRequest(message.requestID);
            if (sameGroup(spreadMessage.getSender(), repo.connection.getPrivateGroup())) {
                if (req != null) {
                    repo.setBusy();

                    Work work = Work.newBuilder()
                            .setAddressSrc(req.addressSrc)
                            .setAddressDest(req.addressDest)
                            .build();

                    repo.client.onNext(work);

                    repo.responseSender.send(req.exchangeName, req.requestID);
                } else {
                    String requestID = repo.getPendingRequestID();
                    if (requestID != null) {
                        repo.election(requestID);
                    } else {
                        repo.canCandidate = true;
                    }
                }
            }
        }else if (message.isJoinResponse()) {
            if (firstRun) {
                System.out.println("Getting pending requests");
                repo.addRequests(message.pendingRequests);
                String requestID = repo.getPendingRequestID();
                if (requestID != null) {
                    repo.election(requestID);
                }
                firstRun = false;
            }
        }
    }

    @Override
    public void membershipMessageReceived(SpreadMessage spreadMessage) {
        MembershipInfo info = spreadMessage.getMembershipInfo();
        SpreadGroup group = info.getGroup();

        if(info.isRegularMembership()) {
            SpreadGroup[] members = info.getMembers();
            System.out.println("REGULAR membership for group " + group +
                    " with " + members.length + " members:");
            // a possible lake of information
            if (info.isCausedByJoin()) {
                System.out.println("JOIN of " + info.getJoined());

                Message message = new Message();
                message.type = MessageType.JOIN_RESPONSE;
                message.pendingRequests = repo.getPendingRequests();

                SpreadMessage msg = repo.getSpreadMessage(info.getJoined(), message);

                try {
                    repo.connection.multicast(msg);
                } catch (SpreadException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean sameGroup(SpreadGroup group1, SpreadGroup group2) {
        return group1.toString().equals(group2.toString());
    }
}

