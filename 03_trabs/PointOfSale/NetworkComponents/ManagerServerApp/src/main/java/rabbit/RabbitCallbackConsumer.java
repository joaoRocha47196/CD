package rabbit;

import com.rabbitmq.client.*;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class RabbitCallbackConsumer implements DeliverCallback {

    private final CompletableFuture<String> futureNotification;

    public RabbitCallbackConsumer(CompletableFuture<String> future) {
        this.futureNotification = future;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) {
        String notficationMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("Notification Message Received: '" + notficationMessage + "'");

        // Complete Notification So the user can receive the message
        futureNotification.complete(notficationMessage);
    }
}

