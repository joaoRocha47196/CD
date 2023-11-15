package cn2223tfclient;

import cn2223tf.IdentifierResponse;
import io.grpc.stub.StreamObserver;

public class ClientStreamObserverSubmitImage implements StreamObserver<IdentifierResponse> {

    private final String imageName;

    public ClientStreamObserverSubmitImage(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void onNext(IdentifierResponse identifierResponse) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                             Image identifier                             ║");
        System.out.println("║--------------------------------------------------------------------------║");
        System.out.println("║ " + "Image identifier: " + identifierResponse.getName());
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable throwable) {System.err.println("\nError on call: " + throwable.getMessage());}

    @Override
    public void onCompleted() {System.out.println("Image submitted successfully\n");}
}
