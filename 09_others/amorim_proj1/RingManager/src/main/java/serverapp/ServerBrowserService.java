package serverapp;

import io.grpc.stub.StreamObserver;
import rpcBrowserStub.Server;
import rpcBrowserStub.ServerBrowserServiceGrpc;
import rpcBrowserStub.Void;

public class ServerBrowserService extends ServerBrowserServiceGrpc.ServerBrowserServiceImplBase {
    private final Repo repo;
    private int nextServer = 0;

    public ServerBrowserService(Repo repo) {
        this.repo = repo;
    }

    @Override
    public void getKvServer(Void request, StreamObserver<Server> responseObserver) {
        Repo.ServerInfo serverInfo = repo.getServer(nextServer++);
        if (nextServer == repo.totalServers())
            nextServer = 0;
        Server server = Server.newBuilder()
                .setIp(serverInfo.ip)
                .setPort(serverInfo.port)
                .build();
        responseObserver.onNext(server);
        responseObserver.onCompleted();
    }
}
