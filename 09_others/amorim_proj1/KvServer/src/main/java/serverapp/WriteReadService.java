package serverapp;

import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import rpcClientStub.Void;
import rpcClientStub.*;

public class WriteReadService extends WriteReadServiceGrpc.WriteReadServiceImplBase {
    private final Repo repo;
    private final Jedis redis;


    public WriteReadService(Repo repo, Jedis redis) {
        this.repo = repo;
        this.redis = redis;
    }

    @Override
    public void write(Pair pair, StreamObserver<Void> responseObserver) {
        rpcKvServerStub.Pair toWrite = rpcKvServerStub.Pair.newBuilder()
                .setKey(pair.getKey())
                .setValue(pair.getValue())
                .setIdServer(repo.serverId)
                .build();
        repo.nextWrite.onNext(toWrite);
        redis.set(pair.getKey(), pair.getValue());
        responseObserver.onNext(Void.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void read(Key key, StreamObserver<Value> responseObserver) {
        String redisValue = redis.get(key.getKey());
        if (redisValue == null) {
            rpcKvServerStub.Pair toRead = rpcKvServerStub.Pair.newBuilder()
                    .setKey(key.getKey())
                    .setIdServer(repo.serverId)
                    .build();
            repo.addRequest(key.getKey(), responseObserver);
            repo.nextRead.onNext(toRead);
        } else {
            Value value = Value.newBuilder()
                    .setValue(redisValue)
                    .build();
            responseObserver.onNext(value);
            responseObserver.onCompleted();
        }
    }
}
