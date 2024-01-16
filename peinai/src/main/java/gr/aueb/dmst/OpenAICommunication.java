package gr.aueb.dmst;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.DataOutputStream;

/**
 * This class makes a connection to an API endpoint through REST API
 * And receives the response which is the recipe
 */

public class OpenAICommunication {
    // setting the API endpoint
    private static final String endpoint = "https://api.openai.com/v1/chat/completions";
    HttpURLConnection connection;

    // constructor of the class
    public OpenAICommunication() {
        connection = null;
    }

    // method to open the connection
    public void openConnection() {
        URL url = null;
        try {
            // url connection to the endpoint
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            System.err.println("Invalid url: " + e);
            System.exit(1);
        }
        try {
            // openning the connection and catching some exceptions
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

    public void sendRequest(String userQuestion) {
        String apiKey = System.getenv("API_KEY"); // environment variable
        apiKey = apiKey.trim();
        try {
            connection.setRequestMethod("POST"); // to indicate that data is to be sent to the model
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoOutput(true); // to indicate that i am expecting the model's response
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", apiKey); // key

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {

            // Create ObjectMapper object
            ObjectMapper objectMapper = new ObjectMapper();

            // Create the paylod of the json object
            ObjectNode payloadJson = objectMapper.createObjectNode();
            payloadJson.put("model", "gpt-3.5-turbo");
            ObjectNode message = objectMapper.createObjectNode();
            message.put("role", "user");
            message.put("content", userQuestion
                    + "Describe the recipe in a maximum of 200 words.");
            payloadJson.set("messages", objectMapper.createArrayNode().add(message));
            payloadJson.put("temperature", 0.7);

            wr.writeBytes(objectMapper.writeValueAsString(payloadJson)); // writing the payload
                                                                         // to the outputStream
            wr.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // At this point we have made the connection to the api and sent the user's
        // request.
        // We are now awaiting the response.
    }

    // method to recieve response
    public String receiveResponse() {
        StringBuilder response = new StringBuilder();
        try {
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode); // code=200 if i have an answer
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            // building the api's response
            String c;
            while ((c = in.readLine()) != null) {
                response.append(c.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    // method to close the connection
    public void closeConnection() {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
