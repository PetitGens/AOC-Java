package adventOfCode2023.day8;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    private static FileLogger instance;

    private final String path = "log.txt";

    private FileLogger(){

    }

    public static FileLogger getInstance() {
        if(instance == null){
            instance = new FileLogger();
        }

        return instance;
    }

    public void log(String line){
        try {
            // Create a FileWriter in append mode
            FileWriter fileWriter = new FileWriter(path, true);

            // Create a BufferedWriter to efficiently write characters to the file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Append the line to the file
            bufferedWriter.write(line);
            bufferedWriter.newLine();  // Add a newline character

            // Close the resources
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
