package clientapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rpcBrowserStub.Server;
import rpcBrowserStub.ServerBrowserServiceGrpc;
import rpcBrowserStub.Void;
import rpcClientStub.Key;
import rpcClientStub.Pair;
import rpcClientStub.WriteReadServiceGrpc;

import java.util.Scanner;


public class Client {

    private static String svcIP = "localhost";
    private static int svcPort = 8000;
    private static ManagedChannel ringManagerChannel;
    private static ManagedChannel serverChannel;
    private static ServerBrowserServiceGrpc.ServerBrowserServiceBlockingStub ringManagerBlockingStub;
    private static WriteReadServiceGrpc.WriteReadServiceStub serverStub;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length == 2) {
            svcIP = args[0];
            svcPort = Integer.parseInt(args[1]);
        }
        System.out.println("connect to master in: "+svcIP+":"+svcPort);
        ringManagerChannel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                .usePlaintext()
                .build();
        ringManagerBlockingStub = ServerBrowserServiceGrpc.newBlockingStub(ringManagerChannel);

        Server server = ringManagerBlockingStub.getKvServer(Void.newBuilder().build());
        System.out.printf("connect to server with IP: %s and Port: %d\n", server.getIp(), server.getPort());

        serverChannel = ManagedChannelBuilder.forAddress(server.getIp(), server.getPort())
                .usePlaintext()
                .build();
        serverStub = WriteReadServiceGrpc.newStub(serverChannel);
        boolean exit = false;
        while (!exit) {
            int op = Menu();
            switch (op) {
                case 1: write();
                        break;
                case 2: read();
                        break;
                case 9: exit = true;
            }
        }
    }

    private static void read() {
        String key = getInput("Key?");
        Key ret = Key.newBuilder()
                .setKey(key)
                .build();
        serverStub.read(ret, new ReadStreamObserver(key));
    }

    private static void write() {
        String key = getInput("Key?");
        String value = getInput("Value?");
        Pair ret = Pair.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
        serverStub.write(ret, new WriteStreamObserver(ret.getKey(), ret.getValue()));
    }

    private static String getInput(String print) {
        System.out.println(print);
        return in.next();
    }

    private static int Menu() {
        int op;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println();
            System.out.println("    MENU");
            System.out.println(" 1 - Insert Key-Value Pair");
            System.out.println(" 2 - Get Value for Key");
            System.out.println(" 9 - Exit");
            System.out.println();
            System.out.println("Choose an Option?");
            op = scan.nextInt();
        } while (!((op >= 1 && op <= 2) || op == 9));
        return op;
    }
}
