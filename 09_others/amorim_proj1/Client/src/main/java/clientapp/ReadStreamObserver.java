package clientapp;

import io.grpc.stub.StreamObserver;
import rpcClientStub.Value;

public class ReadStreamObserver implements StreamObserver<Value> {
    private final String key;
    private String value;

    public ReadStreamObserver(String key) {
        this.key = key;
    }
    @Override
    public void onNext(Value value) {
        this.value = value.getValue();
    }

    @Override
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }

    @Override
    public void onCompleted() {
        if (value.equals(""))
            System.out.printf("Don't exist pair with key: %s\n", key);
        else
            System.out.printf("Pair: \"%s: %s\"\n", key, value);
    }
}
