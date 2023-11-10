package services;

import io.grpc.stub.StreamObserver;
import repo.Repo;
import srstubs.*;
public class SRService extends SRServiceGrpc.SRServiceImplBase {

    @Override
    public void registerServer(ServerRegistration request, StreamObserver<EmptyResponse> responseObserver) {
        System.out.println("Received registration request from server: " + request.getServerIp());
        Repo.addServer(request);
        System.out.println("Server Registered:");
        System.out.println("Name: " + request.getServerId());
        System.out.println("IP: " + request.getServerIp());
        System.out.println("Port: " + request.getServerPort());

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}

