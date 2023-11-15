package cn2223tfclient;



import cn2223tf.*;
import io.grpc.stub.StreamObserver;


public class ClientStreamObserverGetByConfidenceLevel implements StreamObserver<FilteredImagesResponse> {

    private final double confidence;

    public ClientStreamObserverGetByConfidenceLevel(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public void onNext(FilteredImagesResponse filteredImagesResponse) {
        if (filteredImagesResponse.getImageListList().isEmpty()) {
            System.out.println("\nNo images found with confidence greater than <" + confidence + ">\n");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    Images with Confidence > " + confidence + "                  ║");
        System.out.println("║------------------------------------------------------------------║");

        for (ImageWithLandmark imageWithLandmark : filteredImagesResponse.getImageListList()) {
            String imageName = imageWithLandmark.getImageName();
            String landmarkName = imageWithLandmark.getLandmarkName();
            System.out.println("║ Image: " + imageName);
            System.out.println("║ Landmark: " + landmarkName);
            System.out.println("║------------------------------------------------------------------║");
        }

        System.out.println("║                          No more images                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable throwable) {System.err.println("\nError on call: " + throwable.getMessage());}

    @Override
    public void onCompleted() {System.out.println("\nThere are no more images that fit the criteria\n");}
}
