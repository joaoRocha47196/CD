package clientapp;

import clientapp.servercallers.ImageServerCaller;
import clientapp.servercallers.RegisterServerCaller;
import crstubs.ServerEndpoint;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;


public class Client {
    private static final int MENU_EXIT_OPTION = 4;
    private static final int IMAGE_DEFAULT_PORT = 7008;
    private static final String IMAGE_DEFAULT_IP = "34.175.229.136";
    private static int REGISTER_DEAFULT_PORT = 8009;
    private static final String REGISTER_DEFAULT_IP = "34.175.229.136";

    private static String imageServerIp; // "35.246.73.129";
    private static int imageServerPort;
    private static String registerServerIp; // "35.246.73.129";
    private static int registerServerPort;

    private static ImageServerCaller imageServerCaller;
    private static RegisterServerCaller registerServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initRegisterServerConnection();
        initImageServerConnection();
        processClientOperations();
    }

    public static void initConnections(String[] args){
        if (args.length == 2) {
            //imageServerIp = args[0];
            //imageServerPort = Integer.parseInt(args[1]);
            registerServerIp = args[0];
            registerServerPort = Integer.parseInt(args[1]);
        }
        else {
            //imageServerPort = IMAGE_DEFAULT_PORT;
            //imageServerIp = IMAGE_DEFAULT_IP;
            registerServerPort = REGISTER_DEAFULT_PORT;
            registerServerIp = REGISTER_DEFAULT_IP;
        }
    }

    static void initRegisterServerConnection(){
        System.out.println("connect to register server in:" + registerServerIp + ":" + registerServerPort);
        ManagedChannel registerChannel = createChannel(registerServerIp, registerServerPort);
        registerServerCaller = new RegisterServerCaller(registerChannel);
    }

    static void initImageServerConnection(){
        // First get a free server that create a connection with that port and ip
        CompletableFuture<ServerEndpoint> future = registerServerCaller.getServerEndpoint();
        future.thenCompose(serverEndpoint -> {
            imageServerIp = serverEndpoint.getServerIp();
            imageServerPort = serverEndpoint.getServerPort();
            System.out.println("connect to image server in: " + imageServerIp + ":" + imageServerPort);

            ManagedChannel imageChannel = createChannel(imageServerIp, imageServerPort);
            System.out.println(imageChannel);
            imageServerCaller = new ImageServerCaller(imageChannel);

            return CompletableFuture.completedFuture(null);
        });
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
                    System.out.println("\nInsert the image path: ");
                    String imagePath = sc.nextLine();
                    System.out.print("Enter keywords (space-separated): ");
                    String keywordsInput = sc.nextLine();
                    String[] keywords = keywordsInput.split(" ");
                    imageServerCaller.processImage(imagePath, keywords);
                    break;

                case 2:
                    System.out.println("\nInsert the image identifier: ");
                    String imageId = sc.nextLine();
                    imageServerCaller.checkImageStatus(imageId);
                    break;

                case 3:
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
        System.out.println("║ 1 ║ Process Image          ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 2 ║ Check Image Status     ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 3 ║ Download Marked Image  ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 4 ║ Exit                   ║");
        System.out.println("╚═══╩════════════════════════╝");
    }

    private static boolean isValidOption(int option) {
        return (option >= 1 && option <= 4) || option == MENU_EXIT_OPTION;
    }

}
