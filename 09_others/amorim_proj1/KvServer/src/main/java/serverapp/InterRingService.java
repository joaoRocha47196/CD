package serverapp;

import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import rpcClientStub.Value;
import rpcKvServerStub.InterRingServiceGrpc;
import rpcKvServerStub.Pair;
import rpcKvServerStub.Void;

import java.util.LinkedList;
import java.util.Locale;

public class InterRingService extends InterRingServiceGrpc.InterRingServiceImplBase {
    private final Repo repo;
    private final Jedis redis;

    public InterRingService(Repo repo, Jedis redis) {
        this.repo = repo;
        this.redis = redis;
    }

    @Override
    public StreamObserver<Pair> interWrite(StreamObserver<Void> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(Pair pair) {
                if (!pair.getIdServer().equals(repo.serverId)) {
                    redis.set(pair.getKey(), pair.getValue());
                    repo.nextWrite.onNext(pair);
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Pair> interRead(StreamObserver<Void> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(Pair pair) {
                if (pair.getIdServer().equals(repo.serverId)) {
                    LinkedList<StreamObserver<Value>> waiters = repo.removeRequest(pair.getKey());
                    if (waiters.size() > 0) {
                        Value value = Value.newBuilder()
                                .setValue(pair.getValue())
                                .build();
                        for (StreamObserver<Value> waiter : waiters) {
                            waiter.onNext(value);
                            waiter.onCompleted();
                        }
                    }
                } else {
                    if (pair.getValue().equals("")) {
                        String redisValue = redis.get(pair.getKey());
                        if (redisValue != null) {
                            Pair readPair = Pair.newBuilder()
                                    .setKey(pair.getKey())
                                    .setValue(redisValue)
                                    .setIdServer(pair.getIdServer())
                                    .build();
                            repo.nextRead.onNext(readPair);
                            Pair writePair = Pair.newBuilder()
                                    .setKey(pair.getKey())
                                    .setValue(redisValue)
                                    .setIdServer(repo.serverId)
                                    .build();
                            repo.nextWrite.onNext(writePair);

                        } else
                            repo.nextRead.onNext(pair);
                    } else
                        repo.nextRead.onNext(pair);
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
