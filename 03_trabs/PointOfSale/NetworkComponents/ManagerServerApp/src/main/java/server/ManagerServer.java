package server;

import rabbit.RabbitConsumerNotification;
import servercallers.SpreadGroupCaller;
import services.UMService;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ManagerServer {

    private static final String THIS_DEFAULT_IP = "localhost";
    private static int THIS_DEFAULT_PORT = 8080;
    private static int thisPort;
    private static String thisIp;

    private static final String SPREAD_DEFAULT_IP = "localhost";
    private static final int SPREAD_PORT = 4803;
    private static String spreadIP;

    // RABBIT
    private static final String RABBITMQ_DEFAULT_HOST = "34.28.226.254";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static String rabbitMQHost;
    private static int rabbitMQPort;

    private static GrpcBaseServer server;
    private static SpreadGroupCaller spreadGroupCaller;

    public static void main(String[] args) {
        initConnections(args);
        initSpreadGroupConnection();
        startManagerServer();
        awaitServer();
    }

    public static void initConnections(String[] args){
        if (args.length == 5) {
            thisIp = args[0];
            thisPort = Integer.parseInt(args[1]);
            rabbitMQHost = args[2];
            rabbitMQPort = Integer.parseInt(args[3]);
            spreadIP = args[4];
        }
        else {
            thisPort = THIS_DEFAULT_PORT;
            thisIp = THIS_DEFAULT_IP;
            spreadIP = SPREAD_DEFAULT_IP;
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
        }
    }

    public static void startManagerServer(){
        server = new GrpcBaseServer();
        server.init(thisPort, List.of(new UMService(spreadGroupCaller)));
        server.start();
    }

    public static void awaitServer(){
        server.awaitTermination();
    }

    public static void initSpreadGroupConnection(){
        spreadGroupCaller = new SpreadGroupCaller(SPREAD_PORT, spreadIP);
    }

    public static void consumeNotifications(String exchangeName, CompletableFuture<String> futureNotification){
        RabbitConsumerNotification rabbitBroker = new RabbitConsumerNotification
                (rabbitMQHost, rabbitMQPort, exchangeName);
        rabbitBroker.initConnection();
        rabbitBroker.declareQueue();
        rabbitBroker.consumeNotifications(futureNotification);
    }
}
