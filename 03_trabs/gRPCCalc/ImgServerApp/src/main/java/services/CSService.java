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
    public void checkImageStatus(ImageIdentifier request, StreamObserver<StatusResponse> responseObserver){
        System.out.println(":: Checking Image Status ::");
        StatusResponse status = StatusResponse.newBuilder()
                .setStatus() //ver o estado atual da imagem na vm (se ja esta pronta para ser downloaded)
                .build();

        responseObserver.onNext(status);
        responseObserver.onCompleted();


    }

    @Override
    public void downloadProcessedImage(ImageIdentifier request,StreamObserver<ImageResponse> responseObserver){
        System.out.println(":: Getting marked image ::");

    }



}
