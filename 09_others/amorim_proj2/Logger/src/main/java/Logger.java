import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class Logger {

    public static void main(String[] args) {
        try {
            if(args.length < 3){
                System.out.println("Need more parameters: Pubsub IP, Pubsub Port and Queue Name");
                return;
            }

            String IP = args[0];
            int pubsubPort = Integer.parseInt(args[1]);
            String queueName = args[2];

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(IP);
            factory.setPort(pubsubPort);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            LoggerCallback clientCallback = new LoggerCallback(channel);
            LoggerCallbackCancel clientCancel = new LoggerCallbackCancel();

            channel.basicConsume(queueName, false, clientCallback, clientCancel);

            Scanner in = new Scanner(System.in);
            in.nextLine();

            channel.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
