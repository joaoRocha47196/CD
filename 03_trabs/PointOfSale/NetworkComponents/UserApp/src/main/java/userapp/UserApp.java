package clientapp;

import clientapp.servercallers.ImageServerCaller;
import clientapp.servercallers.RegisterServerCaller;
import crstubs.ServerEndpoint;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import userapp.servercallers.ManagerServerCaller;

import java.util.Scanner;


public class UserApp {
    private static final int MENU_EXIT_OPTION = 9;
    private static final int MANAGER_DEFAULT_PORT = 8009;
    private static final String MANAGER_DEFAULT_IP = "34.175.229.136";
    private static String manageServerIp;
    private static int managerServerPort;
  

    private static ManagerServerCaller managerServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initImageServerConnection();
        processClientOperations();
    }

    public static void initConnections(String[] args){
        if (args.length == 2) {
            manageServerIp = args[0];
            managerServerPort = Integer.parseInt(args[1]);
        }
        else {
            manageServerIp = MANAGER_DEFAULT_IP;
            managerServerPort = MANAGER_DEFAULT_PORT;
        }
    }
    

    static void initImageServerConnection(){
        ManagedChannel imageChannel = createChannel(manageServerIp, managerServerPort);
        managerServerCaller = new ManagerServerCaller(imageChannel);
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
                    System.out.println("\nInsert the fileName to write sales");
                    String fileName = sc.nextLine();
                    String exchangeName = "ExgSales";
                    managerServerCaller.resumeSales(exchangeName, fileName);
                    break;

                case 2:
                    System.out.println("\nInsert the file identifier: ");
                    String fileId = sc.nextLine();
                    System.out.println("\nInsert the file download path: ");
                    String destinationPath = sc.nextLine();
                    managerServerCaller.downloadFile(fileId, destinationPath);
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
        System.out.println("║ 1 ║ Resume Sales           ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 2 ║ Download Resume File   ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 9 ║ Exit                   ║");
        System.out.println("╚═══╩════════════════════════╝");
    }

    private static boolean isValidOption(int option) {
        return (option >= 1 && option <= 2) || option == MENU_EXIT_OPTION;
    }

}
