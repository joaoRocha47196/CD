import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RabbitConfigurator {

    static Connection connection = null;
    static Channel channel = null;

    public static void main(String[] args) {

        try {
            if (args.length < 5){
                System.out.println("Needs more parameters: Pubsub IP, Pubsub Port, Global Exchange Name, " +
                        "Logger Queue Name and Workers Queue Name");
            }

            String IP = args[0];
            int porto = Integer.parseInt(args[1]);
            String nomeExchangeGlobal = args[2];
            String loggerQueueName = args[3];
            String workersQueueName = args[4];

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(IP);
            factory.setPort(porto);

            connection = factory.newConnection();
            channel = connection.createChannel();

            CreateExchangeWithAlternateExchange(nomeExchangeGlobal, loggerQueueName, workersQueueName);

            channel.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void CreateExchangeWithAlternateExchange(String exchangeName, String loggerQueueName,
                                                    String workersQueueName) throws IOException {
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
        channel.queueDeclare(loggerQueueName, true, false, false, null);
        channel.queueBind(loggerQueueName, exchangeName, "");
        channel.queueDeclare(workersQueueName, true, false, false, null);
        channel.queueBind(workersQueueName, exchangeName, "");
    }

}
