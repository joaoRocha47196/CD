package kvserverservice;

import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import servertoclientservice.Void;
import servertoclientservice.*;
import servertoserverservice.ServerToServerServiceGrpc;

public class ServerToClientService extends ServerToClientServiceGrpc.ServerToClientServiceImplBase{

    private static final Jedis jedis = ServerLogic.getInstance().getJedis();
    private static ServerToServerServiceGrpc.ServerToServerServiceStub noBlockStub = null;

    private static final StreamObserver<servertoserverservice.Void> streamObserverVoid = new StreamObserver<>() {
        @Override
        public void onNext(servertoserverservice.Void aVoid) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onCompleted() {

        }
    };

    @Override
    public void writeUpdate(Pair pair, StreamObserver<Void> responseObserver) {

        if (noBlockStub == null) {
            noBlockStub = ServerLogic.getInstance().createNoBLockStub();
            ServerLogic.getInstance().setNoBlockStub(noBlockStub);
        }
        noBlockStub = ServerLogic.getInstance().getNoBlockStub();

        // writes in jedis
        // sends write to next server
        jedis.set(pair.getKey(), pair.getValue());

        noBlockStub.writeRead(streamObserverVoid).onNext(servertoserverservice.Pair.newBuilder().setKey(pair.getKey()).setValue(pair.getValue()).setIPPort(ServerLogic.getInstance().getIPPort()).setFlagWriteRead(true).build());

        responseObserver.onNext(Void.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void read(Key key, StreamObserver<Value> responseObserver) {

        if (noBlockStub == null) {
            noBlockStub = ServerLogic.getInstance().createNoBLockStub();
            ServerLogic.getInstance().setNoBlockStub(noBlockStub);
        }
        noBlockStub = ServerLogic.getInstance().getNoBlockStub();

        // checks jedis
        // if not
        // sends read to next server
        // continues to check jedis until value is written or flag exists changes to "not found"
        if (jedis.get(key.getKey()) != null){
            responseObserver.onNext(Value.newBuilder().setValue(jedis.get(key.getKey())).build());
            responseObserver.onCompleted();
        }else {
            ServerLogic.getInstance().getKeyExistsMap().put(key.getKey(), "searching");
            noBlockStub.writeRead(streamObserverVoid).onNext(servertoserverservice.Pair.newBuilder().setKey(key.getKey()).setValue(" ").setIPPort(ServerLogic.getInstance().getIPPort()).setFlagWriteRead(false).build());
            while(true){
                if (!ServerLogic.getInstance().getKeyExistsMap().get(key.getKey()).equals("searching")){
                    responseObserver.onNext(Value.newBuilder().setValue(ServerLogic.getInstance().getKeyExistsMap().get(key.getKey())).build());
                    responseObserver.onCompleted();
                    break;
                }
            }
        }



    }
}
