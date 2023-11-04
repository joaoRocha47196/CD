package ringmanager;

import client.ClientGrpc;
import client.clientID;
import client.msg;

import java.util.*;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import ring.serverID;


public class RingManagerClient extends ClientGrpc.ClientImplBase{

    public static final Logger logger = Logger.getLogger(RingManagerServer.class.getName());

    private static String serverIP = "localhost";//"35.242.129.166";
    //private static int serverPort = 9000;
    private int inst = 0;
    private final ArrayList<serverID> servers;
    public HashMap<String , ArrayList<clientID>> ServersClients;




    public RingManagerClient() {
        servers = new ArrayList<serverID>();
    }

    @Override
    public void getServer (clientID clt , StreamObserver<msg> response){

        ArrayList<clientID> ar = null;
        String pp = null;

        for (String ip : ServersClients.keySet()){

            if(ar == null) {
                pp = ip;
                ar = ServersClients.get(ip);
            }

            if (ar.size() > ServersClients.get(ip).size()){
                pp = ip;
                ar = ServersClients.get(ip);
            }

        }

        ServersClients.get(pp).add(clt);

        response.onNext(msg.newBuilder().setIp(pp).build());
        response.onCompleted();


    }

}