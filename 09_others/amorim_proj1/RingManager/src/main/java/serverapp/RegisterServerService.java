package serverapp;

import io.grpc.stub.StreamObserver;
import rpcRegisterStub.RegisterServiceGrpc;
import rpcRegisterStub.*;

import java.util.UUID;

public class RegisterServerService extends RegisterServiceGrpc.RegisterServiceImplBase {
    private final Repo repo;
    private final int maxServers;

    public RegisterServerService(Repo repo, int maxServers) {
        this.repo = repo;
        this.maxServers = maxServers;
    }


    @Override
    public void registServer(Server request, StreamObserver<Server> responseObserver) {
        if(repo.totalServers() == maxServers) {
            responseObserver.onNext(Server.newBuilder().build());
            responseObserver.onCompleted();
            return;
        }
        Repo.ServerInfo serverInfo = new Repo.ServerInfo(request.getIp(), request.getPort());
        int idx = repo.addServer(serverInfo);
        while (repo.totalServers() != maxServers)
            Thread.yield();

        Repo.ServerInfo nextServer = repo.getServer(idx == maxServers-1 ? 0 : idx + 1);
        String id = UUID.randomUUID().toString();
        Server server = Server.newBuilder()
                .setIp(nextServer.ip)
                .setPort(nextServer.port)
                .setId(id)
                .build();
        System.out.printf("new server with IP: %s, Port: %d and ID: %s\n", request.getIp(), request.getPort(), id);
        responseObserver.onNext(server);
        responseObserver.onCompleted();
    }
}
