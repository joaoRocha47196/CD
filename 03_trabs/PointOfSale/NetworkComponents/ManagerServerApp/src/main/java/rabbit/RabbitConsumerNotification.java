package rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConsumerNotification {
    private static final String EXCHANGE_NAME = "ExgSales";

    private String rabbitMQHost;
    private int rabbitMQPort;
    private Channel rabbitChannel;
    private String queueName;


    public RabbitConsumerNotification(String rabbitMQHost, int rabbitMQPort) {
        this.rabbitMQHost = rabbitMQHost;
        this.rabbitMQPort = rabbitMQPort;

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
            rabbitChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // Declare a temporary queue and bind it to the fanout exchange
            this.queueName = rabbitChannel.queueDeclare().getQueue();
            rabbitChannel.queueBind(queueName, EXCHANGE_NAME, "");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNotification() {
        try {
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            RabbitCallbackConsumer workerCallback = new RabbitCallbackConsumer();
            RabbitCallbackCancel cancelCallback = new RabbitCallbackCancel();

            rabbitChannel.basicConsume(queueName, true, workerCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.out.println("Error connecting to RabbitMQ");
        }
    }
}
