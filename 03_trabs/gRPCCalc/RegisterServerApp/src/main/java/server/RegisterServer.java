package server;

import calcstubs.*;
import io.grpc.BindableService;
import services.CRService;
import services.SRService;
import calcstubs.Number;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;


public class RegisterServer {

    private static final int DEFAULT_PORT = 8500;
    private static int svcPort;
    private static GrpcBaseServer server;

    public static void main(String[] args) {
        initConnections(args);
        initServer();
        awaitServer();
    }

    public static void initConnections(String[] args){
        if (args.length > 0)
            svcPort = Integer.parseInt(args[0]);
        else svcPort = DEFAULT_PORT;
    }

    public static void initServer(){
        server = new GrpcBaseServer();
        List<BindableService> services = List.of(new SRService(), new CRService());
        server.init(svcPort,services);
        server.start();
    }

    public static void awaitServer(){
        server.awaitTermination();
    }

    public void logger(String className, String methodName, String info){
        System.out.println("[INFO]" + " ["
                + className + " - "
                + methodName + " - "
                + info + "]"
        );
    }
}
