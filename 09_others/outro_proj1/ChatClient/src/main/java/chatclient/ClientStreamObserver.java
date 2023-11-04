package chatclient;

import chat.ChatMessage;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class ClientStreamObserver implements StreamObserver<ChatMessage> {
    private boolean isCompleted=false;
    private boolean success=false;
    public boolean OnSuccesss() {
        return success;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    List<ChatMessage> rplys = new ArrayList<ChatMessage>();
    public List<ChatMessage> getReplays() {
        return rplys;
    }
    @Override
    public void onNext(ChatMessage reply) {
        System.out.println("Reply ("+reply.getFromUser()+"):"+reply.getTxtMsg());
        rplys.add(reply);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call:"+throwable.getMessage());
        isCompleted=true; success=false;
    }

    @Override
    public void onCompleted() {
        System.out.println("Stream completed");
        isCompleted=true; success=true;
    }
}
