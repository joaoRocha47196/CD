package serverapp;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import redis.clients.jedis.Jedis;
import rpcKvServerStub.InterRingServiceGrpc;
import rpcRegisterStub.RegisterServiceGrpc;
import rpcRegisterStub.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class KVServer {

    private static final Repo repo = new Repo();
    private static int svcPort = 8000;

    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Need the ring manager ip and port and redis ip and port as arguments.");
                return;
            }
            String ringIp = args[0];
            int ringPort = Integer.parseInt(args[1]);
            String redisIp = args[2];
            int redisPort = Integer.parseInt(args[3]);
            repo.redis = new Repo.ServerInfo(redisIp, redisPort);
            if (args.length > 4)
                svcPort = Integer.parseInt(args[4]);

            Jedis redis = new Jedis(repo.redis.ip, repo.redis.port);

            io.grpc.Server svc = ServerBuilder
                    .forPort(svcPort)
                    .addService(new InterRingService(repo, redis))
                    .addService(new WriteReadService(repo,redis))
                    .build();
            svc.start();

            Server info = setRingManager(ringIp, ringPort);
            if (info.getId().equals("")) {
                System.out.println("Caller is full");
                return;
            }
            repo.serverId = info.getId();

            setNextServer(info.getIp(), info.getPort());
            System.out.println("Server started, listening on " + svcPort);
            svc.awaitTermination();
            svc.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Server setRingManager(String ringIp, int ringPort) throws IOException {
        ManagedChannel ringManagerChannel = ManagedChannelBuilder.forAddress(ringIp, ringPort)
                .usePlaintext()
                .build();

        String ip = getExternalIp();
        repo.server = new Repo.ServerInfo(ip, svcPort);

        Server server = Server.newBuilder()
                .setIp(repo.server.ip)
                .setPort(svcPort)
                .build();

        repo.ringManagerServiceBlockingStub = RegisterServiceGrpc.newBlockingStub(ringManagerChannel);
        return repo.ringManagerServiceBlockingStub.registServer(server);
    }

    private static String getExternalIp() throws IOException {
        URL myIp = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(myIp.openStream()));
        String ip = in.readLine();
        return ip;
    }

    private static void setNextServer(String ip, int port) {
        repo.nextServer = new Repo.ServerInfo(ip, port);
        ManagedChannel nextServerChannel = ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build();

        System.out.printf("setting next server with IP: %s and Port: %d\n", ip, port);
        InterRingServiceGrpc.InterRingServiceStub nextServerStub = InterRingServiceGrpc.newStub(nextServerChannel);
        repo.nextWrite = nextServerStub.interWrite(new VoidStreamObserver("ERROR: setting next write"));
        repo.nextRead = nextServerStub.interRead(new VoidStreamObserver("ERROR: setting next read"));
    }
}
