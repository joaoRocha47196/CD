package chatserver;

import chat.ChatGrpc;
import chat.ChatMessage;
import chat.UserID;
import com.google.protobuf.Empty;
import contractServer.ServerToServerGrpc;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import ring.RingGrpc;

import contractServer.ChatMessageServer;


import java.util.logging.Logger;

public class ServerToServer extends ServerToServerGrpc.ServerToServerImplBase {


    private ChatServer chat;
    private static ChatGrpc.ChatStub nonBlockingStubServer;
    private static RingGrpc.RingBlockingStub blockingStubServer;



    public ServerToServer(ChatServer c){

        chat = c;
    }


    @Override
    public void sendMessageServer(ChatMessageServer inMessage, StreamObserver<Empty> responseObserver) {

        ChatMessage msg = ChatMessage.newBuilder().setTxtMsg(inMessage.getTxtMsg()).setFromUser(inMessage.getFromUser()).setToken(inMessage.getToken()).build();
        chat.sendMessage(msg, responseObserver);
        //chat.sendMessageFromServer(inMessage);
        //responseObserver.onNext(Empty.newBuilder().build());
        //responseObserver.onCompleted();
    }


}


