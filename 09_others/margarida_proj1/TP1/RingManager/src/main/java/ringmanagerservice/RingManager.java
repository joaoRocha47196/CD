package ringmanagerservice;

import io.grpc.ServerBuilder;

public class RingManager {

    private static int svcPort = 8000;

    public static void main(String[] args) {
        try {
            if (args.length > 0) svcPort = Integer.parseInt(args[0]);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(new RingToClientService())
                    .addService(new RingToServerService())
                    .build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);



            svc.awaitTermination();
            svc.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
