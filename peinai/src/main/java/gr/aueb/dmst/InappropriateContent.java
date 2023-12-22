package main.java.gr.aueb.dmst;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InappropriateContent {
     // Set your API key here
         private static final String apiKey = "AIzaSyBCn3o2sQkPcRTLCKn4shgrk3HnTUqYI0o";
         private String  textToAnalyze ;
           
           public InappropriateContent(String textToAnalyze){
            // Specify the text to be analyzed
           this.textToAnalyze = textToAnalyze;}
        public double checkContent()  throws IOException {
            
            // Send the request and get the response
            String toxicityScore = analyzeText(apiKey, textToAnalyze);
            String extractedToxicityScore = extractToxicityScore(toxicityScore);
            String input = extractedToxicityScore ;
            String result = input.substring(17);
            double doubletoxicity = Double.parseDouble(result);

            // Print the toxicity score
            System.out.println("Toxicity: " + doubletoxicity);
            return doubletoxicity;
        }

    private static String analyzeText(String apiKey, String text) throws IOException {
        String apiUrl = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=AIzaSyBCn3o2sQkPcRTLCKn4shgrk3HnTUqYI0o";

        // Create the JSON payload
        String jsonPayload = "{\"comment\": {\"text\": \"" + text + "\"}, \"requestedAttributes\": {\"TOXICITY\": {}}}";

        // Create the HTTP connection
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the JSON payload
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            wr.write(input, 0, input.length);
        }

        // Check the HTTP response code
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } else {
            // Handle the error, maybe by reading from connection.getErrorStream()
            throw new IOException("HTTP request failed with status: " + connection.getResponseCode());
           
    }
        }
        private static String extractToxicityScore(String jsonResponse) {
            int indexOfToxicity = jsonResponse.indexOf("\"summaryScore\": ");
            if (indexOfToxicity != -1) {
                int startIndex = indexOfToxicity + "\"summaryScore\": ".length();
                int endIndex = jsonResponse.indexOf(",", startIndex);
                if (endIndex == -1) {
                    endIndex = jsonResponse.indexOf("}", startIndex);
                }
                if (endIndex != -1) {
                    String toxicityScoreString = jsonResponse.substring(startIndex, endIndex);
                    return toxicityScoreString.trim();
                }
            }
            return "N/A";
        }
    }
