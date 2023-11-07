package clientapp;

import calcstubs.CalcServiceGrpc;
import clientapp.StreamObservers.GetServerEndpoint;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.*;


public class Client {

    private static final int MENU_EXIT_OPTION = 9;
    private static String svcIP = "localhost";
    //private static String svcIP = "35.246.73.129";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static CalcServiceGrpc.CalcServiceBlockingStub blockingStub;
    private static CalcServiceGrpc.CalcServiceStub noBlockStub;


    public static void main(String[] args) {
        try {
            if (args.length == 2){
                svcIP = args[0];
                svcPort = Integer.parseInt(args[1]);
            }

            System.out.println("connect to register server in:"+svcIP+":"+svcPort);
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();
            blockingStub = CalcServiceGrpc.newBlockingStub(channel);
            noBlockStub = CalcServiceGrpc.newStub(channel);

            ServerCaller serverCaller = new ServerCaller(blockingStub, noBlockStub);

            Scanner sc = new Scanner(System.in);

            while (true) {
                int menuOption = getMenuOption();
                switch (menuOption) {
                    case 1:
                        getServerEndpoint();
                        break;

                    case 2:

                        break;

                    case 3:

                        break;

                    case 4:
                        break;

                    case MENU_EXIT_OPTION:
                        System.exit(0);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private static void getServerEndpoint(){
        StreamObserver<GetServerRequest> streamObserver = blockingStub.GetServerEndpoint(new GetServerEndpoint());
    }

    private static void registerServer(){
        StreamObserver<ServerRegistration> streamObserver = blockingStub.RegisterServer(new GetServerEndpoint());
    }
}
