package userapp.StreamObservers;

import io.grpc.stub.StreamObserver;
import umstubs.FileResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


//TODO: Confirm destinationPath parameter

public class DownloadFileStreamObserver implements StreamObserver<FileResponse> {
    private String destinationPath;
    private OutputStream outputStream;

    public DownloadFileStreamObserver(String destinationPath) throws FileNotFoundException {
        this.destinationPath = destinationPath;
        this.outputStream = new FileOutputStream(destinationPath);
    }

    @Override
    public void onNext(FileResponse fileResponse) {
        try {
            byte[] chunk = fileResponse.getProcessedBytes().toByteArray();
            outputStream.write(chunk);
            outputStream.flush();
        } catch (IOException e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error during file download: " + throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        try {
            outputStream.close();
            System.out.println("File download completed");
        } catch (IOException e) {
            onError(e);
        }
    }
}
