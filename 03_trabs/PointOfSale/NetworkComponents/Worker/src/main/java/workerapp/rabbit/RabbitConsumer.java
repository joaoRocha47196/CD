package workerapp.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {
    private static final String EXCHANGE_NAME = "ExgSales";

    private String rabbitMQHost;
    private int rabbitMQPort;
    private String routingKey;
    private Channel rabbitChannel;
    private String workerName;

    public RabbitConsumer(String rabbitMQHost, int rabbitMQPort, String routingKey, String workerName) {
        this.rabbitMQHost = rabbitMQHost;
        this.rabbitMQPort = rabbitMQPort;
        this.routingKey = routingKey;
        this.workerName = workerName;
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
            e.printStackTrace();
        }
    }

    public void declareSalesQueue() {
        try {
            if (routingKey.equals("ALIMENTAR")) {
                rabbitChannel.queueDeclare("QueueAlimentar", true, false, false, null);
                rabbitChannel.queueBind("QueueAlimentar", EXCHANGE_NAME, routingKey);
                consumeMessages("QueueAlimentar");
            } else {
                rabbitChannel.queueDeclare("QueueCasa", true, false, false, null);
                rabbitChannel.queueBind("QueueCasa", EXCHANGE_NAME, routingKey);
                consumeMessages( "QueueCasa");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void consumeMessages(String queueName) {
        try {
            System.out.println(" [*] Waiting for messages. To exit, press Ctrl+C");

            RabbitCallbackConsumer workerCallback = new RabbitCallbackConsumer(queueName, workerName);
            RabbitCallbackCancel cancelCallback = new RabbitCallbackCancel();

            rabbitChannel.basicConsume(queueName, false, workerCallback, cancelCallback);
        } catch (IOException e) {
            System.out.println("Error connecting to RabbitMQ");
        }
    }
}
