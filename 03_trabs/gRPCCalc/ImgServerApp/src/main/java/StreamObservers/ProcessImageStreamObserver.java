package StreamObservers;

import csstubs.ImageIdentifier;
import csstubs.ImageRequest;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProcessImageStreamObserver implements StreamObserver<ImageRequest> {

    private ByteArrayOutputStream writer;
    private StreamObserver<ImageIdentifier> replies;


    public ProcessImageStreamObserver(StreamObserver<ImageIdentifier> responseObserver){
        this.replies = responseObserver;
        //todo
    }

    @Override
    public void onNext(ImageRequest imageRequest) {
        try {
            writer.write(imageRequest.getImageData().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        //change status value
    }

    @Override
    public void onCompleted() {

    }
}
