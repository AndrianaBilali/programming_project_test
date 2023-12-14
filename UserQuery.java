package com.example;
import java.io.IOException;
import java.util.Scanner;

public class UserQuery {
    private String query ;
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
            query = query.substring(0,1).toUpperCase() +query.substring(1);
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
        while (query.matches("[a-zA-Z ?]+") != true){
                System.out.println("The query doesn't contain latin characters. Please reenter your query in the English language");
                String newQuery = input.nextLine(); //the user enters the query again
                query = newQuery;// place the new query in the query variable
        }
        return query;
    }
    //checks if the query is relative with the food
   public void foodContent() {
        boolean containsFood = query.contains("recipy");
        if (!containsFood) {
            System.out.println("The query doesn't relate with food. Please try again.");
            String s = input.nextLine();
            this.setQuery(s);
        }
    }
}


