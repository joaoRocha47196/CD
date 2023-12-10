package userapp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


public class PointOfSaleApp {
    private static final String RABBITMQ_DEFAULT_HOST = "34.175.229.136";
    private static final int RABBITMQ_DEFAULT_PORT = 7008;
    private static final int MENU_EXIT_OPTION = 2;

    private static String rabbitMQHost;
    private static int rabbitMQPort;



    public static void main(String[] args) {
        initConnections(args);
        initRabbitMQConnection();
        processClientOperations();
    }

    public static void initConnections(String[] args){
        if (args.length == 2) {
            rabbitMQHost = args[0];
            rabbitMQPort = Integer.parseInt(args[1]);
        }
        else {
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
        }
    }

    static void initRabbitMQConnection(){
        System.out.println("connect to RabbitMQ server in:" + rabbitMQHost + ":" + rabbitMQPort);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Declare any necessary RabbitMQ entities (exchanges, queues, etc.)
            // For simplicity, you can skip this if they already exist.

            // Set up the RabbitMQ channel for publishing messages
            // (you might want to adjust this based on your RabbitMQ setup)
            channel.exchangeDeclare("your_exchange_name", "fanout");

            // Assuming you have a direct exchange named "your_exchange_name"
            // You can change the exchange type and name based on your setup

            // Set up any other configurations as needed

            // Close the channel and connection
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ");
            e.printStackTrace();
        }
    }


    static ManagedChannel createChannel(String ip, int port){
        return ManagedChannelBuilder.forAddress(ip, port)
            .usePlaintext()
            .build();
    }

    static void processClientOperations(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            int menuOption = getMenuOption();
            switch (menuOption) {
                case 1:
                    System.out.println("Enter the product code: ");
                    String code = sc.nextLine();
                    System.out.println("Enter the product name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter the product quantity: ");
                    String quantity = sc.nextLine();
                    System.out.println("Enter the product price: ");
                    String price = sc.nextLine();
                    float totalPrice = Float.parseFloat(quantity) * Float.parseFloat(price);
                    System.out.println("Enter IVA: ");
                    String iva = sc.nextLine();

                    //Send Sale to pub/sub broker (RabbitMQ)


                    break;
                case MENU_EXIT_OPTION:
                    System.exit(0);
            }
        }
    }

    static int getMenuOption() {
        Scanner scan = new Scanner(System.in);
        int option;
        do {
            printMenuOptions();
            System.out.print("Enter an Option: \n");
            option = scan.nextInt();
        } while (!isValidOption(option));
        return option;
    }

    static void printMenuOptions() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║            MENU            ║");
        System.out.println("╠═══╦════════════════════════╣");
        System.out.println("║ 1 ║ Publish Sale           ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 2 ║ Exit                   ║");
        System.out.println("╚═══╩════════════════════════╝");
    }

    private static boolean isValidOption(int option) {
        return (option >= 1 && option <= 2) || option == MENU_EXIT_OPTION;
    }

}
