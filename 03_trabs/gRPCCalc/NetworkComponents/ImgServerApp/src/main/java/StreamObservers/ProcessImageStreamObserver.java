package StreamObservers;

import api.DockerAPI;
import csstubs.ImageIdentifier;
import csstubs.ImageRequest;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessImageStreamObserver implements StreamObserver<ImageRequest> {

    private ByteArrayOutputStream writer;
    private StreamObserver<ImageIdentifier> replies;

    private List<String> keywords;
    private String filename;
    private String filetype;

    public ProcessImageStreamObserver(StreamObserver<ImageIdentifier> responseObserver){
        this.writer = new ByteArrayOutputStream();
        this.replies = responseObserver;
        this.keywords = new ArrayList<>();
        this.filename = "";
        this.filetype = "";
    }

    @Override
    public void onNext(ImageRequest imageRequest) {
        try {
            this.filename = imageRequest.getMetadata().getName();
            this.filetype = imageRequest.getMetadata().getType();
            this.keywords = imageRequest.getKeywordsList();

            writer.write(imageRequest.getImageData().toByteArray());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        try {
            writer.close();

            String imageOutputName = UUID.randomUUID() + "_annotated_" + this.filename + "." + this.filetype;

            String inputPath = "./usr/images/" + this.filename + "." + this.filetype;

            String outputPath = "./usr/datafiles/" + imageOutputName;

            String inputPathDocker = this.filename + "." + this.filetype;


            FileOutputStream fileOutputStream = new FileOutputStream(inputPath);
            writer.writeTo(fileOutputStream);
            fileOutputStream.close();

            // Define the arguments for DockerAPI
            List<String> dockerArgs = new ArrayList<>();
            dockerArgs.add("unix:///var/run/docker.sock"); // Docker host URI
            dockerArgs.add(imageOutputName);        // Container name
            dockerArgs.add("/usr/images");          // Volume or directory path
            dockerArgs.add("markimage");            // Docker image name
            dockerArgs.add("java");
            dockerArgs.add("-jar");
            dockerArgs.add("/usr/datafiles/MarkImageApp-1.0-jar-with-dependencies.jar");
            dockerArgs.add(inputPath);
            dockerArgs.add(outputPath);
            dockerArgs.addAll(this.keywords);

            DockerAPI.main(dockerArgs.toArray(new String[0]));

            ImageIdentifier response = ImageIdentifier.newBuilder()
                    .setIdentifier(imageOutputName)
                    .build();

            replies.onNext(response);
            replies.onCompleted();
        } catch (Exception e) {
            onError(e);
        }
    }
}