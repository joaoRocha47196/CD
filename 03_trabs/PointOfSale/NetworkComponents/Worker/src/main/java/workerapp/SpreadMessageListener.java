package workerapp;

import spread.*;
import spread.*;

public class SpreadMessageListener implements BasicMessageListener {
    private SpreadConnection connection;
    private SpreadGroup group;
    private int workerId; // This server's worker ID
    private boolean isLeader;

    public SpreadMessageListener(SpreadConnection connection, int workerId) {
        this.workerId = workerId;
        this.isLeader = false;
        this.connection = connection;
    }

    // Method to start an election process
    private void startElection() {
        SpreadMessage electionMessage = new SpreadMessage();
        electionMessage.setSafe();
        electionMessage.setData("ELECTION".getBytes());

        try {
            connection.multicast(electionMessage);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    // Implementing BasicMessageListener's messageReceived method
    @Override
    public void messageReceived(SpreadMessage spreadMessage) {
        byte[] data = spreadMessage.getData();
        String receivedMessage = new String(data);

       if (receivedMessage.equals("ELECTION")) {
            // Respond to election message and handle leader election logic
            // Logic for Bully Algorithm: compare worker ID and decide leadership
            int receivedWorkerId = 1;//spreadMessage.getSender().getPrivateAddress().hashCode();

            // If this worker has a higher ID, it can become the leader
            if (workerId > receivedWorkerId) {
                // Announce leadership
                SpreadMessage leaderAnnouncement = new SpreadMessage();
                leaderAnnouncement.setSafe();
                leaderAnnouncement.setData("LEADER".getBytes());
                try {
                    connection.multicast(leaderAnnouncement);
                } catch (SpreadException e) {
                    e.printStackTrace();
                }
            }
            // Handle other cases as needed for your specific leader election logic
        } else if (receivedMessage.equals("LEADER")) {
            // Handle the case where this server received a LEADER announcement
            // Possibly update internal state to acknowledge the leader
            // Set isLeader flag or perform leader-related tasks
        }else {
            // NORMAL MESSAGE, LIKE SENT BY ManageServer
            // Start the election process
            startElection();
        }
    }

    // Method to stop the server
    public void stopServer() {
        try {
            connection.remove(this);
            group.leave();
            connection.disconnect();
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int workerId = 123; // Unique ID for this worker
        //ManagedServer server = new ManagedServer(workerId);
        // Other logic...
        // server.stopServer(); // Stop the server when done
    }


}
