package services;


import StreamObservers.ProcessImageStreamObserver;
import csstubs.*;
import io.grpc.stub.StreamObserver;

public class CSService extends CSServiceGrpc.CSServiceImplBase {

    @Override
    public StreamObserver<ImageRequest> processImage(StreamObserver<ImageIdentifier> responseObserver ){
        System.out.println(":: Submitting image ::");
        ProcessImageStreamObserver requests = new ProcessImageStreamObserver(responseObserver);
        return requests;
    }

    @Override
    public void checkImageStatus(ImageIdentifier request, StreamObserver<ImageResponse> responseObserver){
        // Todo

    }

    @Override
    public void downloadProcessedImage(ImageIdentifier request,StreamObserver<ImageResponse> responseObserver){
        // Todo
    }



}
