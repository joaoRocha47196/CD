package ringmanager;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Run{

    private static final int ServersN = 2;
    private static int serverPort = 6000;
    private static int clientPort = 7000;

    public static void main(String[] args) {


        RingManagerClient ringClient = new RingManagerClient();
        RingManagerServer ringServer = new RingManagerServer(ringClient);


        try {
            final Server serverRun = ServerBuilder.forPort(serverPort)
                    .addService(ringServer)
                    .build()
                    .start();

            final Server clientRun = ServerBuilder.forPort(clientPort)
                    .addService(ringClient)
                    .build()
                    .start();


            new Thread()
            {
                public void run() {
                    System.err.println("*** server await termination");
                    try {
                        serverRun.awaitTermination();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }


            }

            }.start();



            System.err.println("*** server await termination");
            clientRun.awaitTermination();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }









    }

