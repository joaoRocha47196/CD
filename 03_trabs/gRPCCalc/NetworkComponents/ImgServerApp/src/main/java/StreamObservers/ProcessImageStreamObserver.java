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

            String volumePath = "/usr/datafiles/";
            String inputFileName = this.filename + "." + this.filetype;
            String outputFileName = UUID.randomUUID() + "-annotated-" + inputFileName;

            String vmRootDir = "./usr/images/" + this.filename + "." + this.filetype;

            String containerInputPath = volumePath + inputFileName;
            String containerOutPath = volumePath + outputFileName;

            // Write file in VM FileSystem
            FileOutputStream fileOutputStream = new FileOutputStream(vmRootDir);
            writer.writeTo(fileOutputStream);
            fileOutputStream.close();

            // Run Container do process file
            List<String> dockerArgs = new ArrayList<>();
            dockerArgs.add("unix:///var/run/docker.sock");  // Docker host URI
            dockerArgs.add(outputFileName);                 // Container name
            dockerArgs.add("/usr/images");                  // Volume or directory path
            dockerArgs.add("markimage");                    // Docker image name
            dockerArgs.add(containerInputPath);
            dockerArgs.add(containerOutPath);
            dockerArgs.addAll(this.keywords);

            DockerAPI.main(dockerArgs.toArray(new String[0]));

            ImageIdentifier response = ImageIdentifier.newBuilder()
                    .setIdentifier(outputFileName)
                    .build();

            replies.onNext(response);
            replies.onCompleted();
        } catch (Exception e) {
            onError(e);
        }
    }
}