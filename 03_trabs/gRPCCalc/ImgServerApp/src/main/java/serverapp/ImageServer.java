package serverapp;

import calcstubs.*;
import calcstubs.Number;
import csstubs.CSServiceGrpc;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import servercallers.RegisterServerCaller;
import services.CSService;

public class ImageServer{

    private static int svcPort = 8500;

    public static void main(String[] args) {
        if (args.length > 0)
            svcPort = Integer.parseInt(args[0]);

        Server grpcServer = initServer();
        if(grpcServer == null){
            System.out.println("Error");
            return;
        }
        registerServer();
        awaitServerTermination(grpcServer);
    }

    public static void awaitServerTermination(Server grpcServer){
        try {
            grpcServer.awaitTermination();
            grpcServer.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static Server initServer(){
        try {
            Server grpcServer = ServerBuilder
                .forPort(svcPort)
                .addService(new CSService())
                .build();
            grpcServer.start();
            System.out.println("Server started, listening on " + svcPort);
            return grpcServer;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Trohw error
        return null;
    }

    public static void registerServer(){
        String svcIP = "localhost";
        int registerServerPort = 8500;

        System.out.println("connect to register server in:" + svcIP + ":" + registerServerPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(svcIP, registerServerPort)
                .usePlaintext()
                .build();

        RegisterServerCaller registerServerCaller = new RegisterServerCaller(channel);
        registerServerCaller.registerServer();
    }

    public void logger(String className, String methodName, String info){
        System.out.println("[INFO]" + " ["
                + className + " - "
                + methodName + " - "
                + info + "]"
        );
    }
}
