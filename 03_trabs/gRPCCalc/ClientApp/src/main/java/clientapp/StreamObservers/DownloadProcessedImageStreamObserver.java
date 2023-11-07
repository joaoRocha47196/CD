package clientapp.StreamObservers;

import io.grpc.stub.StreamObserver;

public class DownloadProcessedImageStreamObserver implements StreamObserver<ImageResponse> {
    @Override
    public void onNext(ImageResponse imageResponse) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Downloaded Image Info       ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Received processed image ID: " + imageResponse.getImageId());
        System.out.println("║ Status: " + imageResponse.getStatus());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error during image download: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Image download completed.");
    }
}
