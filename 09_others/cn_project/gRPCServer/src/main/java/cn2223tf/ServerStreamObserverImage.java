package cn2223tf;


import com.google.api.core.ApiFuture;
import com.google.cloud.WriteChannel;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

public class ServerStreamObserverImage implements StreamObserver<ImageUploadRequest> {

    public static String BUCKET_NAME;
    private ByteArrayOutputStream writer;
    private StreamObserver<IdentifierResponse> replies;
    private Storage storage;
    private String filename;
    private String filetype;
    private Status status = Status.SUCCESS;


    public ServerStreamObserverImage(StreamObserver<IdentifierResponse> responseObserver, Storage storage, String bucketName) {
        this.replies = responseObserver;
        this.storage = storage;
        this.filename = "";
        this.filetype = "";
        this.writer = new ByteArrayOutputStream();
        this.BUCKET_NAME = bucketName;
    }

    @Override
    public void onNext(ImageUploadRequest imageUploadRequest) {
        try {
            if(imageUploadRequest.getImage().hasMetadata()) {
                setMetadata(imageUploadRequest.getImage().getMetadata());
            }
            writer.write(imageUploadRequest.getImage().getContent().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        status = Status.FAILURE;
        //this.onCompleted();
    }

    @Override
    public void onCompleted() {
        try {
            writer.close();
            String name = uploadImageToCloudStorage(new ByteArrayInputStream(writer.toByteArray()));
            publishMessage("landmarks-detection", name);
            IdentifierResponse response = IdentifierResponse.newBuilder()
                    .setName(name)
                    .setStatus(status)
                    .build();

            replies.onNext(response);
            replies.onCompleted();
            System.out.println("Finished request");
        } catch ( Exception  e) {
            e.printStackTrace();
        }
    }

    private void setMetadata(Metadata metadata) {

        this.filename = metadata.getName();
        this.filetype = metadata.getType();
    }

    public String uploadImageToCloudStorage(ByteArrayInputStream inputImage){

        String bucketName = BUCKET_NAME;
        String blobName;

        if(this.filename.equals("")){
            Date date = new Date();
            blobName = "imagem"+date.getTime()+".jpg";
        } else {
            blobName = this.filename + "-" + UUID.randomUUID() + "." + this.filetype;
        }


        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        if (inputImage.available() > 1_000_000) {
            try (WriteChannel writer = storage.writer(blobInfo)) {
                byte[] buffer = new byte[1024];
                int limit;
                while ((limit = inputImage.read(buffer)) >= 0) {
                    try {
                        writer.write(ByteBuffer.wrap(buffer, 0, limit));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (inputImage.available() > 0){
            byte[] bytes = inputImage.readAllBytes();
            storage.create(blobInfo, bytes);
        }
        System.out.println("Blob " + blobName + " created in bucket " + bucketName);
        return blobName;
    }

    public static void publishMessage(String pubTopicName, String id) throws Exception {

        TopicName topic = TopicName.ofProjectTopicName("cn2223-t1-g14", pubTopicName);
        Publisher publisher = Publisher.newBuilder(topic).build();

        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .putAttributes("id", id)
                .build();
        ApiFuture<String> future = publisher.publish(pubsubMessage);
        String msgID = future.get();
        System.out.println("Message Published with ID=" + msgID);
        publisher.shutdown();
    }
}
