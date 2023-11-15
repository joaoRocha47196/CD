package cn2223tfLandMark;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.*;
import java.util.List;

public class DetectLandmarks {
    final static int ZOOM = 15;
    final static String SIZE = "600x300";

    public static void main(String[] args) {
        try {
            // depends on GOOGLE_APPLICATION_CREDENTIALS
            if (args.length != 1) {
                System.exit(-1);
            }
            String bucketName = args[0];
            StorageOptions storageOptions = StorageOptions.getDefaultInstance();
            Storage storage = storageOptions.getService();
            String blobName = listBucketChooseImage(storage, bucketName);
            detectLandmarksGcs(storage, bucketName, blobName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String listBucketChooseImage(Storage storage, String bucketName) {
        Bucket bucket = storage.get(bucketName);
        for (Blob blob : bucket.list().iterateAll()) {
            System.out.println("    " + blob.toString());
        }
        System.out.println("\n  Enter Blob Name? ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public static void detectLandmarksGcs(Storage storage, String bucketName, String blobName) throws IOException {

        String gcsPath = "gs://" + bucketName + "/" + blobName;
        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).build();


        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

        BatchAnnotateImagesRequest singleBatchRequest = BatchAnnotateImagesRequest.newBuilder()
                .addRequests(request)
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(singleBatchRequest);
            List<AnnotateImageResponse> listResponses = batchResponse.getResponsesList();

            if (listResponses.isEmpty()) {
                System.out.println("Empty response, no landmark detected.");
                return;
            }
            AnnotateImageResponse response = listResponses.get(0);

            FireStoreOperations op = new FireStoreOperations();
            op.init(null);

            List<DetectedLandmark> objs = new ArrayList<>();
            for (EntityAnnotation annotation : response.getLandmarkAnnotationsList()) {
                LocationInfo info = annotation.getLocationsList().listIterator().next();
                System.out.format("Landmark: %s%n%s%nConfidence: %s%n", annotation.getDescription(), info.getLatLng(), annotation.getScore());


                DetectedLandmark obj = new DetectedLandmark();
                obj.local = annotation.getDescription();
                obj.latitude = info.getLatLng().getLatitude();
                obj.longitude = info.getLatLng().getLongitude();
                obj.confidence = annotation.getScore();
                objs.add(obj);
            }
            System.out.println("Saving Data In FireStore");
            getStaticMapSaveImage(objs.get(0).latitude, objs.get(0).longitude,storage,bucketName,blobName);
            op.insertDataInFirestore(blobName, objs);
            op.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getStaticMapSaveImage(Double latitude , Double longitude, Storage storage,String bucketName, String blobname){
        String mapApiKey = System.getenv("MAPS_API_KEY");
        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + latitude + "," + longitude
                + "&zoom=" + ZOOM
                + "&size=" + SIZE
                + "&key=" + mapApiKey;
        System.out.println(mapUrl);
        try {
            URL url = new URL(mapUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            BufferedImage image = ImageIO.read(in);
            String[] imageName = blobname.split("\\.");
            String blobName = "static_map_" + imageName[0] + ".jpg";
            saveMapImageToCloudStorage(storage, image, bucketName, blobName);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMapImageToCloudStorage(Storage storage, BufferedImage bufferImg, String bucketName, String destinationBlobName) throws IOException {
        BlobInfo blobInfo = BlobInfo
                .newBuilder(BlobId.of(bucketName, destinationBlobName))
                .setContentType("image/jpeg")
                .build();
        Blob destBlob = storage.create(blobInfo);
        WriteChannel writeChannel = storage.writer(destBlob);
        OutputStream out = Channels.newOutputStream(writeChannel);
        ImageIO.write(bufferImg, "jpg", out);
        out.close();
    }
}

