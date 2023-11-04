import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private static final Gson gson = new GsonBuilder().create();
    private static Channel channel;


    private static int Menu() {
        String op;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println();
            System.out.println("MENU:");
            System.out.println("\t0 - Send Message");
            System.out.println("\t99 - Exit");
            System.out.println();
            System.out.println("Choose an Option:");
            op = scan.nextLine();
        } while (!op.equals("0") && !op.equals("99"));
        return Integer.parseInt(op);
    }

    public static void main(String[] args) {
        try {
            if(args.length < 3){
                System.out.println("Need more parameters: Pubsub IP, Pubsub Port and Exchange Name");
                return;
            }

            String IP = args[0];
            int pubsubPort = Integer.parseInt(args[1]);
            String globalExchangeName = args[2];

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(IP);
            factory.setPort(pubsubPort);

            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            String clientExchangeName = UUID.randomUUID().toString();
            System.out.println("Client Exchange: " + clientExchangeName);
            String queueName = UUID.randomUUID().toString();
            System.out.println("Client Queue: " + queueName);

            CreateClientExchange(clientExchangeName, queueName);

            // Send a message to exchange
            boolean end = false;
            while (!end) {
                int option = Menu();
                switch (option) {
                    case 0:
                        String addressSrc = readline("Source Address?");
                        String addressDest = readline("Destination Address?");
                        String group = readline("Region?");
                        String IDRequest = UUID.randomUUID().toString();

                        ClientRequest request = new ClientRequest(addressSrc, addressDest, clientExchangeName, group,
                                IDRequest);

                        // converter string em byte[]
                        byte[] binData = gson.toJson(request).getBytes(StandardCharsets.UTF_8);

                        channel.basicPublish(globalExchangeName, "", true, null, binData);
                        System.out.println("Message Sent:\n" + request);
                        break;
                    case 99:
                        end = true;
                        break;
                }
            }
            channel.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void CreateClientExchange(String exchangeName, String queueName) throws IOException {
        //durable true if we are declaring a durable exchange (the exchange will survive a server restart)
        //Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable) throws IOException;
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, false);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, "");

        ClientCallback clientCallback = new ClientCallback(channel, gson);
        ClientCallbackCancel clientCancel = new ClientCallbackCancel();

        channel.basicConsume(queueName, false, clientCallback, clientCancel);
    }

    private static String readline(String msg) {
        Scanner in = new Scanner(System.in);
        System.out.println(msg);
        return in.nextLine();
    }

}
