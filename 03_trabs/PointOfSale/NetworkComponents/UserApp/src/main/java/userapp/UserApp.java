package userapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import userapp.servercallers.ManagerServerCaller;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class UserApp {
    private static final int MENU_EXIT_OPTION = 9;
    private static final int MANAGER_DEFAULT_PORT = 8009;
    private static final String MANAGER_DEFAULT_IP = "34.175.229.136";
    private static String manageServerIp;
    private static int managerServerPort;
  

    private static ManagerServerCaller managerServerCaller;

    public static void main(String[] args) throws FileNotFoundException {
        initConnections(args);
        initManagerServerConnection();
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
    

    static void initManagerServerConnection(){
        ManagedChannel imageChannel = createChannel(manageServerIp, managerServerPort);
        managerServerCaller = new ManagerServerCaller(imageChannel);
    }

    static ManagedChannel createChannel(String ip, int port){
        return ManagedChannelBuilder.forAddress(ip, port)
            .usePlaintext()
            .build();
    }

    static void processClientOperations() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int menuOption = getMenuOption();
            switch (menuOption) {
                case 1:
                    System.out.println("\nInsert the fileName to write sales");
                    String fileName = sc.nextLine();
                    System.out.println("\nInsert the product type (ALIMENTAR or CASA)");
                    String productType = sc.nextLine();
                    String exchangeName = "ExgSales";
                    managerServerCaller.resumeSales(exchangeName, fileName, productType);
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
