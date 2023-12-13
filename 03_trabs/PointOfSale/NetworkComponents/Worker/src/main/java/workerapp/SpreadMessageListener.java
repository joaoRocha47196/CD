package workerapp;

import com.rabbitmq.client.Channel;
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

    @Override
    public void messageReceived(SpreadMessage spreadMessage) {
        byte[] data = spreadMessage.getData();
        String receivedMessage = new String(data);

        if (receivedMessage.equals("ELECTION")) {
            // Give a negative acknowledgment to stop processing requests -- ???
            //TODO
            // Respond to election message and handle leader election logic
            // Logic for Bully Algorithm: compare worker ID and decide leadership
            int receivedWorkerId = 1;//spreadMessage.getSender().getPrivateAddress().hashCode();

            // If this worker has a higher ID, it can become the leader
            //DAR UM NEGATIVE ACKNOWLEGE PARA O WORKER PARA DE PROCESSAR MENSAGENS
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
            isLeader = true;
            processAndWriteSummary();
            // Handle the case where this server received a LEADER announcement
            // Possibly update internal state to acknowledge the leader
            // Set isLeader flag or perform leader-related tasks
        } else {
            // NORMAL MESSAGE, LIKE SENT BY ManageServer
            // Start the election process
            startElection();
        }
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

    // Method to process and write the summary when this worker is the leader
    private void processAndWriteSummary() {
        // Implement logic to process and write the summary file
        // This could involve reading files from other workers and combining them into one file
        // Once the summary is ready, notify the Exchange (send a message with the summary file information)

        SpreadMessage summaryMessage = new SpreadMessage();
        summaryMessage.setSafe();
        summaryMessage.setData("SUMMARY_FILE_NAME".getBytes());  // Replace with the actual summary file name

        try {
            connection.multicast(summaryMessage);
        } catch (SpreadException e) {
            e.printStackTrace();
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
