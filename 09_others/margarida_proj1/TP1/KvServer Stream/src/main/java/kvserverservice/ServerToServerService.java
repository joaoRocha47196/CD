package kvserverservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import servertoserverservice.Pair;
import servertoserverservice.ServerToServerServiceGrpc;
import servertoserverservice.Void;

public class ServerToServerService extends ServerToServerServiceGrpc.ServerToServerServiceImplBase {

    private static final Jedis jedis = ServerLogic.getInstance().getJedis();
    private static StreamObserver<Pair> streamObserverPair = null;

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
    public StreamObserver<Pair> writeRead(StreamObserver<Void> resOb){

        if (streamObserverPair == null) {
            ManagedChannel channelNextServer = ManagedChannelBuilder.forAddress(ServerLogic.getInstance().getLocationNextServer().getIP(), Integer.parseInt(ServerLogic.getInstance().getLocationNextServer().getPort()))
                    .usePlaintext()
                    .build();
            ServerToServerServiceGrpc.ServerToServerServiceStub noBlockStub = ServerToServerServiceGrpc.newStub(channelNextServer);
            streamObserverPair = noBlockStub.writeRead(streamObserverVoid);
            ServerLogic.getInstance().setStreamObserverPair(streamObserverPair);
        }
        streamObserverPair = ServerLogic.getInstance().getStreamObserverPair();

        return new ServerStreamObserverWriteRead();

    }

    public static class ServerStreamObserverWriteRead implements StreamObserver<Pair> {

        @Override
        public void onNext(Pair pair) {
            System.out.println("Test");
            if (pair.getFlagWriteRead()){ // If True = Write
                if (jedis.get(pair.getKey()) == null){
                    jedis.set(pair.getKey(), pair.getValue());
                    // write next
                    streamObserverPair.onNext(Pair.newBuilder().setKey(pair.getKey()).setValue(pair.getValue()).setIPPort(pair.getIPPort()).setFlagWriteRead(true).build());
                }else{
                    //if (own ip != pair.getIP()) write next
                    if (!ServerLogic.getInstance().getIPPort().equals(pair.getIPPort())){
                        streamObserverPair.onNext(Pair.newBuilder().setKey(pair.getKey()).setValue(pair.getValue()).setIPPort(pair.getIPPort()).setFlagWriteRead(true).build());
                    }else {
                        // else
                        if (ServerLogic.getInstance().getKeyExistsMap().containsKey(pair.getKey())){
                            ServerLogic.getInstance().replaceKeyExistsMap(pair.getKey(), pair.getValue());
                        }
                    }
                }
            }else {// If False = Read
                if (jedis.get(pair.getKey()) == null){
                    //if (own ip == pair.getIP()) client nao ha
                    if (ServerLogic.getInstance().getIPPort().equals(pair.getIPPort())){
                        ServerLogic.getInstance().replaceKeyExistsMap(pair.getKey(), "not found");
                    }else {
                        // read next
                        streamObserverPair.onNext(Pair.newBuilder().setKey(pair.getKey()).setIPPort(pair.getIPPort()).setFlagWriteRead(false).build());
                    }
                }else {
                    //if (own ip != pair.getIP()) write next
                    if (!ServerLogic.getInstance().getIPPort().equals(pair.getIPPort())){
                        streamObserverPair.onNext(Pair.newBuilder().setKey(pair.getKey()).setValue(jedis.get(pair.getKey())).setIPPort(pair.getIPPort()).setFlagWriteRead(true).build());
                    }
                    // else stops loop
                }
            }

        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("Error on call:" + throwable.getMessage());
        }

        @Override
        public void onCompleted() {


        }
    }
}
