package server;

import io.grpc.*;
import servercallers.RegisterServerCaller;
import services.CSService;

import java.util.List;

public class ImageServer {

    private static int DEFAULT_PORT = 8500;
    private static int svcPort;
    private static GrpcBaseServer server;
    private static RegisterServerCaller registerServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initServer();
        initClient(); // TODO VERIFY METHOD
        processOperations();
        awaitServer();
    }

    public static void initConnections(String[] args){
        if (args.length > 0)
            svcPort = Integer.parseInt(args[0]);
        else svcPort = DEFAULT_PORT;
    }

    public static void initServer(){
        server = new GrpcBaseServer();
        server.init(svcPort, List.of(new CSService()));
        server.start();
    }

    public static void initClient(){
        String svcIP = "localhost";
        int registerServerPort = 8500;


        System.out.println("connect to register server in:" + svcIP + ":" + registerServerPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(svcIP, registerServerPort)
                .usePlaintext()
                .build();

        registerServerCaller = new RegisterServerCaller(channel);
    }

    public static void awaitServer(){
        server.awaitTermination();
    }

    public static void processOperations (){
        registerServerCaller.registerServer();
    }

    public static void logger(String className, String methodName, String info){
        System.out.println("[INFO]" + " ["
                + className + " - "
                + methodName + " - "
                + info + "]"
        );
    }
}
