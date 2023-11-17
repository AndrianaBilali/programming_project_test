
import org.apache.commons.lang3.StringUtils;


public class UserQuery {
    private String query ;
    // receives the user's query 
    UserQuery() {
        query = "";
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
            query = "";
            }
            return query;
        }
    // removes the newline characters and tabs from the query    
    public String remove_Newlines_Tabs() {
        query = query.replaceAll("[\n\t]", "");
        return query;
    }
}


