package services;

import crstubs.*;
import io.grpc.stub.*;
import repo.Repo;
import srstubs.ServerRegistration;

/**
 * This class implements the protoInterface
 */
public class CRService extends CRServiceGrpc.CRServiceImplBase {


    @Override
    public void getServerEndpoint(GetServerRequest request, StreamObserver<ServerEndpoint> responseObserver) {
        System.out.println(":: Getting Server Endpoint ::");

        ServerRegistration choosenServer = chooseAvailableServer(responseObserver);
        String serverIp = choosenServer.getServerIp();
        int serverPort = choosenServer.getServerPort();
        ServerEndpoint serverEndpoint = createServerEndpoint(serverIp, serverPort);

        responseObserver.onNext(serverEndpoint);
        responseObserver.onCompleted();
    }

    private ServerEndpoint createServerEndpoint(String serverIp, int serverPort){
        return ServerEndpoint.newBuilder()
                .setServerIp(serverIp) // Example endpoint; replace with your logic
                .setServerPort(serverPort) // Example port; replace with your logic
                .build();
    }
    public ServerRegistration chooseAvailableServer(StreamObserver<ServerEndpoint> responseObserver) {
        if (Repo.isEmpty())
            responseObserver.onError(new Exception("No servers registered"));
        return Repo.getServer();
    }
    
}
