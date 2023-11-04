import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientCallback implements DeliverCallback {
    private final Channel channel;
    private final Gson gson;

    public ClientCallback(Channel channel, Gson gson){
        this.channel = channel;
        this.gson = gson;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String recMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String routingKey = delivery.getEnvelope().getRoutingKey();
        long deliverTag = delivery.getEnvelope().getDeliveryTag();
        System.out.println("Message Received:" + routingKey+":" + recMessage);

        ClientResponse response = gson.fromJson(recMessage, ClientResponse.class);

        System.out.println(response);

        channel.basicAck(deliverTag,false);
    }
}
