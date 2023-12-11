package workerapp;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WorkerCallback implements DeliverCallback {
    private final String queueName;

    public WorkerCallback(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String routingKey = delivery.getEnvelope().getRoutingKey();

        System.out.println("Message Received with routing key:" + routingKey);
        System.out.println("Received Message: '" + message + "'");
        processAndWriteToFile(queueName, message);
    }

    private void processAndWriteToFile(String queueName, String saleMessage) {
        // Your existing logic for processing and writing to a file
        // ...
    }
}