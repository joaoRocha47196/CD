import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggerCallback implements DeliverCallback {
    private final Channel channel;

    public LoggerCallback(Channel channel){
        this.channel = channel;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String recMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String routingKey = delivery.getEnvelope().getRoutingKey();
        long deliverTag = delivery.getEnvelope().getDeliveryTag();

        System.out.println("Message Received:" + routingKey+":" + recMessage);

        channel.basicAck(deliverTag,false);
    }
}
