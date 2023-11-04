import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import spread.SpreadConnection;
import spread.SpreadException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

public class Courier {
    private CourierMessageHandling msgHandling;
    public Repo repo;

    public Courier(String user, String spreadIP, int spreadPort, String pubsubIP, int pubsubPort) {
        try  {
            Gson gson = new GsonBuilder().create();

            // PubSub
            ConnectionFactory factory = new ConnectionFactory();
            factory.setPort(pubsubPort);
            factory.setHost(pubsubIP);
            Connection pubsubConnection = factory.newConnection();
            Channel pubsubChannel = pubsubConnection.createChannel();

            ResponseSender pubsubSender = new ResponseSender(pubsubChannel, gson);

            // Spread
            SpreadConnection spreadConnection = new SpreadConnection();
            spreadConnection.connect(InetAddress.getByName(spreadIP), spreadPort, user,
                    false, true);

            // repo
            repo = new Repo(spreadConnection, pubsubSender, gson);

            // Spread handler
            msgHandling = new CourierMessageHandling(repo);
            spreadConnection.add(msgHandling);
        }
        catch(SpreadException e)  {
            System.err.println("There was an error connecting to the daemon.");
            e.printStackTrace();
            System.exit(1);
        }
        catch(UnknownHostException e) {
            System.err.println("Can't find the daemon " + spreadIP);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Can't connect to Pubsub " + pubsubIP);
            System.exit(1);
        }
        catch (TimeoutException e) {
            System.err.println("Can't find the PubSub " + pubsubIP);
            System.exit(1);
        }
    }

    public void close() throws SpreadException {
        // remove listener
        repo.connection.remove(msgHandling);
        // Disconnect.
        repo.connection.disconnect();
    }
}
