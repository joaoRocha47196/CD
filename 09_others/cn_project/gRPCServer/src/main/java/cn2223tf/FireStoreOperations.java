package cn2223tf;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class FireStoreOperations {

    Firestore db;
    String currentCollection;

    public void init(String pathFileKeyJson) throws IOException {
        GoogleCredentials credentials = null;
        if (pathFileKeyJson != null) {
            InputStream serviceAccount = new FileInputStream(pathFileKeyJson);
            credentials = GoogleCredentials.fromStream(serviceAccount);
        } else {
            // use GOOGLE_APPLICATION_CREDENTIALS environment variable
            credentials = GoogleCredentials.getApplicationDefault();
        }
        FirestoreOptions options = FirestoreOptions
                .newBuilder().setCredentials(credentials).build();
        db = options.getService();
        currentCollection = "Images";
    }

    public void close() throws Exception {
        db.close();
    }

    public List<HashMap<String, Object>> getLandmarks(String blobName) throws Exception {
        Query query = db.collection(currentCollection).document(blobName).collection("landmarks");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<HashMap<String, Object>> landmarks = new ArrayList<>();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("local", doc.get("local"));
            map.put("confidence", doc.get("confidence"));
            map.put("latitude", doc.get("latitude"));
            map.put("longitude", doc.get("longitude"));

            landmarks.add(map);
        }

        return landmarks;
    }
    

    public Map<String, List<String>> getImagesByConfidence(double confidence) throws Exception {
        Map<String, List<String>> blobLandmarkMap = new HashMap<>();
        Query query = db.collectionGroup("landmarks").whereGreaterThanOrEqualTo("confidence", confidence);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        int querySize = querySnapshot.get().size();
        System.out.println("Number of results: " + querySize);

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            String blobId = doc.getReference().getParent().getParent().getId();
            String landmarkName = doc.getString("local");
            System.out.println("Blob ID: " + blobId + ", Landmark: " + landmarkName);

            if (blobLandmarkMap.containsKey(blobId)) {
                // If the blobId already exists in the map, retrieve the existing landmarks list and add the new landmark
                List<String> landmarks = blobLandmarkMap.get(blobId);
                landmarks.add(landmarkName);
            } else {
                // If the blobId doesn't exist in the map, create a new list and add the landmark
                List<String> landmarks = new ArrayList<>();
                landmarks.add(landmarkName);
                blobLandmarkMap.put(blobId, landmarks);
            }
        }

        return blobLandmarkMap;
    }
}
