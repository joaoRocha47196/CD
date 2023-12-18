package workerapp.spread;

import com.google.gson.Gson;
import spread.*;
import workerapp.gluster.GlusterFileManager;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static workerapp.Worker.sendNotification;

public class SpreadMessageListener implements BasicMessageListener {
    private SpreadConnection connection;
    private boolean isLeader = false;
    private int id = -1;
    private String exchangeName;
    private String type;
    private String fileName;
    private Set<SpreadGroup> groupMembers = new HashSet<>();

    private static final String SPREAD_GROUP_NAME = "SalesWorkers";


    public SpreadMessageListener(SpreadConnection connection) {
        this.connection = connection;
    }

    @Override
    public void messageReceived(SpreadMessage spreadMessage) {
        System.out.println("Received message from Spread Group: " + spreadMessage.getSender());
        if (spreadMessage.isRegular()) {

            String jsonString = new String(spreadMessage.getData(), StandardCharsets.UTF_8);
            CommonMessage commonMessage = new Gson().fromJson(jsonString, CommonMessage.class);

            if ("ResumoInfo".equals(commonMessage.getType())) {
                System.out.println("ResumeInfo message from SpreadGroup Received");
                // TODO somehow stop all the workers
                ResumoInfo resumoInfo = new Gson().fromJson(commonMessage.getData(), ResumoInfo.class);
                System.out.println("ResumoInfo: " + resumoInfo.toString());
                processResumeMessage(resumoInfo);

                // Election Multicast Message
            } else if ("ElectionInfo".equals(commonMessage.getType())) {
                System.out.println("ElectionInfo message from SpreadGroup Received");
                ElectionInfo electionInfo = new Gson().fromJson(commonMessage.getData(), ElectionInfo.class);
                System.out.println("ElectionInfo: " + electionInfo.toString());
                processElectionMessage(electionInfo);
            }

            // Merge All Files
            if (isLeader) {
                System.out.println("Leader is merging files");
                GlusterFileManager.mergeFilesByRoutingKey(this.type, this.fileName);
                sendNotification(this.exchangeName, this.fileName);
            }
        } else if (spreadMessage.isMembership()) {
            MembershipInfo membershipInfo = spreadMessage.getMembershipInfo();
            if (membershipInfo.isRegularMembership()) {
                SpreadGroup[] members = membershipInfo.getMembers();
                System.out.println("Group Members: " + members.length);

                // Update group membership information
                groupMembers.clear();
                groupMembers.addAll(Arrays.asList(members));
            }
        }
    }


    private void processResumeMessage(ResumoInfo retrievedObject) {
        this.exchangeName = retrievedObject.getExchangeName();
        this.type = retrievedObject.getProductType();
        this.fileName = retrievedObject.getFileName();

        SpreadGroup[] members = groupMembers.toArray(new SpreadGroup[0]);
        System.out.println("Group Members: " + members.length);
        findMemberWithHighestId(members);
        sendElectionMessage();
    }

    private void findMemberWithHighestId(SpreadGroup[] members){ //choose random
        for (SpreadGroup member : members) {
            int currentID = member.hashCode();
            if (currentID > this.id)
                this.id = currentID;
        }
    }

    public void sendElectionMessage() {
        try {
            SpreadMessage spreadMessage = new SpreadMessage();
            spreadMessage.setSafe();
            spreadMessage.addGroup(SPREAD_GROUP_NAME);

            CommonMessage commonMessage = new CommonMessage("ElectionInfo", new ElectionInfo(this.id).toString());
            String commonMessageJson = new Gson().toJson(commonMessage);
            spreadMessage.setData(commonMessageJson.getBytes(StandardCharsets.UTF_8));

            sendMulticast(spreadMessage);

        } catch (SpreadException e) {
            System.err.println("Error sending multicast message to Spread Group: " + e.getMessage());
        }
    }

    private void processElectionMessage(ElectionInfo retrievedObject) {
        int receivedLeaderId = retrievedObject.getId();
        System.out.println("Received Leader ID: " + receivedLeaderId);

        if (receivedLeaderId == this.id) {
            isLeader = true;
            System.out.println("Este worker é o líder.");
        } else {
            isLeader = false;
            System.out.println("Este worker não é o líder.");
        }
    }

    public void sendMulticast(SpreadMessage message) throws SpreadException {
        if (connection.isConnected()) {
            connection.multicast(message);
        } else {
            System.err.println("Not connected to Spread group");
        }
    }
}
