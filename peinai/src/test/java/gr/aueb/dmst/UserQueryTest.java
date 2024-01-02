package gr.aueb.dmst;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.UserQuery;
class UserQueryTest {

    @Test
    void modifyQuery() throws IOException {
        // Initial query with inappropriate content
        UserQuery userQuery = new UserQuery("Tell me an inappropriate recipy");

        // Modify the query
        String modifiedQuery = userQuery.modify();

        // Ensure modifications are applied correctly
        assertEquals("Tell me an inappropriate recipy", modifiedQuery);
    }
}

