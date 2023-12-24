package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpenAICommunicationTest {
    private OpenAICommunication openAI;

    @BeforeEach
    public void setUp() {
        openAI = new OpenAICommunication();
        openAI.openConnection();
    }

    @AfterEach
    public void tearDown() {
        openAI.closeConnection();
    }

    @Test
    public void testSendRequestAndReceiveResponse() {
        String userQuestion = "Tell me a recipe with spagetti and cheese.";
        openAI.sendRequest(userQuestion);
        String response = openAI.receiveResponse();

        assertNotNull(response);

        System.out.println("Response:" + response);
    }
}
