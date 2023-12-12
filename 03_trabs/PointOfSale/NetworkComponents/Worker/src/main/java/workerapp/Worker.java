package workerapp;

import com.rabbitmq.client.*;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Worker {
    private static final String RABBITMQ_DEFAULT_HOST = "localhost";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";
    private static final String GLUSTER_DIRECTORY_PATH = "/path/to/gluster/directory/";
    private static final String EXCHANGE_NAME = "ExgSales";
    private static final int SPREAD_PORT = 4803;


    private static String rabbitMQHost;
    private static int rabbitMQPort;
    private static String spreadIP;
    private static String queueName;
    private static Channel rabbitChannel;

    public static void main(String[] args) {
        initConnections(args);
        createGlusterDirectories();
        initRabbitMQConnection();
        initSpreadGroup();

        if (queueName.equals("ALIMENTAR"))
            consumeMessages("QueueAlimentar", "ALIMENTAR");
        else
            consumeMessages("QueueCasa", "CASA");
    }

    private static void initConnections(String[] args) {
        if (args.length == 3) {
            rabbitMQHost = args[0];
            rabbitMQPort = Integer.parseInt(args[1]);
            spreadIP = args[2];
            queueName = args[3];
        } else {
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
            spreadIP = "";
        }
    }

    private static void initRabbitMQConnection() {
        System.out.println("Connect to RabbitMQ server at:" + rabbitMQHost + ":" + rabbitMQPort);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);

        try {
            Connection connection = factory.newConnection();
            rabbitChannel = connection.createChannel();
            System.out.println("Connected to RabbitMQ successfully!");

        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ" + e.getMessage());
        }
    }

    private static void initSpreadGroup() {
        try {
            SpreadConnection connection = new SpreadConnection();
            connection.connect(InetAddress.getByName(spreadIP), SPREAD_PORT, "worker_username", false, true);

            SpreadGroup group = new SpreadGroup();
            group.join(connection, SPREAD_GROUP_NAME);

            connection.add(new SpreadMessageListener());

            System.out.println("Connected to Spread group successfully!");
        } catch (SpreadException e) {
            System.out.println("Error connecting to Spread group: " + e.getMessage());
        } catch (java.net.UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void consumeMessages(String queueName, String routingKey) {
        try {
            rabbitChannel.queueDeclare(queueName, true, false, false, null);
            rabbitChannel.queueBind(queueName, EXCHANGE_NAME, routingKey);
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            WorkerCallback workerCallback = new WorkerCallback(queueName);
            WorkerCallbackCancel cancelCallback = new WorkerCallbackCancel();

            rabbitChannel.basicConsume(queueName, true, workerCallback, cancelCallback);
        } catch (IOException e) {
            System.out.println("Error connecting to RabbitMQ");
        }
    }

    /*
    private static void processAndWriteToFile(String queueName, String saleMessage) {
        String queueType = queueName.toLowerCase();
        String fileName = GLUSTER_DIRECTORY_PATH + queueType + "/f_" + queueType + ".txt";

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(saleMessage + "\n");
            System.out.println(" [x] Sale information written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
    */


    private static void createGlusterDirectories() {
        createGlusterDirectory("alimentar");
        createGlusterDirectory("casa");
    }

    private static void createGlusterDirectory(String queueType) {
        String directoryPath = GLUSTER_DIRECTORY_PATH + queueType;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs())
                System.out.println("Created Gluster directory: " + directoryPath);
            else
                System.out.println("Failed to create Gluster directory: " + directoryPath);
        }
    }
}
