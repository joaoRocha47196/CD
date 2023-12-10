package clientapp.servercallers;

import clientapp.StreamObservers.GetServerEndpointStreamObserver;
import crstubs.CRServiceGrpc;
import crstubs.GetServerRequest;
import crstubs.ServerEndpoint;
import io.grpc.ManagedChannel;

import java.util.concurrent.CompletableFuture;

public class RegisterServerCaller {
    private CRServiceGrpc.CRServiceStub registerServerStub;

    public RegisterServerCaller(ManagedChannel channel) {
        this.registerServerStub = CRServiceGrpc.newStub(channel);
    }

    public CompletableFuture<ServerEndpoint> getServerEndpoint() {
        GetServerRequest request = GetServerRequest.newBuilder()
            .setClientId("clientTest")
            .build();
        GetServerEndpointStreamObserver getServerEndpointStrmObsrvr = new GetServerEndpointStreamObserver();
        registerServerStub.getServerEndpoint(request, getServerEndpointStrmObsrvr);
        return getServerEndpointStrmObsrvr.getFuture();
    }
}