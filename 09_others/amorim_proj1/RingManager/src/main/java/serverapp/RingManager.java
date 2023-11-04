package serverapp;


import io.grpc.ServerBuilder;

public class RingManager {

    private static final Repo repo = new Repo();
    private static int svcPort = 8000;
    private static int maxServers;

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Need to pass the max servers value.");
                return;
            }
            maxServers = Integer.parseInt(args[0]);
            if (maxServers <= 0 || maxServers >= 5) {
                System.out.println("N should be between 0 and 5");
            }
            if (args.length > 1) svcPort = Integer.parseInt(args[1]);
            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(new RegisterServerService(repo, maxServers))
                    .addService(new ServerBrowserService(repo))
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
