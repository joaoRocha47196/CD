import io.grpc.stub.StreamObserver;
import rpcCourierStub.CommunicationServiceGrpc;
import rpcCourierStub.Connection;
import rpcCourierStub.Void;
import rpcCourierStub.Work;
import spread.SpreadException;

public class CommunicationService extends CommunicationServiceGrpc.CommunicationServiceImplBase {
    private final Repo repo;

    public CommunicationService(Repo repo) {
        this.repo = repo;
    }

    @Override
    public void connect(Connection request, StreamObserver<Work> responseObserver) {
        System.out.println("Connecting from spread group");
        joinGroup(request.getRegion());
        repo.client = responseObserver;
    }

    @Override
    public void disconnect(Void request, StreamObserver<Void> responseObserver) {
        System.out.println("Disconnecting from spread group");
        repo.client.onCompleted();
        leaveGroup();
    }

    @Override
    public void free(Void request, StreamObserver<Void> responseObserver) {
        repo.setFree();
        String requestID = repo.getPendingRequestID();
        if (requestID != null) {
            repo.election(requestID);
        }
    }

    @Override
    public void busy(Void request, StreamObserver<Void> responseObserver) {
        repo.setBusy();
    }


    private void joinGroup(String region) {
        try {
            repo.group.join(repo.connection, region);
        } catch (SpreadException e) {
            // ignored
        }
    }

    private void leaveGroup() {
        try {
            repo.group.leave();
        } catch (SpreadException e) {
            // ignored
        }
    }
}
