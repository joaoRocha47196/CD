package clientapp.StreamObservers;

import csstubs.ImageResponse;
import io.grpc.stub.StreamObserver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadProcessedImageStreamObserver implements StreamObserver<ImageResponse> {

    ByteArrayOutputStream writer;
    private final String destinationPath;
    private String filename;
    private String filetype;

    public DownloadProcessedImageStreamObserver(String destinationPath){
        this.writer = new ByteArrayOutputStream();
        this.destinationPath = destinationPath;
        this.filename = "";
        this.filetype = "jpg";
    }

    @Override
    public void onNext(ImageResponse imageResponse) {
        try {
            this.filename = imageResponse.getMetadata().getName();
            this.filetype = imageResponse.getMetadata().getType();

            writer.write(imageResponse.getProcessedImage().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error during image download: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        try {
            writer.close();
            Path path = Paths.get(destinationPath + "\\" + this.filename + "." + this.filetype);
            File imageFile = path.toFile();

            InputStream is = new ByteArrayInputStream(writer.toByteArray());
            BufferedImage image = ImageIO.read(is);

            boolean created = ImageIO.write(image, this.filetype, imageFile);
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                  Download processed image                                  ║");
            System.out.println("║--------------------------------------------------------------------------------------------║");
            if (created) {
                System.out.println("║ File created successfully: " + imageFile.getName());
            } else {
                System.out.println("║ Failed to create file: " + imageFile.getName());
            }
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
        } catch (Exception e) {
            onError(e);
        }
    }
}
