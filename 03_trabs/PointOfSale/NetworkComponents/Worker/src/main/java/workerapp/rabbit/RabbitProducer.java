package workerapp.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {

    private String rabbitMQHost;
    private int rabbitMQPort;
    private Channel rabbitChannel;

    public RabbitProducer(String rabbitMQHost, int rabbitMQPort) {
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

    public void publishMessage(String exchangeName, String filename) {
        try {
            rabbitChannel.exchangeDeclare(exchangeName, "fanout");
            rabbitChannel.basicPublish(exchangeName, "", null, filename.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
