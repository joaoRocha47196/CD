package clientapp.servercallers;

import clientapp.StreamObservers.DownloadProcessedImageStreamObserver;
import clientapp.StreamObservers.ProcessImageStreamObserver;
import com.google.protobuf.ByteString;
import csstubs.*;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * This class uses ImageServerStubs
 */
public class ImageServerCaller {
    private static final int CHUNCK_SIZE = 32 * 1024;

    private final CSServiceGrpc.CSServiceStub imageServerStub;

    public ImageServerCaller(ManagedChannel channel) {
        this.imageServerStub = CSServiceGrpc.newStub(channel);
    }

    public void processImage(String imagePath, String[] keywords) {
        StreamObserver<ImageRequest> streamObserver = imageServerStub.processImage(new ProcessImageStreamObserver(imagePath));

        try {
            Path path = Paths.get(imagePath);
            String filename = String.valueOf(path.getFileName());
            String[] filenameParts = filename.split("\\.");
            String basename = filenameParts[0];
            String extension = filenameParts[1];

            InputStream inputStream = Files.newInputStream(path);
            byte[] bytes = new byte[CHUNCK_SIZE];

            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                ByteString imageData = ByteString.copyFrom(bytes, 0, size);
                List<String> keyWords = Arrays.asList(keywords);
                Metadata metadata = createMetaData(basename, extension);
                ImageRequest imageRequest = createImageRequest(imageData, keyWords, metadata);

                streamObserver.onNext(imageRequest);
            }
            inputStream.close();
            streamObserver.onCompleted();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkImageStatus(String imageId) {
        /*
        ImageIdentifier request = ImageIdentifier.newBuilder()
                .setIdentifier(imageId)
                .build();
        CheckImageStatusStreamObserver response = new CheckImageStatusStreamObserver();
        imageServerStub.checkImageStatus(request, response);
           */
    }

    public void downloadProcessedImage(String imageId, String destinationPath){
        ImageIdentifier request = createImageIdentifier(imageId);
        DownloadProcessedImageStreamObserver response = new DownloadProcessedImageStreamObserver(destinationPath);
        imageServerStub.downloadProcessedImage(request, response);
    }

    private ImageIdentifier createImageIdentifier(String imageId){
        return ImageIdentifier.newBuilder()
            .setIdentifier(imageId)
            .build();
    }

    private ImageRequest createImageRequest(ByteString imageData, List<String> keyWords, Metadata metadata){
        return ImageRequest.newBuilder()
            .setImageData(imageData)
            .addAllKeywords(keyWords)
            .setMetadata(metadata)
            .build();
    }

    private Metadata createMetaData(String baseName, String extension){
        return Metadata.newBuilder()
            .setName(baseName)
            .setType(extension)
            .build();
    }

}
