package server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.List;

public class GrpcBaseServer {

    private Server serverInstance;

    public void init(int svcPort, List<BindableService> services) {
        try {
            ServerBuilder<?> serverBuilder = ServerBuilder
                    .forPort(svcPort);

            for (BindableService srv : services)
                serverBuilder.addService(srv);

            serverInstance = serverBuilder.build();

            System.out.println("Listening on " + svcPort);
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    public void start(){
        try {
            serverInstance.start();
            System.out.println("Server Started ");
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }

    }

    public void awaitTermination() {
        try {
            serverInstance.awaitTermination();
            serverInstance.shutdown();
        } catch (InterruptedException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /** Old
     public void init(int svcPort) {
     try {
     serverInstance = ServerBuilder
     .forPort(svcPort)
     .addService(createService())
     .build();
     System.out.println("Listening on " + svcPort);
     } catch (Exception ex) {
     System.out.println("Error");
     ex.printStackTrace();
     }
     }
     */
}
