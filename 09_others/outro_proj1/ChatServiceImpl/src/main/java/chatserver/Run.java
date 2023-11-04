package chatserver;

import chat.ChatGrpc;
import chat.ChatMessage;
import chat.UserID;
import chat.Vazio;
import com.google.protobuf.Empty;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import ring.serverID;
import ring.RingGrpc;
import contractServer.ChatMessageServer;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class Run {

    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());

    private static final int ServersN = 3;
    private static int serverPort = 6000;
    private static int clientPort = 7000;
    private static String ringIP;
    private static String serverIP;


    public static void main(String[] args) {


        serverIP = args[0];

        ringIP = args[1];

        System.out.println(serverIP  + "  " + ringIP);

        ChatServer clientServer = new ChatServer();

        try {
            final Server cServer = ServerBuilder.forPort(clientPort)
                    .addService(clientServer)
                    .build()
                    .start();


            new Thread() {
                public void run() {
                    System.err.println("*** server await termination");
                    try {
                        cServer.awaitTermination();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }


                }

            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ManagedChannel channel1 = ManagedChannelBuilder.forAddress(ringIP, serverPort)
                .usePlaintext()
                .build();
        RingGrpc.RingStub nonBlockingStub = RingGrpc.newStub(channel1);
        RingGrpc.RingBlockingStub blockingStub = RingGrpc.newBlockingStub(channel1);

        serverID ip = serverID.newBuilder().setIp(serverIP).build();
        blockingStub.registerServer(ip);

        serverID vizinhoIp = blockingStub.vizinhoIp(ip);
        String vIp = vizinhoIp.getIp();

        System.out.println(vIp);

        ServerToServer serverServer = new ServerToServer(clientServer);

        try {

            final Server sServer = ServerBuilder.forPort(serverPort)
                    .addService(serverServer)
                    .build()
                    .start();
            logger.info("Server started, listening on " + serverPort);



            System.err.println("*** server await termination");
            clientServer.connectServerToServer(vIp);
            sServer.awaitTermination();

        } catch (Exception e) {
            e.printStackTrace();
        }





    }

        public Run(String sIP, String rIP){

        serverIP =sIP;
        ringIP = rIP;
        }
    }

