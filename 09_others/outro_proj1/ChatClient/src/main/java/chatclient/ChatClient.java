package chatclient;

import chat.ChatGrpc;
import chat.ChatMessage;
import chat.UserID;
import chat.Vazio;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import client.msg;
import  client.ClientGrpc;
import client.clientID;

import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {

    public static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    private static String ringIP = "35.242.183.223";//"localhost";
    private static int ringPort = 7000;
    private static int serverPort = 7000;

    public static void main(String[] args) throws Exception {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(ringIP, ringPort)
                .usePlaintext()
                .build();
        ClientGrpc.ClientStub nonBlockingStub = ClientGrpc.newStub(channel);
        ClientGrpc.ClientBlockingStub blockingStub = ClientGrpc.newBlockingStub(channel);





        try {

            Scanner input = new Scanner(System.in);

            /*System.out.println("receber utilizadores:  y/n");
            String resp = input.nextLine();

            if(resp.equals("y")){

                //blockingStub.getUtilizadores(Vazio.newBuilder().build());

                Iterator<UserID> manyRpys = blockingStub.getUtilizadores(Vazio.newBuilder().build());
                System.out.println("Lista de Utilizadores");

                while (manyRpys.hasNext()) {

                    UserID rpy = manyRpys.next();
                    System.out.println("Utilizador : " + rpy.getName());

                }

            }*/


            System.out.print("Enter your nickName: ");
            String clientName = input.nextLine();

            // Setup Channel to Server


            clientID nameRing = clientID.newBuilder().setName(clientName).build();
            UserID nameServer = UserID.newBuilder().setName(clientName).build();
            // register client in remote server

            msg pp =  blockingStub.getServer(nameRing);

            System.out.println(" server  ip : "+ pp.getIp());

            ManagedChannel channel2 = ManagedChannelBuilder.forAddress(pp.getIp(), serverPort)
                    .usePlaintext()
                    .build();

            ChatGrpc.ChatStub nonBlockingStub2 = ChatGrpc.newStub(channel2);
            ChatGrpc.ChatBlockingStub blockingStub2 = ChatGrpc.newBlockingStub(channel2);


            nonBlockingStub2.register(nameServer, new ClientChatObserver());
            // send messages
            System.out.println("Enter lines or the word \"exit\"");
            while (true) {
                String line = input.nextLine();
                if (line.equals("exit")) {

                    blockingStub2.endSession(UserID.newBuilder().setName(clientName).build());
                    break;

                }

                //int randomNum = ThreadLocalRandom.current().nextInt(10000, 1000000 + 1);
                int randomNum = 1 + (int)(Math.random() * ((10000 - 1) + 1));

                blockingStub2.sendMessage(ChatMessage.newBuilder()
                    .setFromUser(clientName)
                    .setTxtMsg(line).setToken(randomNum).build());
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error:" + ex.getMessage());
        }
        if (channel!=null) {
            logger.log(Level.INFO, "Shutdown channel to server ");
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
