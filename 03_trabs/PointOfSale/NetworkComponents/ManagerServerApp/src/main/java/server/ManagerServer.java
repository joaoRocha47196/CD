package server;

import io.grpc.*;
import servercallers.SpreadGroupCaller;
import services.UMService;

import java.io.IOException;
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
    private static SpreadGroupCaller spreadGroupCaller;
    private static UMService service;

    public static void main(String[] args) {
        initConnections(args);
        initSpreadGroupConnection();
        startManagerServer();
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

    public static void startManagerServer(){
        server = new GrpcBaseServer();
        server.init(thisPort, List.of(new UMService(spreadGroupCaller)));
        server.start();
    }

    public static void initSpreadGroupConnection(){
        spreadGroupCaller = new SpreadGroupCaller(workersServerPort, workersServerIp);
    }


    public static void awaitServer(){
        server.awaitTermination();
    }


    static ManagedChannel createChannel(String ip, int port){
        return ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();
    }
}
