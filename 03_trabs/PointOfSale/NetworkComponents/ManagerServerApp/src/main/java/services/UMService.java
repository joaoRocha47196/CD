package services;
import io.grpc.stub.StreamObserver;
import servercallers.SpreadGroupCaller;
import spread.SpreadException;
import spread.SpreadMessage;
import umstubs.*;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.io.IOException;

public class UMService extends UMServiceGrpc.UMServiceImplBase {
    private static final int CHUNK_SIZE = 32 * 1024;
    private SpreadGroupCaller spreadGroupCaller;

    // Constructor to accept SpreadGroupCaller parameter
    public UMService(SpreadGroupCaller spreadGroupCaller) {
        this.spreadGroupCaller = spreadGroupCaller;
    }

    @Override
    public void resumeSales(ResumeInfo request, StreamObserver<EmptyResponse> responseObserver) {
        System.out.println(":: Asking Spread Group for Resume Sales File ::");
        String exchangeName = request.getExchangeName();
        String productType = request.getProductType();
        String filename = request.getFileName();

        spreadGroupCaller.sendResumeRequest(exchangeName, productType, filename);

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
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
            responseObserver.onError(new Exception("Error downloading processed image"));
        }
    }
}
