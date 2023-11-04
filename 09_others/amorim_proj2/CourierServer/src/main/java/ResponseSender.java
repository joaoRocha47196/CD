import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ResponseSender {
    private final Channel channel;
    private final Gson gson;

    public ResponseSender(Channel channel, Gson gson) {
        this.channel = channel;
        this.gson = gson;
    }

    public void send(String exchangeName, String requestID) {
        String boxID = UUID.randomUUID().toString();
        String chave = UUID.randomUUID().toString();

        ClientResponse response = new ClientResponse(boxID, chave, requestID);

        byte[] bytes = gson.toJson(response).getBytes(StandardCharsets.UTF_8);

        try {
            channel.basicPublish(exchangeName, "", true, null, bytes);
        } catch (IOException e) {
            // ignored
        }
    }
}
