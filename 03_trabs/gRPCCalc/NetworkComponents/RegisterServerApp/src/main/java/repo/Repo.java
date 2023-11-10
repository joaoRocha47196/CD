package repo;

import srstubs.ServerRegistration;

import java.util.ArrayList;
import java.util.List;

public class Repo {

    /**
     * List of ServerRegistration
     * - serverId
     * - serverIp
     * - serverPort
     */
    private static final List<ServerRegistration> registeredServers = new ArrayList<>();
    private static final RoundRobin roundRobin = new RoundRobin();

    public static void addServer(ServerRegistration request){
        registeredServers.add(request);
    }

    public static boolean isEmpty(){
        return registeredServers.isEmpty();
    }

    public static ServerRegistration getServer(){
        int nextIndex = roundRobin.getNextIndex(registeredServers.size());
        roundRobin.updateState(nextIndex);
        return registeredServers.get(nextIndex);
    }
}

