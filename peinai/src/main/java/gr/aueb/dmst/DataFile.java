package gr.aueb.dmst;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.InvalidPathException;

public class DataFile {

    // Saves AI responses after post-processing the data

    private static File outputFile;
    private String question;
    private String response;

    // Initiallizes instance variables
    public DataFile(String question, String response) {
        this.question = question;
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }

    // Creates file based on a filepath based on some customised parameters
    public static void createFile(String filePath) {

        // Ensures that the file does not exists before creating it
        outputFile = new File(filePath);
        if (isPathValid(filePath) && createDirectory(filePath)) {
            try {
                if (outputFile.createNewFile()) {
                    System.out.println("File created: " + filePath);
                } else {
                    System.out.println("File already exists: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to create file " + filePath + ". Check path and/or directory.");
        }

    }

    // File creation's parameters

    // Checks if the path in null or empty
    private static boolean isPathValid(String filePath) {
        return filePath != null && !filePath.trim().isEmpty();
    }

    // Checks if the directory of the file exists and if it does not, it creates it
    private static boolean createDirectory(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            return parentDir.mkdirs();
        }

        return true;
    }

    // Counts the byte size of the specific file
    public long byteCount() {

        try (var buff = new BufferedInputStream(new FileInputStream(outputFile.getName()))) {
            return Files.size(Paths.get(outputFile.getPath()));
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " + outputFile.getName() + ": " + e.getMessage());
            return -1;
        } catch (IOException e) {
            System.err.println("Error reading byte: " + e.getMessage());
            return -1;
        } catch (InvalidPathException e) {
            System.err.println("Unable to convert path string into sa path");
            return -1;
        }

    }

    // Writes post-processed data into the file
    // FileWriter writes the data as characters not bytes
    public void dataWriter() {

        try (var buff = new BufferedWriter(new FileWriter(outputFile))) {
            buff.write("Q: " + this.question + "\nA:" + this.response + "\n\n");
            buff.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " + outputFile + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing byte: " + e.getMessage());
        }

    }

    // Reads data from the file
    // FileReader reads data as characters not bytes
    public void fileReader() {

        try (var buff = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = buff.readLine()) != null) {
                System.out.println(line);
            }
            buff.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " + outputFile + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing byte: " + e.getMessage());
        }

    }

}
