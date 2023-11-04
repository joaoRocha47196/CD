package clientapp;

import calcstubs.*;
import calcstubs.Number;
import io.grpc.stub.StreamObserver;

public class ServerCaller {

    private CalcServiceGrpc.CalcServiceBlockingStub blockingStub;
    private CalcServiceGrpc.CalcServiceStub noBlockStub;

    public ServerCaller(CalcServiceGrpc.CalcServiceBlockingStub blockingStub, CalcServiceGrpc.CalcServiceStub noBlockStub) {
        this.blockingStub = blockingStub;
        this.noBlockStub = noBlockStub;
    }

    // Função para adicionar dois números

    /**
     * Envia um pedido unico ao Servidor
     * Recebe a resposta unica
     */
    void blockingAdd() {
        logger("ServerCaller", "blockingAdd", "Send Request");
        Result res = blockingStub.add(AddOperands.newBuilder()
                .setId("50+25")
                .setOp1(50)
                .setOp2(25)
                .build());
        logger("ServerCaller", "blockingAdd",
                "Received Data: Id: "+ res.getId() + " Value: " + res.getRes());
    }

    /**
     * Envia um pedido unico ao Servidor
     * Recebe em Stream as potencias á medida que são executadas pelo Servidor
     * Obtem em onComplete o Resultado
     */
    void sendCalculatePowersRequest() {
        logger("ServerCaller", "sendCalculatePowersRequest",
                "Send Request");

        String id = "2^3";
        int base = 2;
        int exponent = 3;

        noBlockStub.generatePowers(
                NumberAndMaxExponent.newBuilder()
                    .setId(id)
                    .setBaseNumber(base)
                    .setMaxExponent(exponent)
                    .build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(Result result) {
                        String id = result.getId();
                        int res = result.getRes();
                        logger("ServerCaller", "sendCalculatePowersRequest",
                            "Received Data: Id: "+ id + " Value: " + res);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        logger("ServerCaller", "sendCalculatePowersRequest",
                                "ERROR");
                    }

                    @Override
                    public void onCompleted() {
                        logger("ServerCaller", "sendCalculatePowersRequest",
                                "Finished");
                    }
                }
        );
    }

    /**
     * Envia em Stream para o servidor uma sequencia de numeros
     * Recebe a resposta unica em onComplete o Resultado final
     */
    private void sumNumberSequence() {
        int start = 1;
        int end = 10;
        StreamObserver<Number> numberStreamObserver = noBlockStub.addSeqOfNumbers(new StreamObserver<>() {
            @Override
            public void onNext(Result result) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }
        });

        for (int i = start; i <= end; i++) {
            numberStreamObserver.onNext(Number.newBuilder()
                    .setNum(i)
                    .build()
            );
        }
        numberStreamObserver.onCompleted();
    }

    // Função para operações de soma múltipla

    /**
     * Envia em Stream para o servidor um conjunto de valores
     * Recebe em Stream o resultado das sucessivas someas
     */
    private void multipleAddOperations() {
        StreamObserver<AddOperands> addOperandsStreamObserver = noBlockStub.multipleAdd(new StreamObserver<>() {
            @Override
            public void onNext(Result result) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }
        });

        addOperandsStreamObserver.onNext(AddOperands.newBuilder()
                .setOp1(10)
                .setOp2(5)
                .build()
        );
        addOperandsStreamObserver.onNext(AddOperands.newBuilder()
                .setOp1(20)
                .setOp2(15)
                .build()
        );

        addOperandsStreamObserver.onNext(AddOperands.newBuilder()
                .setOp1(30)
                .setOp2(5)
                .build()
        );
        addOperandsStreamObserver.onCompleted();
    }

    public void logger(String className, String methodName, String info){
        System.out.println("[INFO]" + " ["
                + className + " - "
                + methodName + " - "
                + info + "]"
        );
    }
}
