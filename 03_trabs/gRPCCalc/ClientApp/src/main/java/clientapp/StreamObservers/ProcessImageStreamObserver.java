package clientapp.StreamObservers;

import io.grpc.stub.StreamObserver;

public class ProcessImageStreamObserver implements StreamObserver<ImageIdentifier> {
    @Override
    public void onNext(ImageIdentifier imageIdentifier) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Image Processing Info       ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Image processed successfully.      ║");
        System.out.println("║ Image ID: " + imageIdentifier.getIdentifier());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error during image processing: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Image processing completed.");
    }
}
