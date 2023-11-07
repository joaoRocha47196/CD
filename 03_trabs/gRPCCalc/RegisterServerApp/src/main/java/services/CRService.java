package services;

import crstubs.*;
import io.grpc.stub.*;

/**
 * This class implements the protoInterface
 */
public class CRService extends CRServiceGrpc.CRServiceImplBase {

    @Override
    public void getServerEndpoint(GetServerRequest request, StreamObserver<ServerEndpoint> responseObserver) {
        // TODO
    }
    
}
