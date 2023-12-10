package clientapp.StreamObservers;
import crstubs.ServerEndpoint;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;

public class GetServerEndpointStreamObserver implements StreamObserver<ServerEndpoint> {
    private CompletableFuture<ServerEndpoint> future = new CompletableFuture<>();

    public CompletableFuture<ServerEndpoint> getFuture() {
        return future;
    }

    @Override
    public void onNext(ServerEndpoint serverEndpoint) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Server Endpoint Info        ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Server IP: " + serverEndpoint.getServerIp());
        System.out.println("║ Server Port: " + serverEndpoint.getServerPort());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
        future.complete(serverEndpoint);
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error: " + t.getMessage());
        future.completeExceptionally(t);
    }

    @Override
    public void onCompleted() {
        System.out.println("Server endpoint retrieval completed.");
    }
}
