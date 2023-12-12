package app.StreamObservers;


import io.grpc.stub.StreamObserver;
import umstubs.EmptyResponse;

public class ResumeSalesStreamObserver implements StreamObserver<EmptyResponse> {

    @Override
    public void onNext( EmptyResponse notification) {
        // Handle the onNext event (summary received)
        System.out.println("Resume Sales notification received: ");
    }

    @Override
    public void onError(Throwable t) {
        // Handle the onError event (error in summary request)
        System.err.println("Error in summary request: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        // Handle the onCompleted event (stream is completed)
        System.out.println("Resume Sales completed.");
    }
}