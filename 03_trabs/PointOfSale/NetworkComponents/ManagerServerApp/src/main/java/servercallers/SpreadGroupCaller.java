package servercallers;

import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

public class SpreadGroupCaller {

    private static int spreadGroupPort;
    private static String spreadGroupIp; // "35.246.73.129";
    private SpreadConnection connection;
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";

    public SpreadGroupCaller(int spreadGroupPort, String spreadGroupIp) {
        this.spreadGroupPort = spreadGroupPort;
        this.spreadGroupIp = spreadGroupIp;
        connection = new SpreadConnection();
        SpreadGroup group = new SpreadGroup();
        //group.join(connection, SPREAD_GROUP_NAME);
    }



    public void sendMulticast(SpreadMessage message) throws SpreadException {
        if (connection.isConnected()) {
            connection.multicast(message);
        } else {
            System.err.println("Not connected to Spread group");
        }
    }

}