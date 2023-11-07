package clientapp;

import calcstubs.CalcServiceGrpc;
import clientapp.StreamObservers.CheckImageStatusStreamObserver;
import clientapp.StreamObservers.DownloadProcessedImageStreamObserver;
import clientapp.StreamObservers.GetServerEndpointStreamObserver;
import clientapp.StreamObservers.ProcessImageStreamObserver;
import clientapp.servercallers.ImageServerCaller;
import clientapp.servercallers.RegisterServerCaller;
import com.google.protobuf.ByteString;
import crstubs.CRServiceGrpc;
import crstubs.GetServerRequest;
import csstubs.CSServiceGrpc;
import csstubs.ImageIdentifier;
import csstubs.ImageRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import srstubs.ServerRegistration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class Client {

    private static final int MENU_EXIT_OPTION = 9;
    private static final int CHUNCK_SIZE = 32 * 1024;
    private static String svcIP = "localhost";
    //private static String svcIP = "35.246.73.129";
    private static int svcPort = 8000;
    private static ManagedChannel channel;


    private static ImageServerCaller imageServerCaller;
    private static RegisterServerCaller registerServerCaller;
    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                svcIP = args[0];
                svcPort = Integer.parseInt(args[1]);
            }

            System.out.println("connect to register server in:" + svcIP + ":" + svcPort);
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    .usePlaintext()
                    .build();

            imageServerCaller = new ImageServerCaller(channel);
            registerServerCaller = new RegisterServerCaller(channel);

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

}
