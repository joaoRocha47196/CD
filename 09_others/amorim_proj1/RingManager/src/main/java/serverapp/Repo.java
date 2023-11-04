package serverapp;

import java.util.LinkedList;

public class Repo {
    public static class ServerInfo {
        String ip;
        int port;

         public ServerInfo(String ip, int port) {
             this.ip = ip;
             this.port = port;
         }
    }
    private final LinkedList<ServerInfo> Servers = new LinkedList<>();

    public synchronized int addServer(ServerInfo server) {
        Servers.add(server);
        return Servers.size()-1;
    }

    public ServerInfo getServer(int idx) {
        return Servers.get(idx);
    }

    public synchronized int totalServers() {
        return Servers.size();
    }
}
