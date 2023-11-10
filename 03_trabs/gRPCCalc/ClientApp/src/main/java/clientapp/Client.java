package clientapp;

import clientapp.servercallers.ImageServerCaller;
import clientapp.servercallers.RegisterServerCaller;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;


public class Client {

    private static final int MENU_EXIT_OPTION = 9;
    private static final int DEFAULT_PORT = 8500;
    private static final String DEFAULT_IP = "localhost";

    private static String svcIP; // "35.246.73.129";
    private static int svcPort;
    private static ImageServerCaller imageServerCaller;
    private static RegisterServerCaller registerServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initClient();
        processOperations();
    }

    public static void initConnections(String[] args){
        if (args.length == 2) {
            svcIP = args[0];
            svcPort = Integer.parseInt(args[1]);
        }
        else {
            svcPort = DEFAULT_PORT;
            svcIP = DEFAULT_IP;
        }
    }

    static void initClient(){
        System.out.println("connect to register server in:" + svcIP + ":" + svcPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();

        imageServerCaller = new ImageServerCaller(channel);
        registerServerCaller = new RegisterServerCaller(channel);
    }

    static void processOperations(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            int menuOption = getMenuOption();
            switch (menuOption) {
                case 1:
                    registerServerCaller.getServerEndpoint();
                    break;

                case 2:
                    System.out.println("\nInsert the image path: ");
                    String imagePath = sc.nextLine();
                    System.out.print("Enter keywords (space-separated): ");
                    String keywordsInput = sc.nextLine();
                    String[] keywords = keywordsInput.split(" ");
                    imageServerCaller.processImage(imagePath, keywords);
                    break;

                case 3:
                    System.out.println("\nInsert the image identifier: ");
                    String imageId = sc.nextLine();
                    imageServerCaller.checkImageStatus(imageId);
                    break;

                case 4:
                    System.out.println("\nInsert the image identifier: ");
                    String imgId = sc.nextLine();
                    System.out.println("\nInsert the image download path: ");
                    String destinationPath = sc.nextLine();
                    imageServerCaller.downloadProcessedImage(imgId, destinationPath);
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
        System.out.println("║ 1 ║ Get Server Endpoint    ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 2 ║ -------------          ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 3 ║ -------------          ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 4 ║ -------------          ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 5 ║ Exit                   ║");
        System.out.println("╚═══╩════════════════════════╝");
    }

    private static boolean isValidOption(int option) {
        return (option >= 1 && option <= 5) || option == MENU_EXIT_OPTION;
    }

}
