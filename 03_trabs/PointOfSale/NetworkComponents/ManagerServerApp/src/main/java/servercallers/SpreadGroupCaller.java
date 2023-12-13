package servercallers;

import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpreadGroupCaller {

    private SpreadConnection connection;
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";
    private static final String SPREAD_PRIVATE_NAME = "worker_username";

    public SpreadGroupCaller(int spreadGroupPort, String spreadGroupIp) {
        connection = new SpreadConnection();
        try {
            connection.connect(InetAddress.getByName(spreadGroupIp), spreadGroupPort, SPREAD_PRIVATE_NAME, false, true);
        } catch (SpreadException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendResumeRequest(String exchangeName, String productType, String filename) {
        try {
            SpreadMessage spreadMessage = new SpreadMessage();
            spreadMessage.setSafe();
            spreadMessage.addGroup(SPREAD_GROUP_NAME);

            spreadMessage.setObject(new ResumoInfo(exchangeName, productType, filename));
            sendMulticast(spreadMessage);
        } catch (SpreadException e) {
            System.err.println("Error sending multicast message to Spread Group: " + e.getMessage());
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