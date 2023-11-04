package chatclient;

import chat.ChatMessage;
import chat.UserID;
import io.grpc.stub.StreamObserver;

public class ClientUserObserver implements StreamObserver<UserID> {
    @Override
    public void onNext(UserID user) {
        System.out.println("[sender: " + user.getName());
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
