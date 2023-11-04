package ringmanagerservice;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import ringtoclientservice.Location;
import ringtoclientservice.RingToClientServiceGrpc;
import ringtoclientservice.Void;

import java.util.Map;

public class RingToClientService extends RingToClientServiceGrpc.RingToClientServiceImplBase{

    private static final SingletonServerMap singletonServerMap = SingletonServerMap.getInstance();
    Map<String, SingletonServerMap.Server> serverMap = singletonServerMap.getServerMap();
    int lowestNumberClients = 0;
    String IP = "";
    String port = "";

    @Override
    public void getKvServer(Void request, StreamObserver<Location> responseObserver) {
        // Percorre o map de servidores guardado na class Singleton, verificando qual o servidor com menor número de clientes
        for (SingletonServerMap.Server server : serverMap.values()) {
            if (server.numberClients <= lowestNumberClients) {
                lowestNumberClients = server.numberClients;
                IP = server.ip;
                port = server.port;
            }
        }
        lowestNumberClients+=1;

        // Adiciona um cliente ao servidor com menos clientes, enviando o ip e o porto ao cliente que fez o pedido
        if (singletonServerMap.addNumberOfClients(IP+"/"+port)){
            responseObserver.onNext(Location.newBuilder().setIP(IP).setPort(port).build());
            responseObserver.onCompleted();
        }else {
            Status status = Status.FAILED_PRECONDITION.withDescription("No servers in Ring");
            responseObserver.onError(status.asRuntimeException());
        }

    }
}

