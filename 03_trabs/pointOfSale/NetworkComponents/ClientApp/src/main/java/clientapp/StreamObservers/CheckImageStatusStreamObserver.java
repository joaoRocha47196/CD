package clientapp.StreamObservers;

import csstubs.StatusResponse;
import io.grpc.stub.StreamObserver;

public class CheckImageStatusStreamObserver implements StreamObserver<StatusResponse> {
    @Override
    public void onNext(StatusResponse statusResponse) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Image Status Info           ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Image ID: " + statusResponse.getImageId());
        System.out.println("║ Status: " + statusResponse.getStatus());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error during image status check: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Image status check completed.");
    }
}
