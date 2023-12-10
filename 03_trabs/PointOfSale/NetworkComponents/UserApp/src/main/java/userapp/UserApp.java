package userapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import userapp.servercallers.ManagerServerCaller;

import java.util.Scanner;


public class UserApp {
    private static final int MENU_EXIT_OPTION = 2;
    private static final int MANAGER_DEFAULT_PORT = 7008;
    private static final String MANAGER_DEFAULT_IP = "34.175.229.136";


    private static String managerServerIp;
    private static int managerServerPort;


    private static ManagerServerCaller manageServerCaller;

    public static void main(String[] args) {
        initConnections(args);
        initManagerServerConnection();
        processClientOperations();
    }

    public static void initConnections(String[] args){
        if (args.length == 2) {
            managerServerIp = args[0];
            managerServerPort = Integer.parseInt(args[1]);
        }
        else {
            managerServerIp = MANAGER_DEFAULT_IP;
            managerServerPort = MANAGER_DEFAULT_PORT;
        }
    }

    static void initManagerServerConnection(){
        System.out.println("connect to manager server in:" + managerServerIp + ":" + managerServerPort);
        ManagedChannel managerChannel = createChannel(managerServerIp, managerServerPort);
        manageServerCaller = new ManagerServerCaller(managerChannel);
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
                    System.out.println("Enter the product category (ALIMENTAR or CASA): ");
                    String category = sc.nextLine();
                    System.out.println("Enter the exchange name: ");
                    String exchangeName = sc.nextLine();
                    System.out.println("Enter the output filename: ");
                    String filename = sc.nextLine();
                    manageServerCaller.resumeSales(category, exchangeName, filename);
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
        System.out.println("║ 1 ║ Get Sales Resume       ║");
        System.out.println("╠═══╬════════════════════════╣");
        System.out.println("║ 2 ║ Exit                   ║");
        System.out.println("╚═══╩════════════════════════╝");
    }

    private static boolean isValidOption(int option) {
        return (option >= 1 && option <= 2) || option == MENU_EXIT_OPTION;
    }

}
