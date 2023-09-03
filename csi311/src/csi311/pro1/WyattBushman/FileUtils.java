package csi311.pro1.WyattBushman;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFileContent(String directory, String filename) {
        try {
            Path filePath = Paths.get(directory, filename);
            return new String(Files.readAllBytes(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file: " + filename);
        }
    }
}
