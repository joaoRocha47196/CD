package userapp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class PointOfSaleApp {
    private static final String RABBITMQ_DEFAULT_HOST = "localhost";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static final int MENU_EXIT_OPTION = 2;

    private static String rabbitMQHost;
    private static int rabbitMQPort;
    private static String exchangeName = "ExgSales";

    public static void main(String[] args) {
        initConnections(args);
        initRabbitMQConnection();
        processClientOperations();
    }

    public static void initConnections(String[] args) {
        if (args.length == 2) {
            rabbitMQHost = args[0];
            rabbitMQPort = Integer.parseInt(args[1]);
        } else {
            rabbitMQHost = RABBITMQ_DEFAULT_HOST;
            rabbitMQPort = RABBITMQ_DEFAULT_PORT;
        }
    }

    static void initRabbitMQConnection() {
        System.out.println("Connect to RabbitMQ server at:" + rabbitMQHost + ":" + rabbitMQPort);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Declare the global fanout exchange ExgSales if it doesn't exist
            channel.exchangeDeclare(exchangeName, "fanout");
            System.out.println("Connected to RabbitMQ successfully!");

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ" + e.getMessage());
        }
    }

    static void publishSale(Channel channel, String message, String productCategory) {
        try {
            // Publish the sale information to the global fanout exchange ExgSales
            channel.basicPublish(exchangeName, productCategory, null, message.getBytes());
            System.out.println("Sale information published successfully!");
        } catch (IOException e) {
            System.out.println("Error publishing sale information");
            e.printStackTrace();
        }
    }

    static void processClientOperations() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int menuOption = getMenuOption();
            switch (menuOption) {
                case 1:
                    System.out.println("Enter the product code: ");
                    String code = sc.nextLine();
                    System.out.println("Enter the product name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter the product category (ALIMENTAR or CASA): ");
                    String productCategory = sc.nextLine();
                    System.out.println("Enter the product quantity: ");
                    String quantity = sc.nextLine();
                    System.out.println("Enter the product price: ");
                    String price = sc.nextLine();
                    float totalPrice = Float.parseFloat(quantity) * Float.parseFloat(price);
                    System.out.println("Enter IVA: ");
                    String iva = sc.nextLine();

                    String saleMessage = buildSaleMessage(code, name, quantity, price, totalPrice, iva);
                    ConnectAndPublishRabbitMQ(saleMessage, productCategory);
                    break;
                case MENU_EXIT_OPTION:
                    System.exit(0);
            }
        }
    }

    private static void ConnectAndPublishRabbitMQ(String saleMessage, String productCategory) {
        try (Connection connection = new ConnectionFactory().newConnection();
            Channel channel = connection.createChannel()) {
            publishSale(channel, saleMessage, productCategory);
        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ");
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

    static String buildSaleMessage(String code, String name, String quantity, String price, float totalPrice, String iva) {
        return String.format(
                "Sale Information:\nProduct Code: %s\nProduct Name: %s\nQuantity: %s\nPrice: %s\nTotal Price: %.2f\nIVA: %s",
                code, name, quantity, price, totalPrice, iva
        );
    }
}

