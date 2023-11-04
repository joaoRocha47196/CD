package kvserverservice;

import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import ringtoserverservice.Location;
import servertoserverservice.Pair;

import java.util.HashMap;
import java.util.Map;

public final class ServerLogic {

    private static ServerLogic INSTANCE;
    private static Jedis jedis;

    private static Location locationNextServer;
    private static String IPPort;
    static Map<String, String> keyExistsMap = new HashMap<>();
    private static StreamObserver<Pair> streamObserverPair;

    public static ServerLogic getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ServerLogic();
        }

        return INSTANCE;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public Location getLocationNextServer() {
        return locationNextServer;
    }

    public String getIPPort() {
        return IPPort;
    }
    public Map<String, String> getKeyExistsMap() {
        return keyExistsMap;
    }

    public StreamObserver<Pair> getStreamObserverPair() {
        return streamObserverPair;
    }

    public void setJedis(Jedis jedis) {
        ServerLogic.jedis = jedis;
    }

    public void setLocationNextServer(Location locationNextServer) {
        ServerLogic.locationNextServer = locationNextServer;
    }

    public void setIPPort(String IPPort) {
        ServerLogic.IPPort = IPPort;
    }

    public void setStreamObserverPair(StreamObserver<Pair> streamObserverPair) {
        ServerLogic.streamObserverPair = streamObserverPair;
    }

    public void replaceKeyExistsMap(String key, String value) {
        keyExistsMap.replace(key, value);
    }
}
