package cn2223tf;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.ByteString;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CN2223TFServer extends CN2223TFServiceGrpc.CN2223TFServiceImplBase{
    private static int svcPort=8000;
    private static Storage storage = null;
    public static String BUCKET_NAME = "cn-europe";

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length > 0) {
            svcPort = Integer.parseInt(args[0]);
            BUCKET_NAME = args[1];
        }
        initStorage();
        initGRPC();


        System.out.println("Terminating...");
    }

    @Override
    public StreamObserver<ImageUploadRequest> imageSubmit(StreamObserver<IdentifierResponse> responseObserver) {
        System.out.println();
        System.out.println("Submitting image ...");
        ServerStreamObserverImage requests = new ServerStreamObserverImage(responseObserver, storage, BUCKET_NAME);
        return requests;
    }

    @Override
    public void getLandmarks(IdentifierRequest request, StreamObserver<LandmarksResponse> responseObserver) {
        String imageName = request.getName();

        System.out.println();
        System.out.println("Analyzing image: " + imageName);

        FireStoreOperations op = new FireStoreOperations();
        try {
            op.init(null);

            List<HashMap<String, Object>> imageLandmarks = op.getLandmarks(imageName);
            LandmarksResponse.Builder responseBuilder = LandmarksResponse.newBuilder();

            for (HashMap<String, Object> landmark : imageLandmarks) {
                Double confidence = Double.parseDouble(landmark.get("confidence").toString());
                String local = landmark.get("local").toString();
                Double latitude = Double.parseDouble(landmark.get("latitude").toString());
                Double longitude = Double.parseDouble(landmark.get("longitude").toString());

                Landmark imageLandmark = Landmark.newBuilder()
                        .setLocal(local)
                        .setConfidence(confidence)
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .build();
                responseBuilder.addLandmark(imageLandmark);
                System.out.println("Landmarks:");
                System.out.println("Local: " + local + " | Confidence: " + confidence + " | Latitude: " + latitude + " | Longitude: " + longitude);
            }

            LandmarksResponse response = responseBuilder.build();
            responseObserver.onNext(response);
            System.out.println("Finished request");
            op.close();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Throwable());
        }

        responseObserver.onCompleted();
    }

    public void getStaticMap(IdentifierRequest request, StreamObserver<StaticMapResponse> responseObserver) {
        String imageName = "static_map_" + request.getName();
        System.out.println();
        System.out.println("Getting processed image: " + imageName);
        try {
            byte[] imageBytes = downloadBlobFromBucket(BUCKET_NAME, imageName);
            String[] filenameParts = imageName.split("\\.");
            String basename = filenameParts[0];
            String extension = filenameParts[1];
            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            byte[] bytes = new byte[4096];
            int size;
            while ((size = inputStream.read(bytes)) > 0){
                StaticMapResponse response = StaticMapResponse.newBuilder()
                        .setImage(Image.newBuilder()
                                .setContent(ByteString.copyFrom(bytes, 0 , size))
                                .setMetadata(Metadata.newBuilder()
                                        .setName(basename)
                                        .setType(extension)
                                        .build())
                                .build())
                        .build();
                responseObserver.onNext(response);
            }

            inputStream.close();
            responseObserver.onCompleted();
            System.out.println("Finished request");
        } catch (IOException e) {
            e.printStackTrace();
            responseObserver.onError(new Throwable());
        }

    }

    public void getImagesByConfidence(ConfidenceRequest request, StreamObserver<FilteredImagesResponse> responseObserver) {
        FireStoreOperations op = new FireStoreOperations();
        try {
            op.init(null);
            System.out.println();
            System.out.println("Searching images that have landmarks with confidence <" + request.getConfidence() + ">");

            List<ImageWithLandmark> imageList = new ArrayList<>();
            Map<String, List<String>> images = op.getImagesByConfidence(request.getConfidence());

            for (Map.Entry<String, List<String>> entry : images.entrySet()) {
                String imageName = entry.getKey();
                List<String> landmarkNames = entry.getValue();

                for (String landmarkName : landmarkNames) {
                    ImageWithLandmark imageWithLandmark = ImageWithLandmark.newBuilder()
                            .setImageName(imageName)
                            .setLandmarkName(landmarkName)
                            .build();

                    imageList.add(imageWithLandmark);
                }
            }

            FilteredImagesResponse response = FilteredImagesResponse.newBuilder()
                    .addAllImageList(imageList)
                    .build();

            responseObserver.onNext(response);

            System.out.println("Finished request");
            op.close();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Throwable("Invalid Query Parameters"));
        }
        responseObserver.onCompleted();
    }

    public byte[] downloadBlobFromBucket(String bucketName, String blobName) throws IOException {


        BlobId blobId = BlobId.of(bucketName, blobName);

        Blob blob = storage.get(blobId);
        if (blob == null) {
            System.out.println("No such Blob exists !");
            return null;
        }

        ByteArrayOutputStream downloadedBlob = new ByteArrayOutputStream();
        if (blob.getSize() < 1_000_000) {

            byte[] content = blob.getContent();
            downloadedBlob.write(content);
        } else {

            try (ReadChannel reader = blob.reader()) {
                byte[] bytes = new byte[64 * 1024];
                while (reader.read(ByteBuffer.wrap(bytes)) > 0) {
                    downloadedBlob.write(bytes);
                }
            }
        }

        System.out.println(downloadedBlob.size());
        return downloadedBlob.toByteArray();
    }

    private static void initGRPC() throws IOException {
        io.grpc.Server svc= ServerBuilder.forPort(svcPort).addService(new CN2223TFServer()).build();
        svc.start();
        System.out.println("Bucket Name = " + BUCKET_NAME);
        System.out.println("Started listening...");

        try {
            svc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void initStorage() {

        StorageOptions storageOptions = null;

        storageOptions = StorageOptions.getDefaultInstance();

        CN2223TFServer.storage = storageOptions.getService();
        String projID = storageOptions.getProjectId();
        if (projID != null) System.out.println("Current Project ID:" + projID);
        else {
            System.out.println("The environment variable GOOGLE_APPLICATION_CREDENTIALS isn't well defined");
            System.exit(-1);
        }
    }
}
