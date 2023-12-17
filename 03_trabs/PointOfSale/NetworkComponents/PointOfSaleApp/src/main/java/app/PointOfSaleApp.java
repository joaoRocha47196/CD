package app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class PointOfSaleApp {
    private static final String RABBITMQ_DEFAULT_HOST = "34.134.140.57";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;
    private static final int MENU_EXIT_OPTION = 2;
    private static final String EXCHANGE_NAME = "ExgSales";

    private static String rabbitMQHost;
    private static int rabbitMQPort;
    private static Channel rabbitChannel;

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
        factory.setUsername("rabbit");
        factory.setPassword("rabbit");

        try {
            Connection connection = factory.newConnection();
            rabbitChannel = connection.createChannel();

            rabbitChannel.exchangeDeclare(EXCHANGE_NAME, "direct");

            rabbitChannel.queueDeclare("QueueCasa", true, false, false, null);
            rabbitChannel.queueBind("QueueCasa", EXCHANGE_NAME, "CASA");
            rabbitChannel.queueDeclare("QueueAlimentar", true, false, false, null);
            rabbitChannel.queueBind("QueueAlimentar", EXCHANGE_NAME, "ALIMENTAR");

            System.out.println("Connected to RabbitMQ successfully!");

        } catch (IOException | TimeoutException e) {
            System.out.println("Error connecting to RabbitMQ: " + e.getMessage());
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
                    publishSale(saleMessage, productCategory);
                    break;
                case MENU_EXIT_OPTION:
                    System.exit(0);
            }
        }
    }


    static void publishSale(String saleMessage, String productCategory) {
        try {
            rabbitChannel.basicPublish(EXCHANGE_NAME, productCategory, null, saleMessage.getBytes());
            System.out.println("Sale information published successfully!");
        } catch (IOException e) {
            System.out.println("Error publishing sale information");
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
        return (option >= 1 && option <= 2);
    }

    static String buildSaleMessage(String code, String name, String quantity, String price, float totalPrice, String iva) {
        return String.format(
                "Product Code: %s | Product Name: %s | Quantity: %s | Price: %s | Total Price: %.2f | IVA: %s",
                code, name, quantity, price, totalPrice, iva
        );
    }
}

