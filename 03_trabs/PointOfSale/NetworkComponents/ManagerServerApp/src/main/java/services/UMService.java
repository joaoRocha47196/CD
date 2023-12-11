package services;


import java.io.*;

package services;

import com.google.protobuf.ByteString;
import csstubs.*;
import io.grpc.stub.StreamObserver;

public class UMService extends UMServiceGrpc.UMServiceImplBase {
    private SpreadGroupCaller spreadGroupCaller;
    public static final int CHUCK_SIZE = 32 * 1024;

    // Constructor to accept SpreadGroupCaller parameter
    public UMService(SpreadGroupCaller spreadGroupCaller) {
        this.spreadGroupCaller = spreadGroupCaller;
    }

    // Other methods remain unchanged...

    @Override
    public void resumeSales(ResumeInfo request, StreamObserver<EmptyResponse> responseObserver) {
        System.out.println(":: Asking Resume of Sales ::");
        String exchangeName = request.getExchangeName();
        String fileName = request.getFileName();

        sendResumeRequestToSG();

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }


    @Override
    public void downloadFile(FileIdentifier request, StreamObserver<FileResponse> responseObserver) {
        System.out.println(":: Getting resume Of Sales File ::");

        String fileId = request.getFileId();
        String filePath = "/usr/images/" + fileId;

        try (FileInputStream fileInputStream = new FileInputStream(fileId)) {
            byte[] buffer = new byte[CHUCK_SIZE];

            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // TODO
            }
            responseObserver.onCompleted();

        } catch (IOException e) {
            e.printStackTrace();
            responseObserver.onError(new Exception("Error downloading processed image"));
        }
    }

    private void sendResumeRequestToSG() {
        // Use the SpreadGroupCaller here...
        if (spreadGroupCaller != null) {
            spreadGroupCaller.callMethod(); // Replace callMethod with the method you want to call
        }

        // TODO
        /**
         Para tal o servidor
         Manager gRPC, envia uma mensagem resume (d) em multicast para o grupo de Workers pedindo a
         operação de resumo de vendas de produtos ALIMENTAR ou CASA, indicando o nome de um Exchange
         para onde deve ser enviada a notificação (f) que o resumo está realizado, incluindo o nome do ficheiro
         onde deve ser escrito o resumo;
         */
    }

}
