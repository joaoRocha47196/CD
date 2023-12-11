package userapp.StreamObservers;

import csstubs.ResumeNotification;
import io.grpc.stub.StreamObserver;

public class ResumeSalesStreamObserver implements StreamObserver<Empty> {

    @Override
    public void onNext( Empty notification) {
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