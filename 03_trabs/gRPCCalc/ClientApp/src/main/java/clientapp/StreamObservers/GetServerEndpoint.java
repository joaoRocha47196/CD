package clientapp.StreamObservers;

import io.grpc.stub.StreamObserver;

public class GetServerEndpoint implements StreamObserver<ServerEndpoint> {
    @Override
    public void onNext(ServerEndpoint serverEndpoint) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Server Endpoint Info        ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Server IP: " + serverEndpoint.getServerIp());
        System.out.println("║ Server Port: " + serverEndpoint.getServerPort());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Server endpoint retrieval completed.");
    }
}
