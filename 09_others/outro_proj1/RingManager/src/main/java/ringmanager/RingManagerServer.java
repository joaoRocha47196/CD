package ringmanager;

import java.util.*;
import java.util.logging.Logger;

import io.grpc.Server;
import ring.serverID;
import ring.RingGrpc;
import client.clientID;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

public class RingManagerServer extends RingGrpc.RingImplBase{



    public static final Logger logger = Logger.getLogger(RingManagerServer.class.getName());

    private int Nservers = 3;
    private ArrayList<serverID> servers = new ArrayList<>();
    private ArrayList<StreamObserver<serverID>> neh = new ArrayList<>();
    private RingManagerClient client;
    private HashMap<String , ArrayList<clientID>> Serverscl = new HashMap<>();

public RingManagerServer(RingManagerClient rg){
    client = rg;
}

    @Override
    public synchronized void registerServer (serverID srv, StreamObserver<Empty> responseObserver){
        System.out.println("xd: "+srv.toString());

        servers.add(srv);
        ArrayList<clientID> arr = new ArrayList<>();
        Serverscl.put(srv.getIp(), arr);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void vizinhoIp(serverID ip, StreamObserver<serverID> responseObserver){
        neh.add(responseObserver);

        System.out.println(servers.size());

        if(servers.size() == Nservers){

            System.out.println("entrou");

            serverID ipV1 = servers.remove(0);

            int i =0;

            for (serverID sr : servers) {
                neh.get(i).onNext(sr);
                neh.get(i).onCompleted();
                i++;
            }

            client.ServersClients = Serverscl;

            neh.get(neh.size() - 1).onNext(ipV1);
            neh.get(neh.size() - 1).onCompleted();

        }



    }


    }







