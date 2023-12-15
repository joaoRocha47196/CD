package app.StreamObservers;


import io.grpc.stub.StreamObserver;
import umstubs.NotificationResponse;

public class ResumeSalesStreamObserver implements StreamObserver<NotificationResponse> {
    private String exchangeName;

    public ResumeSalesStreamObserver(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Override
    public void onNext(NotificationResponse notification) {
        System.out.println("Resume Sales notification received: " + notification.getMessage());
    }

    @Override
    public void onError(Throwable t) {
        // Handle the onError event (error in summary request)
        System.err.println("Error in summary request: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Resume Sales completed.");
    }
}