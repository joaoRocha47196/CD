package cn2223tfLandMark;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    public void insertDataInFirestore(String blobName, List<DetectedLandmark> detectedLandmarks){

        Date date = new Date();
        System.out.println(date);

        CollectionReference colRef = db.collection("Images");
        DocumentReference docRef = colRef.document(blobName);
        CollectionReference colRefLandmarks = docRef.collection("landmarks");

        for(int i = 0; i < detectedLandmarks.size(); i++){
            DocumentReference docRefLandmark = colRefLandmarks.document(String.valueOf(i+1));
            DetectedLandmark landmark = detectedLandmarks.get(i);
            HashMap<String, Object> map = new HashMap<>() {
                {
                    put("local", landmark.local);
                    put("latitude", landmark.latitude);
                    put("longitude", landmark.longitude);
                    put("confidence", landmark.confidence);
                }
            };

            ApiFuture<WriteResult> landmarks = docRefLandmark.create(map);
            landmarks = docRefLandmark.update(map);
        }
    }
}
