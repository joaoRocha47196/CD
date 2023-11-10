package clientapp.StreamObservers;
import crstubs.ServerEndpoint;
import io.grpc.stub.StreamObserver;

public class GetServerEndpointStreamObserver implements StreamObserver<ServerEndpoint> {
    public String ip;
    public int port;
    @Override
    public void onNext(ServerEndpoint serverEndpoint) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Server Endpoint Info        ║");
        System.out.println("║------------------------------------║");
        System.out.println("║ Server IP: " + serverEndpoint.getServerIp());
        System.out.println("║ Server Port: " + serverEndpoint.getServerPort());
        System.out.println("║------------------------------------║");
        System.out.println("╚════════════════════════════════════╝");
        this.ip =serverEndpoint.getServerIp();
        this.port = serverEndpoint.getServerPort();
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
