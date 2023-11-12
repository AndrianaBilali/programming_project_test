package gr.aueb;
import java.util.ArrayList;

import java.util.Scanner;
public class Pre_Process {
    public static void main(String[]args) {
         // initialization of the Scanner object
        Scanner input = new Scanner(System.in);
        //executes this block until the user wants to quit
        while(true) {
            // message for welcome
            System.out.println("Welcome to πεινAI");
            // the user has to enter the desired ingredients 
            System.out.println("Please enter the ingredients that you want your meal to contain ");
            //initialization of the collection of the ingredients
            ArrayList<String> ingredients = new  ArrayList<String>();
            //initialization of the counter
            int counter = 1;
            //executes until there aren't more ingredients
            while(true) {
                //ask the user to enter ingredients
                System.out.println("Please enter the ingredient" + counter++);
                String ingr = input.nextLine();
                // adding the ingredient in our collection
                ingredients.add(ingr);
                // ask the user if he has more ingredients for entering
                System.out.println("if you don't have other ingredients for input press: 1");
                int choice = input.nextInt();
                //if there aren't any other ingredients exits the loop
                if (choice == 1 ){
                    break;
                }
            }
            System.out.println("the ingredients that you have entered are the following:");
            for (String value : ingredients) {
                System.out.println(value);
            }
            
            //the user has to enter the desired category of his/her meal
            System.out.println("Please enter the category of the meal : breakfast , lunch , dessert etc");
            String category = input.nextLine();
            // the user has to enter his preferences about the taste
            System.out.println("Please enter your preferences : sweet , salt , bitter, spicy  etc");
            String preferences = input.nextLine();
            // the user has to enter the available time for cooking
            System.out.println("Please enter the available time that you have for the preparation ");
            double time = input.nextDouble();
            // the user has to enter the allergys or intolerances that he may have
            System.out.println("Please tell me if you have any nutritional allergys or intolerances. If not enter /' null /'");
            String allergy = input.nextLine();
            // the user has to enter his nutrition plan (in case he has one)
            System.out.println("Please tell me if you are following a nutrition plan. if not enter /' null /'");
            String plan = input.nextLine();
            // asks the user if he wants to continue with preparing another meal or exit from the app
            System.out.println("Do you want to prepare another meal or quit the program ?");
            System.out.println("Press 1 to continue /n Press 2 to exit");
            int selection = input.nextInt();
            if (selection == 2) {
                break;// quits the app
            } 
        }
    }
}