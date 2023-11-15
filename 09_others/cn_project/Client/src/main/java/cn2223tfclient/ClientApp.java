package cn2223tfclient;

import cn2223tf.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class ClientApp {

    private static final int SUBMIT_IMAGE_OPTION = 1;
    private static final String INSTANCE_GROUP = "instance-group-grpc";
    private static final String ZONE = "europe-west8-a";
    private static final String SERVER_LOOKUP_URL = "https://europe-west1-cn2223-t1-g14.cloudfunctions.net/funcLookup?" +"instance-group=" +INSTANCE_GROUP+"&&"+ "zone=" +ZONE;
    private static final int GET_LANDMARKS_OPTION = 2;
    private static final int GET_STATIC_MAP_OPTION = 3;
    private static final int GET_ALL_IMAGES_OPTION = 4;
    private static final int MENU_EXIT_OPTION = 5;
    private static int svcPort = 8000;
    private static CN2223TFServiceGrpc.CN2223TFServiceStub stub;

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length > 0) {
            svcPort = Integer.parseInt(args[0]);
        }

        loadMenu();
        Scanner sc = new Scanner(System.in);
        sc.next();
    }

    static void setupServerConnection(String svcIP, Integer svcPort) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();
        stub = CN2223TFServiceGrpc.newStub(channel);
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
        System.out.println("╔═══════════════════════╗");
        System.out.println("║         MENU          ║");
        System.out.println("╠═══╦═══════════════════╣");
        System.out.println("║ 1 ║ Submit Image      ║");
        System.out.println("╠═══╬═══════════════════╣");
        System.out.println("║ 2 ║ Get Landmarks     ║");
        System.out.println("╠═══╬═══════════════════╣");
        System.out.println("║ 3 ║ Get Static Map    ║");
        System.out.println("╠═══╬═══════════════════╣");
        System.out.println("║ 4 ║ Get All Images by ║");
        System.out.println("║   ║ Confidence Level  ║");
        System.out.println("╠═══╬═══════════════════╣");
        System.out.println("║ 5 ║ Exit              ║");
        System.out.println("╚═══╩═══════════════════╝");
    }

    static boolean isValidOption(int option) {
        return (option >= SUBMIT_IMAGE_OPTION && option <= GET_ALL_IMAGES_OPTION) || option == MENU_EXIT_OPTION;
    }

    static void loadMenu() throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Searching for available servers...");
        String[] serversIP = getAvailableServers();
        String serverIP = (serversIP != null && serversIP.length > 0) ? selectAvailableServer(serversIP) : "localhost";
        System.out.println("Selected Server: " + serverIP);

        setupServerConnection(serverIP, svcPort);

        while (true) {
            int menuOption = getMenuOption();
            switch (menuOption) {
                case SUBMIT_IMAGE_OPTION:
                    System.out.println("\nInsert the image path: ");
                    String imagePath = sc.nextLine();
                    submitImage(imagePath);
                    break;

                case GET_LANDMARKS_OPTION:
                    System.out.println("\nInsert the image identifier: ");
                    String imageName = sc.nextLine();
                    getLandmarks(imageName);
                    break;

                case GET_STATIC_MAP_OPTION:
                    System.out.println("\nInsert the image identifier: ");
                    String nameImage = sc.nextLine();
                    System.out.println("\nInsert the image download path: ");
                    String destinationPath = sc.nextLine();
                    getStaticMap(nameImage, destinationPath);
                    break;

                case GET_ALL_IMAGES_OPTION:
                    double confidence = getValidConfidenceValue(sc);
                    getImagesByConfidence(confidence);
                    break;

                case MENU_EXIT_OPTION:
                    System.exit(0);
            }
        }
    }


    private static double getValidConfidenceValue(Scanner scanner) {
        double confidence = -1.0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print("Insert the minimum confidence of the landmark presence in the image [0-1]: ");
            String input = scanner.nextLine();

            try {
                confidence = Double.parseDouble(input);
                if (confidence >= 0.0 && confidence <= 1.0) {
                    isValidInput = true;
                } else {
                    System.out.println("Confidence value must be between 0 and 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return Math.abs(confidence);
    }


    private static String selectAvailableServer(String[] serversIP) {
        int random = new Random().nextInt(serversIP.length);
        return serversIP[random];
    }


    private static String[] getAvailableServers() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_LOOKUP_URL))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println(response.body());
            return response.body().split(",");
        } else {
            System.out.println("Failed to retrieve server list, with status code " + response.statusCode());
        }
        return null;
    }

    private static void submitImage(String imagePath) throws IOException, InterruptedException {
        StreamObserver<ImageUploadRequest> streamObserver = stub.imageSubmit(new ClientStreamObserverSubmitImage(imagePath));

        Path path = Paths.get(imagePath);
        String filename = String.valueOf(path.getFileName());
        String[] filenameParts = filename.split("\\.");
        String basename = filenameParts[0];
        String extension = filenameParts[1];

        InputStream inputStream = Files.newInputStream(path);
        byte[] bytes = new byte[4096];

        int size;
        while ((size = inputStream.read(bytes)) > 0){
            ImageUploadRequest uploadRequest = ImageUploadRequest.newBuilder()
                    .setImage(Image.newBuilder()
                            .setContent(ByteString.copyFrom(bytes, 0 , size))
                            .setMetadata(Metadata.newBuilder()
                                    .setName(basename)
                                    .setType(extension)
                                    .build())
                            .build())
                    .build();
            streamObserver.onNext(uploadRequest);
        }

        inputStream.close();
        streamObserver.onCompleted();
        Thread.sleep(2000);
    }

    private static void getLandmarks(String imageName) throws InterruptedException {
        IdentifierRequest request = IdentifierRequest.newBuilder()
                        .setName(imageName)
                .build();
        stub.getLandmarks(request, new ClientStreamObserverGetLandmarks(imageName));
        Thread.sleep(2000);
    }

    private static void getStaticMap(String imageName, String destinationPath) throws InterruptedException {
        IdentifierRequest request = IdentifierRequest.newBuilder()
                .setName(imageName)
                .build();
        stub.getStaticMap(request, new ClientStreamObserverGetStaticMap(destinationPath));
        Thread.sleep(2000);
    }

    private static void getImagesByConfidence(Double minimumConfidence) throws InterruptedException {
        ConfidenceRequest request = ConfidenceRequest.newBuilder()
                .setConfidence(minimumConfidence)
                .build();
        stub.getImagesByConfidence(request, new ClientStreamObserverGetByConfidenceLevel(minimumConfidence));
        Thread.sleep(2000);
    }
}
