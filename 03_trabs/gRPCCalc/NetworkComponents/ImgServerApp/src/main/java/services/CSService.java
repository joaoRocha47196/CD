package services;


import StreamObservers.ProcessImageStreamObserver;
import api.MarkImageApp;
import com.google.protobuf.ByteString;
import csstubs.*;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CSService extends CSServiceGrpc.CSServiceImplBase {

    @Override
    public StreamObserver<ImageRequest> processImage(StreamObserver<ImageIdentifier> responseObserver ){
        System.out.println(":: Submitting image ::");
        ProcessImageStreamObserver requests = new ProcessImageStreamObserver(responseObserver);
        return requests;
    }

    @Override
    public void checkImageStatus(ImageIdentifier request, StreamObserver<StatusResponse> responseObserver){
        System.out.println(":: Checking Image Status ::");

        /*
        StatusResponse status = StatusResponse.newBuilder()
                .setStatus() //ver o estado atual da imagem na vm (se ja esta pronta para ser downloaded)
                .build();

        responseObserver.onNext(status);
        responseObserver.onCompleted();
        */




    }

    @Override
    public void downloadProcessedImage(ImageIdentifier request, StreamObserver<ImageResponse> responseObserver) {
        System.out.println(":: Getting marked image ::");

        String imageID = request.getIdentifier();
        String[] filenameParts = imageID.split("\\.");
        String basename = filenameParts[0];
        String extension = filenameParts[1];

        try {
            byte[] imageBytes = null;//get annotated image from vm

            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            byte[] bytes = new byte[4096];
            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                Metadata metadata = createMetaData(basename, extension);
                ByteString bs = ByteString.copyFrom(bytes, 0, size);
                ImageResponse response = createImageResponse(bs, metadata);
                responseObserver.onNext(response);
            }
            inputStream.close();
            responseObserver.onCompleted();
            System.out.println("Finished request");
        } catch (IOException e) {
            e.printStackTrace();
            responseObserver.onError(new Throwable());
        }
    }

    private ImageResponse createImageResponse(ByteString bs, Metadata metadata){
        return ImageResponse.newBuilder()
            .setProcessedImage(bs)
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
