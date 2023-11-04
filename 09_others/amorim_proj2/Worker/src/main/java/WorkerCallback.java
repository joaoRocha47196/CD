import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WorkerCallback implements DeliverCallback {
    private final SpreadConnection spreadConnection;
    private final Channel channel;
    private final Gson gson;

    public WorkerCallback(Channel channel, SpreadConnection spreadConnection){
        this.spreadConnection = spreadConnection;
        this.gson = new GsonBuilder().create();
        this.channel = channel;
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String recMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
        String routingKey = delivery.getEnvelope().getRoutingKey();
        long deliverTag = delivery.getEnvelope().getDeliveryTag();

        System.out.println("Message Received with routing key:" + routingKey);

        ClientRequest requestClient = gson.fromJson(recMessage, ClientRequest.class);
        System.out.println("Message:" + requestClient.toString() + "\n");
        this.channel.basicAck(deliverTag,false);

        Message message = new Message();
        message.type = MessageType.REQUEST;
        message.request = new Request(requestClient.getIDRequest(), requestClient.getAddressSrc(),
                requestClient.getAddressDest(), requestClient.getExchangeName());

        SpreadMessage msg = new SpreadMessage();
        msg.setData(gson.toJson(message).getBytes(StandardCharsets.UTF_8));
        msg.addGroup(requestClient.getGroup());
        msg.setSafe();
        try {
            spreadConnection.multicast(msg);
        } catch (SpreadException e) {
            // ignored
        }
    }
}
