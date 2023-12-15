package workerapp;

import workerapp.rabbit.RabbitProducer;
import workerapp.spread.GroupMember;
import workerapp.rabbit.RabbitConsumer;

import java.io.File;
import java.io.IOException;

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
        createGlusterDirectories();
        initSpreadGroup();
        initRabbit();
        //Channel rabbitChannel = initRabbitMQConnection();
        //rabbitconfig(rabbitChannel);
    }

    private static void initConnections(String[] args) {
        if (args.length == 4) {
            rabbitMQHost = args[0];
            rabbitMQPort = Integer.parseInt(args[1]);
            routingKey = args[2];
            //spreadIP = args[3];
            workerName = args[3];
        } else {
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
            routingKey = "ALIMENTAR";
            spreadIP = "";
            workerName = "worker1";
        }
    }

    private static void initSpreadGroup() {
        GroupMember groupMember = new GroupMember(spreadIP, SPREAD_PORT, workerName);
        groupMember.joinGroup();
    }

    private static void initRabbit(){
        RabbitConsumer rabbitBroker = new RabbitConsumer(rabbitMQHost, rabbitMQPort, routingKey, workerName);
        rabbitBroker.initConnection();
        rabbitBroker.declareSalesQueue();
    }

    public static void sendNotification(String exchangeName, String fileName) {
        RabbitProducer rabbitBroker = new RabbitProducer(rabbitMQHost, rabbitMQPort);
        rabbitBroker.initConnection();
        rabbitBroker.publishMessage(exchangeName, fileName);
    }

    /*
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
*/

    private static void createGlusterDirectories() {
        createGlusterDirectory("/alimentar");
        createGlusterDirectory("/casa");
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
