package StreamObservers;

import crstubs.GetServerRequest;
import crstubs.ServerEndpoint;
import io.grpc.stub.StreamObserver;

public class GetServerEndpointStreamObserver implements StreamObserver<GetServerRequest> {

    public GetServerEndpointStreamObserver(StreamObserver<ServerEndpoint> responseObserver){

    }

    @Override
    public void onNext(GetServerRequest getServerRequest) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
