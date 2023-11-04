package kvserverservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import redis.clients.jedis.Jedis;
import ringtoserverservice.Location;
import ringtoserverservice.RingToServerServiceGrpc;

public class KvServer {
    static int REDIS_PORT=6002; // redis default port
    private static final ServerLogic serverLogic = ServerLogic.getInstance();

    public static void main(String[] args) {
        try {
            if (args.length < 4) throw new RuntimeException("Not enough arguments (ringManagerIP, ringManagerPort, serverIP, serverPort)");
            String ringManagerIP = args[0];
            String ringManagerPort = args[1];
            String svcIP = args[2];
            String svcPort = args[3];
            serverLogic.setIPPort(svcIP+"/"+svcPort);

            String jedisIP="localhost";
            Jedis jedis = new Jedis(jedisIP, REDIS_PORT);
            System.out.println("Redis is running on "+jedisIP+" port="+REDIS_PORT+jedis.ping());
            serverLogic.setJedis(jedis);

            ManagedChannel channelRingManger = ManagedChannelBuilder.forAddress(ringManagerIP, Integer.parseInt(ringManagerPort))
                    .usePlaintext()
                    .build();
            RingToServerServiceGrpc.RingToServerServiceBlockingStub blockingStub = RingToServerServiceGrpc.newBlockingStub(channelRingManger);

            Location locationNextServer = blockingStub.registerServer(Location.newBuilder().setIP(svcIP).setPort(svcPort).build());
            serverLogic.setLocationNextServer(locationNextServer);

            channelRingManger.shutdown();

            io.grpc.Server svc = ServerBuilder
                    .forPort(Integer.parseInt(svcPort))
                    .addService(new ServerToServerService())
                    .addService(new ServerToClientService())
                    .build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);

            svc.awaitTermination();
            svc.shutdown();



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
