import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.json.JSONObject; //add dependency on maven
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class OpenAICommunication {
    // setting the API endpoint
    private static final String endpoint = "https://api.openai.com/v1/chat/completions";
    HttpURLConnection connection = null;

    public void openConnection() {
        URL url = null;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            System.err.println("Invalid url: " + e);
            System.exit(1);
        }
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

    public void sendRequest(String userQuestion) {
        try {
            connection.setRequestMethod("POST"); // indicating that i will be sending data to the model
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer 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"); // key
        try {
            JSONObject jo = new JSONObject(); // creating json object
            jo.put("q: ", userQuestion); // passing the user's question into the json object
            String inputJSON = jo.toString(); // finally converting the json object to a string
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        connection.setDoInput(true); // to indicate that i am going to give data
        connection.setDoOutput(true); // to indicate that i am expecting the model's response

        try {
            OutputStream obj = connection.getOutputStream(); // creating an object
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] array = inputJSON.getBytes("utf-8"); // interpreting the text to bytes
        try {
            obj.write(array, 0, array.length); // writing all the bytes to the stream
            obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // At this point we have made the connection to the server and sent the user's
        // request.
        // We are now awaiting the response.
    }

    // method to recieve response
    public void recieveResponse() {
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.err.println("Protocol Exception: " + e);
            System.exit(1);
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            System.err.println("Connection error: " + e);
            System.exit(1);
        }

        int c;
        StringBuilder response = new StringBuilder();
        try {
            while ((c = in.read()) != -1) {
                response.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
    }
}
