package userapp.servercallers;

import io.grpc.ManagedChannel;
import umstubs.FileIdentifier;
import umstubs.ResumeInfo;
import umstubs.UMServiceGrpc;
import userapp.StreamObservers.DownloadFileStreamObserver;
import userapp.StreamObservers.ResumeSalesStreamObserver;

import java.io.FileNotFoundException;


public class ManagerServerCaller {
    private final UMServiceGrpc.UMServiceStub managerServiceStub;

    public ManagerServerCaller(ManagedChannel channel) {
        this.managerServiceStub = UMServiceGrpc.newStub(channel);
    }

    public void resumeSales(String exchangeName, String filename, String productType) {
        ResumeInfo request = ResumeInfo.newBuilder()
            .setExchangeName(exchangeName)
            .setFileName(filename)
            .setProductType(productType)
            .build();

        ResumeSalesStreamObserver response = new ResumeSalesStreamObserver();
        managerServiceStub.resumeSales(request, response);
    }

    public void downloadFile(String fileId, String destinationPath) throws FileNotFoundException {
        FileIdentifier request = FileIdentifier.newBuilder()
                .setFileId(fileId)
                .build();

        DownloadFileStreamObserver response = new DownloadFileStreamObserver(destinationPath);
        managerServiceStub.downloadFile(request, response);
    }
}