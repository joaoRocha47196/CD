package workerapp.rabbit;

import com.rabbitmq.client.CancelCallback;

public class RabbitCallbackCancel implements CancelCallback {
    @Override
    public void handle(String consumerTag) {
        System.out.println("CANCEL Received! "+consumerTag);
    }
}
