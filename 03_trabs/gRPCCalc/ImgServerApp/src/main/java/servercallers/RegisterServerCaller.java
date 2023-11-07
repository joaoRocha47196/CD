package servercallers;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import srstubs.EmptyResponse;
import srstubs.SRServiceGrpc;
import srstubs.ServerRegistration;


public class RegisterServerCaller {
    private SRServiceGrpc.SRServiceStub registerStub;

    public RegisterServerCaller(ManagedChannel channel) {
        this.registerStub = SRServiceGrpc.newStub(channel);
    }


    public void registerServer() {
        //registerStub.registerServer();
        //registerStub.registerServer(ServerRegistration request, StreamObserver<EmptyResponse> responseObserver)
        // TODO
    }
}