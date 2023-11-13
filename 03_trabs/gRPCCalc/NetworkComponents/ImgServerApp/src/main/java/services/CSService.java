package services;


import StreamObservers.ProcessImageStreamObserver;
import com.google.protobuf.ByteString;
import csstubs.*;
import io.grpc.stub.StreamObserver;

import java.io.*;

public class CSService extends CSServiceGrpc.CSServiceImplBase {

    public static final int CHUCK_SIZE = 32 * 1024;

    @Override
    public StreamObserver<ImageRequest> processImage(StreamObserver<ImageIdentifier> responseObserver ){
        System.out.println(":: Submitting image ::");
        ProcessImageStreamObserver requests = new ProcessImageStreamObserver(responseObserver);
        return requests;
    }

    @Override
    public void checkImageStatus(ImageIdentifier request, StreamObserver<StatusResponse> responseObserver){
        System.out.println(":: Checking Image Status ::");

        String imageID = request.getIdentifier();
        String imagePath = "C:\\Users\\user\\Pictures\\" + imageID; 

        File imageFile = new File(imagePath);
        boolean isImageReady = imageFile.exists();

        StatusResponse status;
        if (isImageReady)
            status = getStatusResponse("Image is ready to download");
        else
            status = getStatusResponse("Image is not yet available");

        responseObserver.onNext(status);
        responseObserver.onCompleted();
    }

    private static StatusResponse getStatusResponse(String statusMessage) {
        StatusResponse status;
        status = StatusResponse.newBuilder()
                .setStatus(statusMessage)
                .build();
        return status;
    }

    @Override
    public void downloadProcessedImage(ImageIdentifier request, StreamObserver<ImageResponse> responseObserver) {
        System.out.println(":: Getting marked image ::");

        String imageID = request.getIdentifier();
        String imagePath = "/usr/images/" + imageID;

        try (FileInputStream fileInputStream = new FileInputStream(imagePath)) {
            byte[] buffer = new byte[CHUCK_SIZE];

            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                ByteString bs = ByteString.copyFrom(buffer, 0, bytesRead);
                Metadata metadata = createMetaData(imageID);
                ImageResponse response = createImageResponse(bs, metadata);
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();

        } catch (IOException e) {
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    private ImageResponse createImageResponse(ByteString bs, Metadata metadata){
        return ImageResponse.newBuilder()
            .setProcessedImage(bs)
            .setMetadata(metadata)
            .build();
    }

    private Metadata createMetaData(String baseName){
        return Metadata.newBuilder()
            .setName(baseName)
            .setType("jpg")
            .build();
    }
}
