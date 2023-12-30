package gr.aueb.dmst;

import java.util.Scanner;
import java.util.Set;
import java.util.prefs.Preferences;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        // Initializing the scanner
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Peinai!");
        System.out.println(
                "If you wish to quit please enter 0. If you wish to continue, please enter 1");
        int choise = sc.nextInt();
        // Creating OpenAiCommunication object
        // The connection opens here because we don't want it to open and close evrry
        // time in the loop
        OpenAICommunication openAI = new OpenAICommunication();
        openAI.openConnection();
        // The app starts if the user enters
        while (choise == 0) {

            // sign up part
            // user information
            // username
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            // password
            System.out.print("\nEnter password: ");
            String password = sc.nextLine();
            // email
            System.out.print("\nEnter email: ");
            String email = sc.nextLine();
            // gender
            System.out.print("\nEnter gender (female/male/other): ");
            String gender = sc.nextLine();
            // birth date
            System.out.print("\nEnter birth date (YYYY-MM-DD): ");
            String birthDateString = sc.nextLine();
            LocalDate birthDate = LocalDate.parse(birthDateString);

            System.out.print("\nEnter allergy: ");
            System.out.print("If you have no allergies press 'n'");
            String allergy = sc.nextLine();

            // preferences information
            // favorite ingredients of the user
            Set<String> favIngredients = new HashSet<>();
            System.out.println("Enter favorite ingredients (type 'exit' to stop):");
            while (true) {
                System.out.print("Enter an ingredient: ");
                // read the ingredient and trim amy accidental whitespaces
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                favIngredients.add(input);
            }
            // verification of the favorite ingredients
            System.out.println("Your favorite ingredients: " + favIngredients);

            // ingredients the user does not like
            Set<String> worstIngredients = new HashSet<>();
            System.out.println("Enter ingredients you don't like (type 'exit' to stop):");
            while (true) {
                System.out.print("Enter an ingredient: ");
                // read the ingredient and trim amy accidental whitespaces again
                String input2 = sc.nextLine().trim();
                if (input2.equalsIgnoreCase("exit")) {
                    break;
                }
                worstIngredients.add(input2);
            }
            System.out.println("Ingredients you don't like: " + favIngredients);

            // Create Preferences object
            Preferences preferences = new Preferences(allergy, favIngredients, worstIngredients);
            // Create User object (create new user)
            User user = new User(username, password, email, gender, birthDate, preferences);

            // display the user profile
            System.out.println("User Profile: ");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Age: " + user.calculateAge());
            System.out.println("Preferences: ");
            System.out.println("Allergy: " + preferences.getAllergy());
            System.out.println("Favorite Ingredients: " + preferences.getFavIngredients());
            System.out.println("Worst Ingredients: " + preferences.getWorstIngredients());

            // user profile here
            // if we have the same user then no need to ask for credentials
            // we want to keep the username throughout the loop and check it here at the
            // start

            System.out.println("Please enter the ingredients that you want your meal to contain ");
            // Initialization of the ArrayList that contains the ingredients
            ArrayList<String> ingredients = new ArrayList<String>();

            // Loop for the input of the ingredients
            int c = 1;
            while (true) {
                System.out.println("Please enter ingredient number " + c);
                String ingredient = sc.nextLine();
                ingredients.add(ingredient);
                System.out.println("If you have other ingredients for input press 1, else press 0.");
                int choise2 = sc.nextInt();

                if (choise2 == 0) {
                    break;
                }
            }

            // Verification of the ingredients
            System.out.println("The ingredients you have are: ");
            for (String value : ingredients) {
                System.out.println(value);
            }

            // Appending the user question to send it to the api.
            String userQuestion = "Can you give me a recipe with the following ingredients: "
                    + ingredients.toString();

            // Pre process of the user's question
            // Object with the question as a parameter
            UserQuery m = new UserQuery(userQuestion);
            // Calling the method that processes everything on the object
            String modifiedUserQuestion = m.modify();

            openAI.sendRequest(modifiedUserQuestion);
            String apiResponse = openAI.receiveResponse();
            // Celia
            // I will probably need a second loop for post processing
            System.out.println(apiResponse);

            // The recipe will be saved in a file
            DataFile datafile = new DataFile(modifiedUserQuestion, apiResponse); // remember to use the modified api
                                                                                 // response
            System.out.println("Your recipe will now be saved in a file...");
            // Creating the file if it doesn't exist
            DataFile.createFile();
            // Saving the ingredients and recipe in the file
            datafile.dataWriter();
            // Counting the bytes of the file
            long byteCount = datafile.byteCount();
            System.out.println("The recipe has successfully been saved to the file!");

            // Asking again if the user wants to quit the app
            System.out.println(
                    "If you wish to quit please enter 0. If you wish to continue, please enter 1");
            choise = sc.nextInt();
        }
        // Closing the connection and Scanner when the user quits the app
        openAI.closeConnection();
        sc.close();
    }
}