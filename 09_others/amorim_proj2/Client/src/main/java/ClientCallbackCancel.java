import com.rabbitmq.client.CancelCallback;

public class ClientCallbackCancel implements CancelCallback {
    @Override
    public void handle(String consumerTag) {
        System.out.println("CANCEL Received! "+consumerTag);
    }
}
