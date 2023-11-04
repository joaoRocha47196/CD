import io.grpc.Server;
import io.grpc.ServerBuilder;
import spread.SpreadException;

import java.io.IOException;

public class CourierServer {
    private static int spreadPort = 4803;


    public static void main(String[] args) {
        if (args.length < 4) {
                System.out.println("Need more parameters: Server Port, Courier Name, PubSub IP," +
                    " PubSub Port, Spread IP and (optional)Spread Port.");
            return;
        }

        int serverPort = Integer.parseInt(args[0]);
        String userName = args[1];
        String pubsubIP = args[2];
        int pubsubPort = Integer.parseInt(args[3]);
        String spreadIP = args[4];

        if (args.length == 6) {
            spreadPort = Integer.parseInt(args[5]);
        }

        Courier courier = new Courier(userName, spreadIP, spreadPort, pubsubIP, pubsubPort);

        Server svc = ServerBuilder
                .forPort(serverPort)
                .addService(new CommunicationService(courier.repo))
                .build();

        try {
            svc.start();
            svc.awaitTermination();
            courier.close();
        } catch (IOException | InterruptedException | SpreadException e) {
            e.printStackTrace();
        }
    }
}
