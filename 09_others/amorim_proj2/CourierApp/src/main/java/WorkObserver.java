import io.grpc.stub.StreamObserver;
import rpcCourierStub.Work;

public class WorkObserver implements StreamObserver<Work> {
    @Override
    public void onNext(Work value) {
        CourierApp.state = State.WORKING;
        System.out.println("Source Address: " + value.getAddressSrc());
        System.out.println("Destination Address: " + value.getAddressDest());
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("ERROR: " + t.getMessage());

    }

    @Override
    public void onCompleted() {
        System.out.println("Connection closed.");
    }
}
