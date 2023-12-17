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
        System.err.println("Error in summary request: " + t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        System.out.println("Resume Sales completed.");
    }
}