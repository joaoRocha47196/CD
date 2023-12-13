package workerapp;

import com.rabbitmq.client.*;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

public class Worker {
    // RABBIT
    private static final String RABBITMQ_DEFAULT_HOST = "34.28.226.254";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static final String EXCHANGE_NAME = "ExgSales";
    private static String rabbitMQHost;
    private static int rabbitMQPort;

    // SPREAD
    private static final String SPREAD_GROUP_NAME = "SalesWorkers";
    private static final int SPREAD_PORT = 4803;
    private static String spreadIP;
    private static String routingKey;
    private static String workerName;

    // GLUSTER
    private static final String GLUSTER_DIRECTORY_PATH = "/var/sharedfiles";


    public static void main(String[] args) throws IOException {
        initConnections(args);
        //createGlusterDirectories();
        initSpreadGroup();
        Channel rabbitChannel = initRabbitMQConnection();
        rabbitconfig(rabbitChannel);
    }

    private static void rabbitconfig( Channel rabbitChannel) throws IOException {
        if (routingKey.equals("ALIMENTAR")){
            rabbitChannel.queueDeclare("QueueAlimentar", true, false, false, null);
            rabbitChannel.queueBind("QueueAlimentar", EXCHANGE_NAME, routingKey);
            consumeMessages(rabbitChannel,"QueueAlimentar");
        }
        else {
            rabbitChannel.queueDeclare("QueueCasa", true, false, false, null);
            rabbitChannel.queueBind("QueueCasa", EXCHANGE_NAME, routingKey);
            consumeMessages(rabbitChannel,"QueueCasa");
        }
    }

    private static void initConnections(String[] args) {
        if (args.length == 3) {
            rabbitMQHost = args[0];
            rabbitMQPort = Integer.parseInt(args[1]);
            routingKey = args[2];
            //spreadIP = args[3];
            workerName = args[4];
        } else {
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
            routingKey = "ALIMENTAR";
            spreadIP = "";
            workerName = "worker1";
        }
    }

    private static Channel initRabbitMQConnection() {
        System.out.println("Connect to RabbitMQ server at:" + rabbitMQHost + ":" + rabbitMQPort);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);
        factory.setUsername("rabbit");
        factory.setPassword("rabbit");

        try {
            Connection connection = factory.newConnection();
            Channel rabbitChannel = connection.createChannel();
            System.out.println("Connected to RabbitMQ successfully!");
            return rabbitChannel;

        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ" + e.getMessage());
            return null;
        }
    }

    private static void initSpreadGroup() {
        try {
            SpreadConnection connection = new SpreadConnection();
            connection.connect(InetAddress.getByName(spreadIP), SPREAD_PORT, workerName, false, true);

            SpreadGroup group = new SpreadGroup();
            group.join(connection, SPREAD_GROUP_NAME);

            connection.add(new SpreadMessageListener(connection, 1));

            System.out.println("Connected to Spread group successfully!");
        } catch (SpreadException e) {
            System.out.println("Error connecting to Spread group: " + e.getMessage());
        } catch (java.net.UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void consumeMessages(Channel rabbitChannel, String queueName) {
        try {
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            WorkerCallback workerCallback = new WorkerCallback(queueName, workerName);
            WorkerCallbackCancel cancelCallback = new WorkerCallbackCancel();

            rabbitChannel.basicConsume(queueName, true, workerCallback, cancelCallback);
        } catch (IOException e) {
            System.out.println("Error connecting to RabbitMQ");
        }
    }


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
