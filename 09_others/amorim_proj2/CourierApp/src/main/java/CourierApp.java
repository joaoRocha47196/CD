import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rpcCourierStub.CommunicationServiceGrpc;
import rpcCourierStub.Connection;
import rpcCourierStub.Void;

import java.util.Scanner;

public class CourierApp {
    private static CommunicationServiceGrpc.CommunicationServiceStub serverStub;
    private static final Scanner in = new Scanner(System.in);
    protected static State state = State.DISCONNECTED;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Need more parameters: Server IP and Server Port.");
            return;
        }

        String svcIP = args[0];
        int svcPort = Integer.parseInt(args[1]);

        System.out.println("connect to server in: " + svcIP + ":" + svcPort);
        ManagedChannel serverChannel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();

        serverStub = CommunicationServiceGrpc.newStub(serverChannel);

        boolean exit = false;
        while (!exit) {
            switch (state) {
                case DISCONNECTED:
                    exit = disconnectedMenu();
                    break;

                case BUSY:
                    exit = busyMenu();
                    break;

                case WORKING:
                    exit = workingMenu();
                    break;

                case FREE:
                    exit = freeMenu();
                    break;

                default: break;
            }
        }
        if (state != State.DISCONNECTED) {
            serverStub.disconnect(Void.newBuilder().build(), new VoidObserver());
        }
    }

    private static boolean freeMenu() {
        String menu = "Select an action:\n\t" +
                "1 - Set busy\n\t" +
                "2 - Disconnect\n\t" +
                "99- Exit\n";
        while (true) {
            switch (getInput(menu)) {
                case "1":
                    if (state == State.FREE) {
                        serverStub.busy(Void.newBuilder().build(), new VoidObserver());
                        state = State.BUSY;
                    }
                    return false;

                case "2":
                    state = State.DISCONNECTED;
                    serverStub.disconnect(Void.newBuilder().build(), new VoidObserver());
                    return false;

                case "99":
                    return state == State.FREE;
                default:
                    if (state != State.FREE)
                        return false;
            }
        }
    }

    private static boolean disconnectedMenu() {
        String menu = "Select an action:\n\t" +
                "1 - Connect\n\t" +
                "99- Exit\n";

        while (true) {
            switch (getInput(menu)) {
                case "1":
                    String region = getInput("Region to connect?");
                    Connection connection = Connection.newBuilder()
                            .setRegion(region)
                            .build();
                    state = State.FREE;
                    WorkObserver workObserver = new WorkObserver();
                    serverStub.connect(connection, workObserver);
                    return false;

                case "99":
                    return true;
                default:
                    break;
            }
        }

    }

    private static boolean busyMenu() {
        String menu = "Select an action:\n\t" +
                "1 - Set free\n\t" +
                "2 - Disconnect\n\t" +
                "99- Exit\n";
        return busySwitch(menu);
    }

    private static boolean workingMenu() {
        String menu = "Select an action:\n\t" +
                "1 - Work Done!\n\t" +
                "2 - Disconnect\n\t" +
                "99- Exit\n";
        return busySwitch(menu);
    }

    private static boolean busySwitch(String menu) {
        while (true) {
            switch (getInput(menu)) {
                case "1":
                    state = State.FREE;
                    serverStub.free(Void.newBuilder().build(), new VoidObserver());
                    return false;

                case "2":
                    state = State.DISCONNECTED;
                    serverStub.disconnect(Void.newBuilder().build(), new VoidObserver());
                    return false;

                case "99":
                    return true;

                default: break;
            }
        }
    }

    private static String getInput(String print) {
        System.out.println(print);
        return in.nextLine();
    }
}