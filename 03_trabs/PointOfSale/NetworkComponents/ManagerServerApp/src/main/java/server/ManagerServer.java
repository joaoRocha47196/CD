package server;

import io.grpc.*;
import servercallers.RegisterServerCaller;
import services.CSService;

import java.util.List;

public class ManagerServer {

    private static final String THIS_DEFAULT_IP = "localhost";
    private static int THIS_DEFAULT_PORT = 8080;
    private static int WORKERS_DEAFULT_PORT = 8500;
    private static final String WORKERS_DEFAULT_IP = "localhost";

    private static int thisPort;
    private static String thisIp; // "35.246.73.129";
    private static int workersServerPort;
    private static String workersServerIp; // "35.246.73.129";

    private static GrpcBaseServer server;
    private static RegisterServerCaller registerServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initRegisterServerConnection();
        startImageServer();
        registerImageServer();
        awaitServer();
    }

    public static void initConnections(String[] args){
        if (args.length == 4) {
            thisIp = args[0];
            thisPort = Integer.parseInt(args[1]);
            workersServerPort = Integer.parseInt(args[2]);
            workersServerIp = args[3];
        }
        else {
            thisPort = THIS_DEFAULT_PORT;
            thisIp = THIS_DEFAULT_IP;
            workersServerPort = WORKERS_DEAFULT_PORT;
            workersServerIp = WORKERS_DEFAULT_IP;
        }
    }

    public static void startImageServer(){
        server = new GrpcBaseServer();
        server.init(thisPort, List.of(new CSService()));
        server.start();
    }

    public static void initRegisterServerConnection(){
        System.out.println("connect to manager server in: " +  + ":" + registerServerPort);
        ManagedChannel channel = createChannel(registerServerIp, registerServerPort);
        registerServerCaller = new RegisterServerCaller(channel);
    }

    public static void awaitServer(){
        server.awaitTermination();
    }

    public static void registerImageServer(){
        registerServerCaller.registerServer(thisIp, thisPort);
    }

    static ManagedChannel createChannel(String ip, int port){
        return ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();
    }
}
