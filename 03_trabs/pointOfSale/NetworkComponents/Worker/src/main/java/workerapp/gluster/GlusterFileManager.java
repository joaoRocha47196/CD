package workerapp.gluster;

import java.io.*;
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

    public static void mergeFilesByRoutingKey(String routingKey, String filename) {
        String directoryPath = GLUSTER_DIRECTORY_PATH + routingKey.toLowerCase();
        String fileDirectory = directoryPath + filename;

        try {
            List<Path> filesInDirectory = Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());

            try (BufferedWriter mergedFileWriter = Files.newBufferedWriter(Paths.get(filename))) {
                for (Path filePath : filesInDirectory) {
                    List<String> lines = Files.readAllLines(filePath);
                    for (String line : lines) {
                        mergedFileWriter.write(line);
                        mergedFileWriter.newLine();
                    }
                }
                System.out.println("All files in directory for routing key " + routingKey +
                        " have been merged into " + filename);
            } catch (IOException e) {
                System.out.println("Error writing to merged file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading files in directory: " + e.getMessage());
        }
    }



    public static void writeToFile(String content, String routingKey, String workerName) {
        String directoryPath = GLUSTER_DIRECTORY_PATH;
        String fileName = directoryPath + "file_" + workerName + ".txt";

        try {
            File file = new File(fileName);
            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Use FileWriter with append mode
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(content + "\n");
                System.out.println(" [x] Sale information written to file: " + fileName);
            }catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
