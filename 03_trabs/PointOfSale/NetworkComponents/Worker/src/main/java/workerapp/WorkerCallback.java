package workerapp;

import com.rabbitmq.client.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WorkerCallback implements DeliverCallback {

    private static final String GLUSTER_DIRECTORY_PATH = "/var/sharedfiles";
    private final String queueName;
    private final String workerName;

    public WorkerCallback(String queueName, String workerName) {
        this.queueName = queueName;
        this.workerName = workerName;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String routingKey = delivery.getEnvelope().getRoutingKey();

        System.out.println("Message Received with routing key:" + routingKey);
        System.out.println("Received Message: '" + message + "'");
        writeToFile(message, routingKey);
    }

    private void writeToFile(String content, String routingKey) {
        String fileName = "/var/sharedfiles/" + routingKey.toLowerCase() + "/file_" + workerName + ".txt";

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(content + "\n");
            System.out.println(" [x] Sale information written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

