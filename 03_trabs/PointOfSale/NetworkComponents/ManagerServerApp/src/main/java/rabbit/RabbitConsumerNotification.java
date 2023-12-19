package rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

public class RabbitConsumerNotification {
    private static final String QUEUE_NAME = "QueueNotificacao";

    private final String rabbitMQHost;
    private final int rabbitMQPort;
    private Channel rabbitChannel;
    private final String exchangeName;


    public RabbitConsumerNotification(String rabbitMQHost, int rabbitMQPort, String exchangeName) {
        this.rabbitMQHost = rabbitMQHost;
        this.rabbitMQPort = rabbitMQPort;
        this.exchangeName = exchangeName;
    }

    public void initConnection() {
        System.out.println("Connect to RabbitMQ server at:" + rabbitMQHost + ":" + rabbitMQPort);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);
        factory.setUsername("rabbit");
        factory.setPassword("rabbit");

        try {
            Connection connection = factory.newConnection();
            this.rabbitChannel = connection.createChannel();
            System.out.println("Connected to RabbitMQ successfully!");

        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ" + e.getMessage());
        }
    }

    public void declareQueue() {
        try {
            rabbitChannel.exchangeDeclare(exchangeName, "fanout");
            // Declare the queue
            rabbitChannel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // Bind the queue to the exchange
            rabbitChannel.queueBind(QUEUE_NAME, exchangeName, "");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consumeNotifications(CompletableFuture<String> futureNotification) {
        try {
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            RabbitCallbackConsumer workerCallback = new RabbitCallbackConsumer(futureNotification);
            RabbitCallbackCancel cancelCallback = new RabbitCallbackCancel();

            rabbitChannel.basicConsume(QUEUE_NAME, true, workerCallback, cancelCallback);

        } catch (IOException e) {
            System.out.println("Error connecting to RabbitMQ");
        }
    }
}
