import static org.junit.Assert.*;
import org.junit.Test;

public class OpenAICommunicationTest {

    @Test
    public void testOpenConnection() {
        OpenAICommunication openAIComm = new OpenAICommunication();
        openAIComm.openConnection();
        assertNotNull(openAIComm.connection);
    }

    @Test
    public void testSendRequest() {
        OpenAICommunication openAIComm = new OpenAICommunication();
        openAIComm.openConnection();
        openAIComm.sendRequest("Tell me a recipe with pasta cheese and tomatos");
        assertNotNull(openAIComm.connection);
    }
    @Test
    public void testReceiveResponse() {
    OpenAICommunication openAIComm = new OpenAICommunication();
    openAIComm.openConnection();
    openAIComm.recieveResponse("Test question");
    String response = openAIComm.getResponse();
    // Assert that the response is not empty
    assertNotNull(response);
    assertFalse(response.isEmpty());
}
}
