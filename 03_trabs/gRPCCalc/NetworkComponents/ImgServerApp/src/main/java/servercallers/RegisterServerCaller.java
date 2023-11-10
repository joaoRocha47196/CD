package servercallers;

import StreamObservers.RegisterServerStreamObserver;
import io.grpc.ManagedChannel;
import srstubs.EmptyResponse;
import srstubs.SRServiceGrpc;
import srstubs.ServerRegistration;

import java.util.UUID;


public class RegisterServerCaller {
    private final SRServiceGrpc.SRServiceStub registerStub;

    public RegisterServerCaller(ManagedChannel channel) {
        this.registerStub = SRServiceGrpc.newStub(channel);
    }

    public void registerServer(String serverIp, int serverPort) {
        ServerRegistration request = createServerRegistration(serverIp, serverPort);
        RegisterServerStreamObserver response = new RegisterServerStreamObserver();
        registerStub.registerServer(request, response);
    }

    private ServerRegistration createServerRegistration(String serverIp, int serverPort){
        String id = UUID.randomUUID().toString();
        return ServerRegistration.newBuilder()
            .setServerId(id)
            .setServerIp(serverIp)
            .setServerPort(serverPort)
            .build();
    }
}