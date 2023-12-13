package rabbit;

import com.rabbitmq.client.*;


import java.nio.charset.StandardCharsets;

public class RabbitCallbackConsumer implements DeliverCallback {

    private static final String GLUSTER_DIRECTORY_PATH = "/var/sharedfiles";
    private String filename;

    public RabbitCallbackConsumer() {

    }

    @Override
    public void handle(String consumerTag, Delivery delivery) {
        this.filename = new String(delivery.getBody(), StandardCharsets.UTF_8);
    }
}

