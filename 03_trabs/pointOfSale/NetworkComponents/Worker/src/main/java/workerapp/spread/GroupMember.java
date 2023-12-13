package workerapp.spread;

import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GroupMember {
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";

    private SpreadConnection connection;

    public GroupMember(String spreadIP, int spreadPort, String workerName) {
        try  {
            this.connection = new SpreadConnection();
            connection.connect(InetAddress.getByName(spreadIP), spreadPort, workerName, false, true);
            connection.add(new SpreadMessageListener(connection));
        }
        catch(SpreadException e)  {
            System.err.println("There was an error connecting to the daemon.");
            e.printStackTrace();
        }
        catch(UnknownHostException e) {
            System.err.println("Can't find the daemon " + spreadIP);
            e.printStackTrace();
        }
    }

    public void joinGroup(){
        try {
            SpreadGroup group = new SpreadGroup();
            group.join(connection, SPREAD_GROUP_NAME);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }
}
