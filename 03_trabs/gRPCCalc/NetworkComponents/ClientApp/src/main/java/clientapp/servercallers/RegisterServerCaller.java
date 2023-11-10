package clientapp.servercallers;

import clientapp.StreamObservers.GetServerEndpointStreamObserver;
import crstubs.CRServiceGrpc;
import crstubs.GetServerRequest;
import io.grpc.ManagedChannel;

public class RegisterServerCaller {
    private CRServiceGrpc.CRServiceStub registerServerStub;

    public RegisterServerCaller(ManagedChannel channel) {
        this.registerServerStub = CRServiceGrpc.newStub(channel);
    }

    public GetServerEndpointStreamObserver getServerEndpoint() {
        GetServerRequest request = GetServerRequest.newBuilder()
            .setClientId("clientTest")
            .build();
        GetServerEndpointStreamObserver g = new GetServerEndpointStreamObserver();
        registerServerStub.getServerEndpoint(request, new GetServerEndpointStreamObserver());
        return g;
    }
}