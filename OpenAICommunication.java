import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.JSONObject;
import java.io.OutputStream;

public class OpenAICommunication {
    // setting the API endpoint
    private final URL url = new URL(https://api.openai.com/v1/chat/completions);

    //method to open connection
    public void openConnection(String userQuestion) { //parameter is the user's request
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //openning connection
        } catch (ClassCastException e) {
            System.err.prinln("Specified protocol is not HTTP.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connection error: " + e);
            System.exit(1);
        }

        connection.setRequestMethod("POST"); //indicating that i will be sending data to the model 
        connection.setRequestProperty("Content-Type", "application/json"); 
        connection.setRequestProperty("Authorization", "Bearer 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"); //openai key
        
        JSONObject jo = new JSONObject(); //creating json object
        jo.put("q: ", userQuestion) //passing the user's question into the json object
        String inputJSON = jo.toString(); //finally converting the json object to a string

        connection.setDoOutput(true);
        
    }
}
