package serverapp;

import calcstubs.*;
import srstubs.*;
import calcstubs.Number;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;



public class Server extends CalcServiceGrpc.CalcServiceImplBase {

    private static int svcPort = 8500;

    private SRServiceGrpc.SRServiceImplBase srService = new SRServiceGrpc.SRServiceImplBase() {

    };
        public static void main(String[] args) {
        try {
            if (args.length > 0) svcPort = Integer.parseInt(args[0]);
            io.grpc.Server svc = ServerBuilder
                .forPort(svcPort)
                .addService(new Server())
                .build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);
            //Scanner scan = new Scanner(System.in);
            //scan.nextLine();
            svc.awaitTermination();
            svc.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void logger(String className, String methodName, String info){
        System.out.println("[INFO]" + " ["
                + className + " - "
                + methodName + " - "
                + info + "]"
        );
    }
    @Override
    public void add(AddOperands request, StreamObserver<Result> responseObserver) {
        logger("Server", "add", "Start Exec");

        String id = request.getId();
        int op1 = request.getOp1();
        int op2 = request.getOp2();

        // Realize a operação de adição
        int result = op1 + op2;

        Result resultMessage = Result.newBuilder()
                .setId(id)
                .setRes(result)
                .build();

        logger("Server", "add", "Data Send Sucefully: " + resultMessage.getRes());
        responseObserver.onNext(resultMessage);

        logger("Server", "add", "End Exec");
        responseObserver.onCompleted();
    }

    @Override
    public void generatePowers(NumberAndMaxExponent request, StreamObserver<Result> responseObserver) {
        logger("Server", "generatePowers", "Start Exec");

        String id = request.getId();
        int base = request.getBaseNumber();
        int maxExponent = request.getMaxExponent();

        for (int exponent = 0; exponent <= maxExponent; exponent++) {
            // Realize o cálculo da potência (base elevada ao expoente)
            int result = (int) Math.pow(base, exponent);

            // Crie e envie o resultado usando o StreamObserver
            Result resultMessage = Result.newBuilder()
                    .setId(id)
                    .setRes(result)
                    .build();

            logger("Server", "generatePowers", "SendMsg: " + resultMessage.getRes());
            responseObserver.onNext(resultMessage);
        }

        logger("Server", "generatePowers", "Finished Exec");
        responseObserver.onCompleted();
    }


    @Override
    public StreamObserver<Number> addSeqOfNumbers(StreamObserver<Result> responseObserver) {
       // not implemented
        return null;

    }

    @Override
    public StreamObserver<AddOperands> multipleAdd(StreamObserver<Result> responseObserver) {
       // not implemented
        return null;

    }
}
