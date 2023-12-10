package userapp.servercallers;

import csstubs.Category;
import csstubs.ResumeSales;
import csstubs.UserAppServiceGrpc;
import io.grpc.ManagedChannel;
import userapp.StreamObservers.ResumeSalesStreamObserver;


public class ManagerServerCaller {
    private final UserAppServiceGrpc.UserAppServiceStub userAppServiceStub;

    public ManagerServerCaller(ManagedChannel channel) {
        this.userAppServiceStub = UserAppServiceGrpc.newStub(channel);
    }

    public void resumeSales(String category, String exchangeName, String filename) {
        ResumeSales request = ResumeSales.newBuilder()
            .setCategory(Category.valueOf(category))
            .setExchangeName(exchangeName)
            .setResumeFilename(filename)
            .build();

        ResumeSalesStreamObserver response = new ResumeSalesStreamObserver();
        userAppServiceStub.resumeSales(request, response);
    }
}