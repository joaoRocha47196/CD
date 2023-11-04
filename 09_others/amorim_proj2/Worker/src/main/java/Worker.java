import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import spread.SpreadConnection;
import spread.SpreadGroup;

import java.net.InetAddress;

public class Worker {
    private static int spreadPort = 4803;
    private final static String GROUP_NAME = "workers";

    public static void main(String[] args) {
        try {

            if (args.length < 5) {
                System.out.println("Need more parameters:  Pubsub IP, Pubsub Port, Queue Name, Worker Name, Spread IP" +
                        " and (optional)SpreadPort.");
                return;
            }

            String pubsubIP = args[0];
            int pubsubPort = Integer.parseInt(args[1]);
            String queueName = args[2];
            String workerName = args[3];
            String spreadIP = args[4];

            if (args.length == 6) {
                spreadPort = Integer.parseInt(args[5]);
            }

            SpreadConnection spreadConnection = new SpreadConnection();
            spreadConnection.connect(InetAddress.getByName(spreadIP), spreadPort, workerName,
                false, true);
            SpreadGroup spreadGroup = new SpreadGroup();
            spreadGroup.join(spreadConnection, GROUP_NAME);

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(pubsubIP);
            factory.setPort(pubsubPort);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Consumer handler to receive a cancel receiving messages
            WorkerCallbackCancel cancelCallback = new WorkerCallbackCancel();

            // sem autoAck
            WorkerCallback deliverCallback = new WorkerCallback(channel, spreadConnection);

            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

            Thread.currentThread().join();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
