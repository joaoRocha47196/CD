package services;

import crstubs.*;
import io.grpc.stub.*;
import StreamObservers.GetServerEndpointStreamObserver;

/**
 * This class implements the protoInterface
 */
public class CRService extends CRServiceGrpc.CRServiceImplBase {

    @Override
    public void getServerEndpoint(GetServerRequest request, StreamObserver<ServerEndpoint> responseObserver) {
        System.out.println(":: Getting Server Endpoint ::");
        GetServerEndpointStreamObserver requests = new GetServerEndpointStreamObserver(responseObserver);

    }
    
}
