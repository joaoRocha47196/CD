package cn2223tfLandMark;

import com.google.cloud.ReadChannel;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.storage.*;
import com.google.pubsub.v1.PubsubMessage;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Map;

public class MessageReceiveHandler implements MessageReceiver {

    public static String BUCKET_NAME;
    Storage storage = null;

    public MessageReceiveHandler(String bucketName) {
        this.BUCKET_NAME = bucketName;
    }

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        System.out.println("Message (Id:" + pubsubMessage.getMessageId()+" Data:"+pubsubMessage.getData().toStringUtf8()+")");
        Map<String, String> atribs=pubsubMessage.getAttributesMap();
        String imageName = null;
        for (String key : atribs.keySet()) {
            System.out.println("Msg Attribute:("+key+", "+atribs.get(key)+")");
            imageName = atribs.get(key);
        }

        if(storage == null)
            initStorage();

        try {
            downloadBlobFromBucket(imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DetectLandmarks.detectLandmarksGcs(storage, BUCKET_NAME, imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
        ackReplyConsumer.ack();
    }

    public void initStorage(){
        StorageOptions storageOptions = StorageOptions.getDefaultInstance();
        storage = storageOptions.getService();
        String projID = storageOptions.getProjectId();
        if (projID != null) System.out.println("Current Project ID:" + projID);
        else {
            System.out.println("The environment variable GOOGLE_APPLICATION_CREDENTIALS isn't well defined!!");
            System.exit(-1);
        }
    }

    public byte[] downloadBlobFromBucket(String blobName) throws IOException {
        String bucketName = BUCKET_NAME;

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
}
