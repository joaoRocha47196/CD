package clientapp.StreamObservers;

import csstubs.ImageResponse;
import io.grpc.stub.StreamObserver;

public class CheckImageStatusStreamObserver implements StreamObserver<ImageResponse> {
    @Override
    public void onNext(ImageResponse imageResponse) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Image Status Info           ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Image ID: " + imageResponse.getImageId());
        System.out.println("║ Status: " + imageResponse.getStatus());
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
