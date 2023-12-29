package gr.aueb.dmst;

import java.util.Scanner;
import java.util.ArrayList;

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