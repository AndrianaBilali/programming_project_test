package main.java.gr.aueb.dmst ;
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
    //total pre-processing of the query
    //returns the modified query
    public String modify() throws IOException {
        this.EmptyQuery();
        this.checkLanguage();
        this.TrimQuery();
        this.LowerCaseQuery();
        this.CapitalFirstLetter();
        this.remove_Newlines_Tabs();
        this.foodContent();
        
        InappropriateContent inobj = new InappropriateContent(query);
        double inappropriateContent = inobj.checkContent();
        if (inappropriateContent >= 0.5) {
            System.out.println("Your query contains inappropriate content. Please enter another query");
            String newQuery = input.nextLine(); //the user enters the query again
            setQuery(newQuery);// place the new query in the query variables
        }
        String mquery = getQuery();
        return mquery;
    }
    //removes the blank spaces from the query and returns it
    public void TrimQuery () {
        query = query.trim(); 
        
    }// converts the query in lower letters and returns it
    public void LowerCaseQuery() {
        query = query.toLowerCase();
        
    }// first letter capital
    public void CapitalFirstLetter() {
            query = query.substring(0,1).toUpperCase() +query.substring(1);}
    
    // checks if the user has not entered a query 
    public void EmptyQuery() {
        while (query.isEmpty() == true) {
            System.out.println("You have not entered a query. Please try again");
            String newQuery = input.nextLine(); //the user enters the query again
            query = newQuery;// place the new query in the query variable
            }
        }
    // removes the newline characters and tabs from the query    
    public void remove_Newlines_Tabs() {
        query = query.replaceAll("[\n\t]", "");
    }
    //checks if the query is written in the English language
    public void checkLanguage() {
        while (query.matches("[a-zA-Z ?]+") != true){
                System.out.println("The query doesn't contain latin characters. Please reenter your query in the English language");
                String newQuery = input.nextLine(); //the user enters the query again
                query = newQuery;// place the new query in the query variable
        }
        
    }
    //checks if the query is relatable with food
    public void foodContent() {
        boolean containsFood = (query.contains("recipy") || (query.contains("Recipy"))) ;
        while (true) {
            if (containsFood == false){
                System.out.println("The query doesn't relate with food. Please try again.");
                String s = input.nextLine();
                this.setQuery(s);
                 this.setQuery(s);
                this.EmptyQuery();
                this.checkLanguage();
                this.TrimQuery();
                this.LowerCaseQuery();
                this.CapitalFirstLetter();
                this.remove_Newlines_Tabs(); }
            else{
                break;
            }
              containsFood = (query.contains("recipy") || (query.contains("Recipy")));
        }
    }
    
    
    

      /*  public static void main(String[] args)  throws IOException {
        UserQuery n = new UserQuery("tell me a recipy that ");
        String mofified_query = n.modify();
        System.out.println(mofified_query);
    }*/
}


