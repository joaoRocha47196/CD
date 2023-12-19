package services;
import io.grpc.stub.StreamObserver;
import rabbit.RabbitConsumerNotification;
import server.ManagerServer;
import servercallers.SpreadGroupCaller;
import spread.SpreadException;
import spread.SpreadMessage;
import umstubs.*;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static server.ManagerServer.consumeNotifications;

public class UMService extends UMServiceGrpc.UMServiceImplBase {
    private static final int CHUNK_SIZE = 32 * 1024;
    private SpreadGroupCaller spreadGroupCaller;
    private static final String RABBITMQ_DEFAULT_HOST = "34.28.226.254";
    private static final int RABBITMQ_DEFAULT_PORT = 5672;

    // Constructor to accept SpreadGroupCaller parameter
    public UMService(SpreadGroupCaller spreadGroupCaller) {
        this.spreadGroupCaller = spreadGroupCaller;
    }

    @Override
    public void resumeSales(ResumeInfo request, StreamObserver<NotificationResponse> responseObserver) {
        System.out.println(":: Asking Spread Group for Resume Sales File ::");
        String exchangeName = request.getExchangeName();
        String productType = request.getProductType();
        String filename = request.getFileName();

        spreadGroupCaller.sendResumeRequest(exchangeName, productType, filename);

        // The future notification message
        CompletableFuture<String> futureNotification = new CompletableFuture<>();

        // Create Rabbit consumer
        consumeNotifications(exchangeName, futureNotification);

        // Wait for future to be complete (A new notification arrived)
        futureNotification.thenCompose(message -> {
            // Send the notification message to the client
            NotificationResponse notificationRsp = NotificationResponse.newBuilder()
                    .setMessage(message)
                    .build();
            responseObserver.onNext(notificationRsp);
            responseObserver.onCompleted();
            return CompletableFuture.completedFuture(null);
        });
    }

    @Override
    public void downloadFile(FileIdentifier request, StreamObserver<FileResponse> responseObserver) {
        System.out.println(":: Getting resume Of Sales File ::");

        String fileId = request.getFileId();
        String filePath = "/var/sharedfiles/" + fileId;

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[CHUNK_SIZE];

            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {

                FileResponse fileResponse = FileResponse.newBuilder()
                        .setFileId(fileId)
                        .setProcessedBytes(ByteString.copyFrom(buffer, 0, bytesRead))
                        .build();
                responseObserver.onNext(fileResponse);
            }

            responseObserver.onCompleted();
        } catch (IOException e) {
            responseObserver.onError(new Exception("Error downloading merged file: " + e.getMessage()));
        }
    }
}
