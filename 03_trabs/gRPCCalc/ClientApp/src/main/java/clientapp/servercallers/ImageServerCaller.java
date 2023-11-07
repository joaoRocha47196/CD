package clientapp.servercallers;

import clientapp.StreamObservers.CheckImageStatusStreamObserver;
import clientapp.StreamObservers.DownloadProcessedImageStreamObserver;
import clientapp.StreamObservers.ProcessImageStreamObserver;
import com.google.protobuf.ByteString;
import csstubs.CSServiceGrpc;
import csstubs.ImageIdentifier;
import csstubs.ImageRequest;
import csstubs.ImageResponse;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This class uses ImageServerStubs
 */
public class ImageServerCaller {
    private static final int CHUNCK_SIZE = 32 * 1024;

    private CSServiceGrpc.CSServiceStub imageServerStub;

    public ImageServerCaller(ManagedChannel channel) {
        this.imageServerStub = CSServiceGrpc.newStub(channel);
    }

    public void processImage(String imagePath, String[] keywords) {
        StreamObserver<ImageRequest> streamObserver = imageServerStub.processImage(new ProcessImageStreamObserver(imagePath));

        try {
            Path path = Paths.get(imagePath);
            InputStream inputStream = Files.newInputStream(path);
            byte[] bytes = new byte[CHUNCK_SIZE];

            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                ImageRequest imageRequest = ImageRequest.newBuilder()
                        .setImageData(ByteString.copyFrom(bytes, 0, size))
                        .addAllKeywords(Arrays.asList(keywords))
                        .build();
                streamObserver.onNext(imageRequest);
            }
            inputStream.close();
            streamObserver.onCompleted();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkImageStatus(String imageId) {
        ImageIdentifier request = ImageIdentifier.newBuilder()
                .setIdentifier(imageId)
                .build();
        imageServerStub.checkImageStatus(request, new CheckImageStatusStreamObserver());
    }

    public void downloadProcessedImage(String imageId, String destinaionPath){
        ImageIdentifier request = ImageIdentifier.newBuilder()
                .setIdentifier(imageId)
                .build();
        imageServerStub.downloadProcessedImage(request, new DownloadProcessedImageStreamObserver(destinaionPath));
    }

    public StreamObserver<ImageRequest> processImage(StreamObserver<ImageIdentifier> responseObserver ) {
        return null;
        // Todo
    }
    public void checkImageStatus(ImageIdentifier request, StreamObserver<ImageResponse> responseObserver){
        // Todo
    }
    public void downloadProcessedImage(ImageIdentifier request,StreamObserver<ImageResponse> responseObserver){
        // Todo
    }
}
