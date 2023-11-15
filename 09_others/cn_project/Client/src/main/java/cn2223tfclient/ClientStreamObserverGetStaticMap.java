package cn2223tfclient;

import cn2223tf.Metadata;
import cn2223tf.StaticMapResponse;
import io.grpc.stub.StreamObserver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientStreamObserverGetStaticMap implements StreamObserver<StaticMapResponse> {

    ByteArrayOutputStream writer;
    String filename;
    String fileType;
    String destinationPath;

    public ClientStreamObserverGetStaticMap(String destinationPath) {
        this.filename = "";
        this.fileType = "jpg";
        this.destinationPath = destinationPath;
        this.writer = new ByteArrayOutputStream();
    }


    @Override
    public void onNext(StaticMapResponse staticMapResponse) {
        try {
            if(staticMapResponse.getImage().hasMetadata()) {
                setMetadata(staticMapResponse.getImage().getMetadata());
            }
            writer.write(staticMapResponse.getImage().getContent().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {System.err.println("\nError on call: " + throwable.getMessage());}

    @Override
    public void onCompleted() {
        try {
            saveProcessedImage();
            writer.close();
            System.out.println();
        } catch ( Exception  e) {
            e.printStackTrace();
        }
    }

    private void setMetadata(Metadata metadata) {
        this.filename = metadata.getName();
        this.fileType = metadata.getType();
    }

    private void saveProcessedImage() throws IOException {
        Path path = Paths.get(destinationPath + "\\" + filename + "." + fileType);
        File imageFile = path.toFile();

        InputStream is = new ByteArrayInputStream(writer.toByteArray());
        BufferedImage image = ImageIO.read(is);

        boolean created = ImageIO.write(image, fileType, imageFile);
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                  Save Processed Image                                      ║");
        System.out.println("║--------------------------------------------------------------------------------------------║");
        if (created) {
            System.out.println("║ File created successfully: " + imageFile.getName());
        } else {
            System.out.println("║ Failed to create file: " + imageFile.getName());
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
