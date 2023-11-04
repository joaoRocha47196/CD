import io.grpc.stub.StreamObserver;
import rpcCourierStub.Void;

public class VoidObserver implements StreamObserver<Void> {
    @Override
    public void onNext(Void value) {
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompleted() {

    }
}
