package com.example;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.commons.lang3.StringUtils;
import java.util.Scanner;

public class UserQuery {
    private String query ;
    String apiKey = "AIzaSyCIvPSelJS85H7dnqWxaRd1GOD9wNBZ5dc";
    Scanner input = new Scanner(System.in); // declaration and initializtion of a Scanner object
    // receives the user's query 
    UserQuery(String query) {
        this.query = query;
    }
    //setters and getters
    public void setQuery(String query){
        this.query = query;
    }
     public String getQuery(){
        return query;
    }
    //removes the blank spaces from the query and returns it
    public String TrimQuery () {
        query = query.trim();
        return query; 
    }// converts the query in lower letters and returns it
    public String LowerCaseQuery() {
        query = query.toLowerCase();
        return query;
    }// first letter capital
    public String CapitalFirstLetter() {
        query = StringUtils.capitalize(query);
        return query;
    }// checks if the user has not entered a query 
    public String EmptyQuery() {
        while (query.isEmpty() == true) {
            System.out.println("You have not entered a query. Please try again");
            String newQuery = input.nextLine(); //the user enters the query again
            query = newQuery;// place the new query in the query variable
            }
            return query;
        }
    // removes the newline characters and tabs from the query    
    public String remove_Newlines_Tabs() {
        query = query.replaceAll("[\n\t]", "");
        return query;
    }
    //checks if the query is written in the English language
    public String checkLanguage() {
        while (!query.matches("[a-zA-Z]+")) {
                System.out.println("The query doesn't contain latin characters. Please reenter your query in the English language");
                String newQuery = input.nextLine(); //the user enters the query again
                query = newQuery;// place the new query in the query variable
        }
        return query;
    }
    //checks for sensitive or inappropriate content
    public String checkContent() {
        HttpResponse<JsonNode> response = Unirest.post("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey)
        .header("Content-Type", "application/json")
        .body("{\"comment\":{\"text\":\"" + query + "\"},\"languages\":[\"en\"],\"requestedAttributes\":{\"TOXICITY\":{}}}")
        .asJson();

        System.out.println(response.getBody().toPrettyString());
        double toxicity = response.getBody().getObject().getJSNObject("attributeScores").getJSNObject("TOXICITY").getJSNObject("summaryScore").getDouble("value");
        System.out.println("Inappropriate content" + toxicity);
        if (toxicity >= 0.1) {
        System.out.println("The query that you have entered contains inappropriate content. Please reenter your query");
        String newQuery = input.nextLine(); //the user enters the query again
        query = newQuery;// place the new query in the query variable
        }
        return query;
    }
}


