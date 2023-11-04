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
import contractServer.ServerToServerGrpc;



import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class ChatServer extends ChatGrpc.ChatImplBase {

    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
    static int serverPort = 6000;
    private static final String ringIP = "34.105.206.247";
    private static String serverIP ;
    private static ChatGrpc.ChatStub nonBlockingStubServer;
    private static RingGrpc.RingBlockingStub blockingStubServer;
    private ConcurrentHashMap<UserID, StreamObserver<ChatMessage>> clients;
    private ManagedChannel channel1;
    private ServerToServerGrpc.ServerToServerStub nonBlockingStub;
    private ServerToServerGrpc.ServerToServerBlockingStub blockingStub;
    ArrayList<Integer> tokens = new ArrayList<>();

    public static void main(String[] args) {


       /* ManagedChannel channel1 = ManagedChannelBuilder.forAddress(ringIP, serverPort)
                .usePlaintext()
                .build();
        RingGrpc.RingStub nonBlockingStub = RingGrpc.newStub(channel1);
        RingGrpc.RingBlockingStub blockingStub = RingGrpc.newBlockingStub(channel1);

      serverID ip = serverID.newBuilder().setIp(serverIP).build();
      blockingStub.registerServer(ip);

       serverID vizinhoIp = blockingStub.vizinhoIp(ip);
       String vIp = vizinhoIp.getIp();

        ManagedChannel channel2 = ManagedChannelBuilder.forAddress(vIp, serverPort2)
                .usePlaintext()
                .build();
        nonBlockingStubServer  = ChatGrpc.newStub(channel2);
        blockingStubServer = RingGrpc.newBlockingStub(channel2);*/


    }




    public ChatServer() {


        clients = new ConcurrentHashMap<UserID, StreamObserver<ChatMessage>>();
    }


    public void connectServerToServer(String ip){

        channel1 = ManagedChannelBuilder.forAddress(ip, serverPort)
                .usePlaintext()
                .build();

        blockingStub = ServerToServerGrpc.newBlockingStub(channel1);

    }

    @Override
    public void sendMessage(ChatMessage inMessage, StreamObserver<Empty> responseObserver) {

        int tk = inMessage.getToken();

        System.out.println("token : " + tk);

        System.out.println(tokens.toString());

        if (tokens.contains(tk)) {

            tokens.remove(tk);

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();

        } else {

            tokens.add(tk);

            for (UserID clientDest : clients.keySet()) {
                try {
                    ChatMessage outMessage = ChatMessage.newBuilder()
                            .setFromUser(inMessage.getFromUser())
                            .setTxtMsg(inMessage.getTxtMsg()).build();
                    clients.get(clientDest).onNext(outMessage);
                } catch (Throwable ex) {
                    // error calling remote client, remove client name and callback
                    System.out.println("Client " + clientDest.getName() + " removed");
                    clients.remove(clientDest);
                }
            }

            ChatMessageServer msg = ChatMessageServer.newBuilder()
                    .setTxtMsg(inMessage.getTxtMsg()).setFromUser(inMessage.getFromUser())
                    .setToken(inMessage.getToken()).build();

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
            System.out.println("completou cliente");

            blockingStub.sendMessageServer(msg);

            System.out.println("mandou server");
        }
    }


    public void sendMessageFromServer(ChatMessage inMessage){




    }






    @Override
    public void getUtilizadores (Vazio vs, StreamObserver<UserID> responseObserver){
        System.out.println("AQUII");
        if(clients.isEmpty()){

            UserID user = UserID.newBuilder().setName("Nao existem utilizadores").build();
            responseObserver.onNext(user);

        }else {

            for (UserID client : clients.keySet()) {
                try {

                    UserID user = UserID.newBuilder().setName(client.getName()).build();
                    responseObserver.onNext(user);


                } catch (Throwable ex) {
                    // error calling remote client, remove client name and callback
                    System.out.println("Client error");

                }
            }
        }

        responseObserver.onCompleted();


    }

    @Override
    public void endSession(UserID clientID, StreamObserver<Empty> responseObserver){

        synchronized (clients) {
            if (clients.containsKey(clientID)) {
                clients.remove(clientID);
                System.out.println("cliente removido");
            }
            else {
                System.out.println("Client " + clientID.getName() + " doesnt exist");
                Throwable t = new StatusException(
                        Status.INVALID_ARGUMENT.withDescription("Client is already logout")
                );
                responseObserver.onError(t);
            }
        }
    }

    @Override
    public void register(UserID clientID, StreamObserver<ChatMessage> responseObserver) {
        synchronized (clients) {
            if (!clients.containsKey(clientID)){
                clients.put(clientID, responseObserver);
                System.out.println("utilizador " + clientID + "registado");}
            else {
                System.out.println("Client " + clientID.getName() + " already taken");
                Throwable t = new StatusException(
                    Status.INVALID_ARGUMENT.withDescription("Client nickname already taken")
                );
                responseObserver.onError(t);
            }
        }
    }
}
