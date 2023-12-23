package test.java;
import org.junit.Before;
import org.junit.Test;

import com.example.InappropriateContent;
import com.example.UserQuery;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class UserQueryTest {

    public UserQuery userQuery;

    @Before
    public void setUp() {
        // Initialization of userQuery before each test
        userQuery = new UserQuery("tell me a recipy that");
    }

    @Test
    public void testTrimQuery() {
        userQuery.TrimQuery();
        assertEquals("tell me a recipy that", userQuery.getQuery());
    }

    @Test
    public void testLowerCaseQuery() {
        userQuery.LowerCaseQuery();
        assertEquals("tell me a recipy that", userQuery.getQuery());
    }

    @Test
    public void testCapitalFirstLetter() {
        userQuery.CapitalFirstLetter();
        assertEquals("Tell me a recipy that", userQuery.getQuery());
    }

    @Test
    public void testEmptyQuery() {
        userQuery.setQuery("");
        userQuery.EmptyQuery();
        assertEquals("You have not entered a query. Please try again", userQuery.getQuery());
    }

    @Test
    public void testremove_Newlines_Tabs() {
        userQuery.remove_Newlines_Tabs();
        assertEquals("tell me a recipy that", userQuery.getQuery());
    }

    @Test
    public void testcheckLanguage() {
        userQuery.setQuery("Καλημέρα");
        userQuery.checkLanguage();
        assertEquals("The query doesn't contain Latin characters. Please reenter your query in the English language", userQuery.getQuery());
    }

    @Test
    public void testfoodContent() {
        userQuery.setQuery("tell me a story");
        userQuery.foodContent();
        assertEquals("The query doesn't relate to food. Please try again.", userQuery.getQuery());
    }
    @Test
    public void testModify() throws IOException {
        // Δημιουργία αντικειμένου UserQuery με αρχικό κείμενο
        UserQuery userQuery = new UserQuery("tell me a recipy that");

        // Κλήση της μεθόδου modify()
        String result = userQuery.modify();

        // Έλεγχος των αναμενόμενων αλλαγών στο κείμενο
        assertEquals("Tell me a recipy that", result);
    }
    @Test
    public void testSetQuery() {
        // Χρήση της μεθόδου setQuery
        userQuery.setQuery("Αυτή είναι μια δοκιμαστική ερώτηση.");

        // Επιβεβαίωση ότι η μέθοδος getQuery επιστρέφει τη σωστή τιμή
        assertEquals("Αυτή είναι μια δοκιμαστική ερώτηση.", userQuery.getQuery());
    }

    @Test
    public void testGetQuery() {
        // Χρήση της μεθόδου setQuery για να ορίσετε μια ερώτηση
        userQuery.setQuery("Αυτή είναι μια δοκιμαστική ερώτηση.");

        // Επιβεβαίωση ότι η μέθοδος getQuery επιστρέφει τη σωστή τιμή
        assertEquals("Αυτή είναι μια δοκιμαστική ερώτηση.", userQuery.getQuery());
    }

}
