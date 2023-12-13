package workerapp;

import com.rabbitmq.client.*;

import java.io.File;
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
        String directoryPath = "/var/sharedfiles/";
        String fileName = directoryPath + "file_" + workerName + ".txt";

        try {
            File file = new File(fileName);
            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Use FileWriter with append mode
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(content + "\n");
                System.out.println(" [x] Sale information written to file: " + fileName);
            }catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

