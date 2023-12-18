package app.StreamObservers;

import io.grpc.stub.StreamObserver;
import umstubs.FileResponse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadFileStreamObserver implements StreamObserver<FileResponse> {
    private OutputStream outputStream;

    public DownloadFileStreamObserver(String destinationPath) throws FileNotFoundException {
        this.outputStream = new FileOutputStream(destinationPath);
    }

    @Override
    public void onNext(FileResponse fileResponse) {
        try {
            byte[] chunk = fileResponse.getProcessedBytes().toByteArray();
            outputStream.write(chunk);
        } catch (IOException e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error during file download: " + t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing output stream: " + e.getMessage());
            onError(e);

        }
        System.out.println("File download completed");
    }
}
