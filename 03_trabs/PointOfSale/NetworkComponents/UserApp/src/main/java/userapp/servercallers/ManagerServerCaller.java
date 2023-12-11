package userapp.servercallers;

import csstubs.Category;
import csstubs.ResumeSales;
import csstubs.UserAppServiceGrpc;
import io.grpc.ManagedChannel;
import userapp.StreamObservers.ResumeSalesStreamObserver;


public class ManagerServerCaller {
    private final UMServiceGrpc.UMServiceStub managerServiceStub;

    public ManagerServerCaller(ManagedChannel channel) {
        this.managerServiceStub = ManagerServiceGrpc.newStub(channel);
    }

    public void resumeSales(String exchangeName, String filename) {
        ResumeInfo request = ResumeInfo.newBuilder()
            .setExchangeName(exchangeName)
            .setFilename(filename)
            .build();

        ResumeSalesStreamObserver response = new ResumeSalesStreamObserver();
        managerServiceStub.resumeSales(request, response);
    }

    public void downloadFile(String fileId, String destinationPath) {
        FileIdentifier request = FileIdentifier.newBuilder()
                .setFileId(fileId)
                .build();

        DownloadProcessedImageStreamObserver response = new DownloadFileStreamObserver(destinationPath);
        managerServiceStub.downloadFile(request, response);
    }
}