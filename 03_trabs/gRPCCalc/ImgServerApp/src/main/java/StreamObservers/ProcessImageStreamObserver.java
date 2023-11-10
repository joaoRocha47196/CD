package StreamObservers;

import csstubs.ImageIdentifier;
import csstubs.ImageRequest;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessImageStreamObserver implements StreamObserver<ImageRequest> {

    private ByteArrayOutputStream writer;
    private StreamObserver<ImageIdentifier> replies;

    private List<String> keywords;
    private String filename;
    private String filetype;
    //private Status status = Status.SUCCESS;


    public ProcessImageStreamObserver(StreamObserver<ImageIdentifier> responseObserver){
        this.writer = new ByteArrayOutputStream();
        this.replies = responseObserver;
        this.keywords = new ArrayList<>();
        this.filename = "";
        this.filetype = "";
    }

    @Override
    public void onNext(ImageRequest imageRequest) {
        try {
            this.filename = imageRequest.getMetadata().getName();
            this.filetype = imageRequest.getMetadata().getType();
            this.keywords = imageRequest.getKeywordsList();

            writer.write(imageRequest.getImageData().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        //status = Status.FAILURE;
    }

    @Override
    public void onCompleted() {
        try {
            writer.close();
            //logic operations to store the image and his keys (volume of VM)

            String imageId = this.filename + "-" + UUID.randomUUID() + "." + this.filetype;
            ImageIdentifier response = ImageIdentifier.newBuilder()
                    .setIdentifier(imageId)
                    .build();

            replies.onNext(response);
            replies.onCompleted();
        } catch (Exception e) {
            onError(e);
        }
    }
}
