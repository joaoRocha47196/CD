package workerapp.gluster;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GlusterFileManager implements Serializable {
    private static final String GLUSTER_DIRECTORY_PATH ="/var/sharedfiles/";

    private static String generateFileName(String routingKey, String workerName){
        return routingKey.toLowerCase() + "/file_" + workerName + ".txt";
    }

    public static void mergeFilesByRoutingKey(String routingKey, String mergedFileName) {
        String directoryPath = GLUSTER_DIRECTORY_PATH + routingKey.toLowerCase();
        //String mergedFileName = BASE_LCOATION + routingKey.toLowerCase() + "_merged.txt";

        try {
            List<Path> filesInDirectory = Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());

            try (BufferedWriter mergedFileWriter = Files.newBufferedWriter(Paths.get(mergedFileName))) {
                for (Path filePath : filesInDirectory) {
                    List<String> lines = Files.readAllLines(filePath);
                    for (String line : lines) {
                        mergedFileWriter.write(line);
                        mergedFileWriter.newLine();
                    }
                }
                System.out.println("All files in directory for routing key " + routingKey +
                        " have been merged into " + mergedFileName);
            } catch (IOException e) {
                System.out.println("Error writing to merged file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading files in directory: " + e.getMessage());
        }
    }

    public static void writeToFile(String content, String routingKey, String workerName) {
        String fileName = GLUSTER_DIRECTORY_PATH + generateFileName(routingKey, workerName);

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(content + "\n");
            System.out.println(" [x] Sale information written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


}
