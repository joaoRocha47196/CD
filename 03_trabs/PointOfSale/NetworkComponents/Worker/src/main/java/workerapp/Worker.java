package workerapp.Worker;

import com.rabbitmq.client.*;
import org.spread.core.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Worker {
    private static final String RABBITMQ_DEFAULT_HOST = "localhost"; // Update with your RabbitMQ host
    private static final int RABBITMQ_DEFAULT_PORT = 5672; // Update with your RabbitMQ port
    private static final String QUEUE_NAME_ALIMENTAR = "QueueAlimentar";
    private static final String QUEUE_NAME_CASA = "QueueCasa";
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";
    private static final String GLUSTER_DIRECTORY_PATH = "/path/to/gluster/directory/";

    public static void main(String[] args) {
        initRabbitMQConnection();
        initSpreadGroup();

        // Register the callback for Spread group
        WorkerSpreadListener workerSpreadListener = new WorkerSpreadListener();
        SpreadGroup spreadGroup = new SpreadGroup();
        spreadGroup.join(workerSpreadListener, SPREAD_GROUP_NAME);

        // Start consuming messages from RabbitMQ queues
        consumeMessages(QUEUE_NAME_ALIMENTAR);
        consumeMessages(QUEUE_NAME_CASA);
    }

    static void initRabbitMQConnection() {
        // Similar to the PointOfSaleApp
    }

    static void initSpreadGroup() {
        // Initialize Spread group
    }

    static void consumeMessages(String queueName) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBITMQ_DEFAULT_HOST);
            factory.setPort(RABBITMQ_DEFAULT_PORT);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                // Process the sale message and write it to a file
                processAndWriteToFile(queueName, message);
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ");
            e.printStackTrace();
        }
    }

    static void processAndWriteToFile(String queueName, String saleMessage) {
        // Process the sale message
        // Extract relevant information and write it to a file

        String fileName = GLUSTER_DIRECTORY_PATH + "f_" + queueName.toLowerCase() + ".txt";

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(saleMessage + "\n");
            System.out.println(" [x] Sale information written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}

class WorkerSpreadListener implements SpreadMessageListener {
    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        // Handle multicast messages received in the Spread group
        // You may implement specific logic based on your requirements
    }
}
