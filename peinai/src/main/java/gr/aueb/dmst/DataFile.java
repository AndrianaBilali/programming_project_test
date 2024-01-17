package gr.aueb.dmst;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class DataFile {

    // Saves AI responses after post-processing the data
    private static final String FILE_PATH = System.getProperty("user.home") +
            File.separator + "data-browsing.txt";
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

    // Creates file το FILE_PATH based on some customised parameters
    public static void createFile() {

        // Ensures that the file does not exists before creating it
        outputFile = new File(FILE_PATH);
        if (isPathValid(FILE_PATH)) {
            try {
                if (outputFile.createNewFile()) {
                    System.out.println("File created: " + FILE_PATH);
                } else {
                    System.out.println("File already exists: " + FILE_PATH);
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to create file " + FILE_PATH + ". Check path and/or directory.");
        }

    }

    // File creation's parameter
    /*
     * The method isPathValid are private
     * but for testing purposes they have been modified to be public
     */

    // Checks if the path in null or empty in case of a change from the project's
    // team
    public static boolean isPathValid(String filepath) {
        return filepath != null && !filepath.trim().isEmpty();
    }

    // Counts the byte size of the specific file
    /*
     * try (var buff = new BufferedInputStream(new
     * FileInputStream(outputFile.getName()))) {
     * return Files.size(Paths.get(outputFile.getPath()));
     */

    /*
     * } catch (FileNotFoundException e) {
     * System.err.println("Unable to open file " + outputFile.getName() + ": " +
     * e.getMessage());
     * return -1L;
     * } catch (IOException e) {
     * System.err.println("Error reading byte: " + e.getMessage());
     * return -1L;
     */
    public long byteCount() {

        try (var buff = new BufferedInputStream(new FileInputStream(outputFile.getPath()))) {
            // return Files.size(Paths.get(outputFile.getPath()));
            System.out.println("File exists: " + outputFile.exists());
            System.out.println("File length: " + outputFile.length());
            return outputFile.length();
        } catch (InvalidPathException e) {
            System.err.println("Unable to convert path string into sa path");
            return -1L;

        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " + outputFile.getName() + ": " + e.getMessage());
            return -1L;
        } catch (IOException e) {
            System.err.println("Error reading byte: " + e.getMessage());
            return -1L;
        }
    }

    // Writes post-processed data into the file
    // FileWriter writes the data as characters not bytes
    public void dataWriter() {

        try (var buff = new BufferedWriter(new FileWriter(outputFile, true))) {
            buff.write("Q: " + question + "\nA: " + response + "\n\n");
            buff.close();
            System.out.println("Data writing was successful");
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