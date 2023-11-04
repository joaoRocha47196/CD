import com.rabbitmq.client.CancelCallback;

public class WorkerCallbackCancel implements CancelCallback {
    @Override
    public void handle(String consumerTag) {
        System.out.println("CANCEL Received! "+consumerTag);
    }
}
