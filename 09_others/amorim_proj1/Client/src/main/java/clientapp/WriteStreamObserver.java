package clientapp;

import io.grpc.stub.StreamObserver;
import rpcClientStub.Void;

public class WriteStreamObserver implements StreamObserver<Void> {
    private String key;
    private String value;

    public WriteStreamObserver(String key, String value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public void onNext(Void value) {

    }

    @Override
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.printf("Pair: \"%s: %s\" Written\n", key, value);
    }
}
