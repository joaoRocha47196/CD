package chatclient;

import chat.ChatMessage;
import chat.Vazio;
import io.grpc.stub.StreamObserver;

public class ClientEmptyObsever implements StreamObserver<Vazio> {
    @Override
    public void onNext(Vazio emp) {
    }

    @Override
    public void onError(Throwable throwable) {
        // TODO
        System.out.println("onError!!"+throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        // TODO
    }
}
