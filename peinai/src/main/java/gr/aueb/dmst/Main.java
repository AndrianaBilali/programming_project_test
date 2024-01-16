package gr.aueb.dmst;

import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to Peinai!");
        System.out.println(
                "If you wish to quit please enter 0. If you wish to continue, please enter 1");
        int choise = in.nextInt();
        // The app starts if the user enters 1
        while (choise == 1) {
            System.out.println("New user! Please enter the following information: ");
            // sign up part
            // user information

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("\nEnter password (Password must be at least 8 characters long): ");
            String password = sc.nextLine();

            System.out.print("\nEnter email: ");
            String email = sc.nextLine();

            System.out.print("\nEnter gender (female/male/other): ");
            String gender = sc.nextLine();

            System.out.print("\nEnter birth date (YYYY-MM-DD): ");
            String birthDateString = sc.nextLine();
            LocalDate birthDate = LocalDate.parse(birthDateString);

            System.out.print("\nEnter allergy ");
            System.out.print("(If you have no allergies input 'no allergies') :\n");
            String allergy = sc.nextLine();

            // preferences information
            // favorite ingredients of the user
            Set<String> favIngredients = new HashSet<>();
            System.out.println("Enter favorite ingredients (type 'exit' to stop): ");
            while (true) {
                System.out.print("Enter an ingredient: ");
                // read the ingredient and trim any accidental whitespaces
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                favIngredients.add(input);
                System.out.println("Enter favorite ingredients (type 'exit' to stop):");
            }
            // verification of the favorite ingredients
            System.out.println("Your favorite ingredients: " + favIngredients);

            // ingredients the user does not like
            Set<String> worstIngredients = new HashSet<>();
            System.out.println("Enter ingredients you don't like (type 'exit' to stop): ");
            while (true) {
                System.out.print("Enter an ingredient: ");
                // read the ingredient and trim amy accidental whitespaces again
                String input2 = sc.nextLine().trim();
                if (input2.equalsIgnoreCase("exit")) {
                    break;
                }
                worstIngredients.add(input2);
                System.out.println("Enter ingredients you don't like (type 'exit' to stop): ");
            }
            System.out.println("Ingredients you don't like: " + worstIngredients);

            Preferences preferences = new Preferences(allergy, favIngredients,
                    worstIngredients);
            // Create User object (create new user)
            User user = new User(username, password, email, gender, birthDate,
                    preferences);

            // display the user profile
            System.out.println("User Profile: ");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Age: " + user.calculateAge());
            System.out.println("Preferences: ");
            System.out.println("Allergy: " + preferences.getAllergy());
            System.out.println("Favorite Ingredients: " +
                    preferences.getFavIngredients());
            System.out.println("Worst Ingredients: " +
                    preferences.getWorstIngredients());

            // loop for the recipe
            int a = 1;
            while (true) {
                // Creating OpenAiCommunication object
                // We want to open and close the connection for each recipe
                OpenAICommunication openAI = new OpenAICommunication();
                openAI.openConnection();
                System.out.println("Recipe number: " + a);
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
                    int choise2 = in.nextInt();
                    c++;
                    if (choise2 == 0) {
                        break;
                    }
                }

                // Verification of the ingredients
                System.out.println("The ingredients you have are: ");
                for (String value : ingredients) {
                    System.out.println(value);
                }
                System.out.println("Please wait while we are preparing your recipe...");

                // Appending the user question to send it to the api.
                String userQuestion = "Can you give me a recipe with the following ingredients: "
                        + ingredients.toString() + " favorite ingredients include: " + preferences.getFavIngredients()
                        + " worst ingredients include: " + preferences.getWorstIngredients() + " allergies: "
                        + preferences.getAllergy();

                // Pre process of the user's question
                // Object with the question as a parameter
                UserQuery m = new UserQuery(userQuestion);
                // Calling the method that processes everything on the object
                String modifiedUserQuestion = null;
                try {
                    modifiedUserQuestion = m.modify();
                } catch (IOException e) {
                    System.out.println("exception in modify() + " + e.getMessage());
                }

                openAI.sendRequest(modifiedUserQuestion);
                String apiResponse = openAI.receiveResponse();
                // Post-processing
                ResponseCheck resp = new ResponseCheck();
                Recipe result = resp.PostProcessingfirst(apiResponse, ingredients, preferences.getAllergy());
                System.out.println(result.getName());
                for (String step : result.getSteps()) {
                    System.out.println(step);
                }
                System.out.println(result.getDescription());

                // The recipe will be saved in a file
                DataFile datafile = new DataFile(modifiedUserQuestion, apiResponse);
                System.out.println("Your recipe will now be saved in a file...");

                DataFile.createFile();

                datafile.dataWriter();

                long byteCount = datafile.byteCount();
                System.out.println("File byte size: " + byteCount);
                // Closing the connection to make sure that if the user does not want another
                // recipe it won't stay open
                openAI.closeConnection();

                System.out.println("Would you like to enter ingredients for another recipe?");
                System.out.println("If yes, please enter 1, else enter 0");
                int ans = in.nextInt();
                if (ans == 0) {
                    break;
                }
                a++;
            }

            // Asking again if the user wants to quit the app
            System.out.println(
                    "If you wish to quit please enter 0. If you wish to continue, please enter 1");
            choise = in.nextInt();
        }
        // Closing the Scanner when the user quits the app
        sc.close();
        in.close();
    }
}
