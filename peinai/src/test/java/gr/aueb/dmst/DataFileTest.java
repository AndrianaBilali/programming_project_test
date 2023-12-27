package gr.aueb.dmst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataFileTest {

    private DataFile datafile;
    private static String testFilePath;

    @BeforeEach
    public void setUp() {
        datafile = new DataFile("Test Question", "Test Response");
        testFilePath = "data/browsing-history.txt";
    }

    @AfterEach
    void tearDown() {
        // Delete the file after each test
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIsPathValid() {
        System.out.println("Testing isPathValid method");
        // Test with a valid path
        assertTrue(DataFile.isPathValid(testFilePath));

        // Test with a null path
        assertFalse(DataFile.isPathValid(null));

        // Test with an empty path
        assertFalse(DataFile.isPathValid(""));

        // Test with a whitespace-only path
        assertFalse(DataFile.isPathValid("   "));

        // Test with a valid path containing whitespace
        assertTrue(DataFile.isPathValid("test file.txt"));
    }

    @Test
    public void testcreateFile() {
        System.out.println("Testing createFile method");
        DataFile.createFile();
        assertTrue(Files.exists(Paths.get(testFilePath)));
    }

    @Test
    void testDataWriterAndFileReader() throws IOException {
        System.out.println("Testing both dataWriter and fileReader methods");
        DataFile.createFile();
        // Write data to the file
        datafile.dataWriter();

        // Read data from the file
        datafile.fileReader();

        // Assert that the content read matches the expected output
        String actualContent = Files.readString(Paths.get(testFilePath));
        System.out.println("Actual content: " + actualContent);
        assertEquals("Q: Test Question\nA: Test Response\n\n", actualContent);

    }

    @Test
    void testByteCount() throws IOException {
        System.out.println("Testing byteCount method");
        DataFile.createFile();
        // Assuming createFile and dataWriter methods are working correctly
        datafile.dataWriter();
        String actualContent = Files.readString(Paths.get((testFilePath)));
        System.out.println("File Content: " + actualContent);

        long byteCount = datafile.byteCount();
        System.out.println("bytecount method was called");
        // Assert that the byte count is greater than 0 (indicating the file has
        // content)
        assertTrue(byteCount > 0);
    }

}
