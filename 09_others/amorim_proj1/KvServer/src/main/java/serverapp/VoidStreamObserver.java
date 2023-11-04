package serverapp;

import io.grpc.stub.StreamObserver;
import rpcKvServerStub.Void;

public class VoidStreamObserver implements StreamObserver<Void> {
    private final String errorMessage;
    public VoidStreamObserver(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void onNext(rpcKvServerStub.Void value) {

    }

    @Override
    public void onError(Throwable t) {
        System.out.println(errorMessage);
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
    }
}
