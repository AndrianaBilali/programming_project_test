package  gr.aueb.dmst;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import com.example.InappropriateContent;
public class InappropriateContentTest {

    @Test
    public void testCheckContent() {
        // Προσοχή: Αυτό είναι ένα απλό JUnit τεστ που χρησιμοποιεί ένα mock API key και κείμενο για ανάλυση.
        // Στην πραγματικότητα, θα πρέπει να χρησιμοποιήσετε ένα πραγματικό API key και κείμενο.
        
        String mockApiKey = "mock-api-key";
        String mockText = "This is a test text.";

        InappropriateContent inappropriateContent = new InappropriateContent(mockText);

        try {
            double toxicityScore = inappropriateContent.checkContent();

            // Ελέγχουμε αν η τιμή της τοξικότητας είναι μεταξύ 0 και 1
            assertTrue(toxicityScore >= 0 && toxicityScore <= 1);

        } catch (IOException e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }
}
