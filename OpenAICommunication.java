import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import org.json.JSONObject; //add dependency on maven
import java.io.OutputStream;
import java.net.URI;

public class OpenAICommunication {
    // setting the API endpoint
    private final URL url = new URL("https://api.openai.com/v1/chat/completions");
    private HttpURLConnection connection = (HttpURLConnection) null;

    public void openConnection() {
        try {
            connection = (HttpURLConnection) url.openConnection(); // openning connection
        } catch (ClassCastException e) {
            System.err.println("Specified protocol is not HTTP.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connection error: " + e);
            System.exit(1);
        } catch (NullPointerException e) {
            System.err.println("No connection found.");
            System.exit(1);
        }
    }

    public void sendRequest(String userQuestion, HttpURLConnection connection) {
        connection.setRequestMethod("POST"); // indicating that i will be sending data to the model
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer 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"); // openai
                                                                                                                      // key
        JSONObject jo = new JSONObject(); // creating json object
        jo.put("q: ", userQuestion); // passing the user's question into the json object
        String inputJSON = jo.toString(); // finally converting the json object to a string

        connection.setDoOutput(true); // to indicate that i am expecting the model's response

        OutputStream obj = connection.getOutputStream(); // creating an object
        byte[] array = inputJSON.getBytes("utf-8"); // interpreting the text to bytes
        obj.write(array, 0, array.length); // writing all the bytes to the stream

        // At this point we have made the connection to the server and sent the user's
        // request.
        // We are now awaiting the response.
    }

    // method to recieve response
    public void recieveResponse(HttpURLConnection connection) {

    }
}
