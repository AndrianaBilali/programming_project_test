package gr.aueb.dmst;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserQueryTest {

    @Test
    void modifyQuery() throws IOException {
        // Initial query with inappropriate content
        UserQuery userQuery = new UserQuery("Tell me an inappropriate recipe");

        // Modify the query
        String modifiedQuery = userQuery.modify();

        // Ensure modifications are applied correctly
        assertEquals("Tell me an inappropriate recipe", modifiedQuery);
    }
}
