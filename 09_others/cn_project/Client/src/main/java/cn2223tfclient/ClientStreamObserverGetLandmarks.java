package cn2223tfclient;


import cn2223tf.Landmark;
import cn2223tf.LandmarksResponse;
import io.grpc.stub.StreamObserver;

public class ClientStreamObserverGetLandmarks implements StreamObserver<LandmarksResponse> {

    private final String imageName;

    public ClientStreamObserverGetLandmarks(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void onNext(LandmarksResponse landmarksResponse) {
        if (landmarksResponse.getLandmarkList().size() == 0) {
            System.out.println("\nNo landmarks detected in image [" + this.imageName + "]\n");
            return;
        }

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Landmarks in Image          ║");
        System.out.println("║------------------------------------║");

        for (Landmark landmark : landmarksResponse.getLandmarkList()) {
            System.out.println("║ Local: " + landmark.getLocal());
            System.out.println("║ Confidence: " + landmark.getConfidence());
            System.out.println("║ Latitude: " + landmark.getLatitude());
            System.out.println("║ Longitude: " + landmark.getLongitude());
            System.out.println("║------------------------------------║");
        }

        System.out.println("║          No more landmarks         ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable throwable) {System.err.println("\nError on call: " + throwable.getMessage());}

    @Override
    public void onCompleted() {System.out.println("There are no more landmarks detected in the image.\n");}

}
