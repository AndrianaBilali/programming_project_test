package gr.aueb.dmst;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // initializing the scanner
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Peinai!");
        System.out.println(
                "If you wish to quit please enter 0. If you wish to continue, please enter 1");
        int choise = sc.nextInt();
        // the app starts if the user enters
        while (choise == 0) {
            // user profile here
            // if we have the same user then no need to ask for credentials
            // we want to keep the username throughout the loop and check it here at the
            // start

            System.out.println("Please enter the ingredients that you want your meal to contain ");
            // initialization of the ArrayList that contains the ingredients
            ArrayList<String> ingredients = new ArrayList<String>();

            // loop for the input of the ingredients
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

            // verification of the ingredients
            System.out.println("The ingredients you have are: ");
            for (String value : ingredients) {
                System.out.println(value);
            }

            // appending the user question to send it to the api.
            String userQuestion = "Can you give me a recipe with the following ingredients: "
                    + ingredients.toString();

            // creating OpenAiCommunication object
            OpenAICommunication openAI = new OpenAICommunication();
            openAI.openConnection();

            // Nefeli pre process
            // i have a result of type string
            // i will probably have a loop here in case i need to send again
            openAI.sendRequest(userQuestion);
            String apiResponse = openAI.receiveResponse();
            // Celia
            // I will probably need a second loop
            System.out.println(apiResponse);

            // asking again if the user wants to quit the app.
            System.out.println(
                    "If you wish to quit please enter 0. If you wish to continue, please enter 1");
            choise = sc.nextInt();
        }
        sc.close();
    }
}