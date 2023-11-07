package services;

import io.grpc.stub.StreamObserver;
import srstubs.*;

public class SRService extends SRServiceGrpc.SRServiceImplBase {

    @Override
    public void registerServer(ServerRegistration request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO
    }


}
