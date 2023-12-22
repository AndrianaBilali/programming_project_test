package gr.aueb.dmst;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;

public class ResponseCheck {
    public String SpellingAndGrammarCheck(String aiAnswer) {
        try{
            JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());
            // Detecting spelling errors
            List<RuleMatch> matches = languageTool.check(aiAnswer);
            StringBuilder correctedText = new StringBuilder(aiAnswer);
        
            for (int i = matches.size() - 1; i >= 0; i--) {
                RuleMatch match = matches.get(i);
                int fromIndex = match.getFromPos();
                int toIndex = match.getToPos();
                String suggestion = match.getSuggestedReplacements().get(0);
                correctedText.replace(fromIndex, toIndex, suggestion);
                
            }
             return correctedText.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Map<String, String> termReplacements = new HashMap<>();
    // Create a map of complex cooking terms and their simpler explanations
    public void createCookingDictionary() {
        termReplacements.put("Julienne","To cut meat, vegetables or fruit into long, very thin strips");
        termReplacements.put("Marinate", "To let food stand in seasonings that include at least one wet ingredient to tenderize and increase the flavor.");
        termReplacements.put("Mince", "To cut or chop food into very small pieces.");
        termReplacements.put("Parboil", "To cook food in a boiling liquid just until partially done. Cooking may be completed using another method or at another time");
        termReplacements.put("Pare", "To remove the outer peel or skin of a fruit or vegetable with a knife.");
        termReplacements.put("Poach", "To cook slowly in a liquid such as water, seasoned water, broth or milk, at a temperature just below the boiling point.");
        termReplacements.put("Prove", "To let dough or yeast mixture rise before baking.");
        termReplacements.put("Purée", "To put food through a sieve, blender or food processor in order to produce a thick pulp.");
        termReplacements.put("Render","To meld solid fat (eg from beef or pork) slowly in the oven.");
        termReplacements.put("Roast","To cook meat or vegetables in an uncovered pan in an oven using dry heat.");
        termReplacements.put("Sauté","To brown or cook meat, fish, vegetables or fruit in a small amount of fat ");
        termReplacements.put("Scald", "To heat milk until just below the boiling point, when you will see tiny bubbles appearing around the edges of the pan.");
        termReplacements.put("Score", "To make shallow slits into the food, usually in a rectangular or diamond pattern.");
        termReplacements.put("Sear","To cook meat quickly at high heat to seal the surface of the meat and produce a brown color.");
        termReplacements.put("Shred","To cut into long thin strips with a knife or shredder.");
        termReplacements.put("Simmer","To cook in liquid that is just below the boiling point. Bubbles will form slowly and burst before reaching the surface.");
        termReplacements.put("Sliver", "To cut into long thin pieces with a knife");
        termReplacements.put("Steam", "To cook in a covered container over boiling water. The container should have small holes in it to allow the steam from the water to rise.");
        termReplacements.put("Steep", "To let a food stand for a few minutes in just boiled water to increase flavor and color.");
        termReplacements.put("Stew", "To simmer slowly in enough liquid to cover.");
        termReplacements.put("Stir Fry", "To cook in a frying pan or wok over high heat in a small amount of fat, stirring constantly.");
        termReplacements.put("Sweat", "To cook gently, usually in butter, a bit of oil, or the foods own juices to soften but not brown the food." );
        termReplacements.put("Toast", "To brown with dry heat in an oven or toaster.");
        termReplacements.put("Whip", "To beat rapidly with a wire whisk, beater or electric mixer to incorporate air, lighten and increase volume.");
        termReplacements.put("Zest", "To grate the outer, colored portion of the skin of a citrus fruit, avoiding the white pith. The thin parings that result are also called the zest.");
    }
    
    // επεξήγηση των περίπλοκων όρων
     public void simplifyTerms(String text) {
        // χωρίζω το κείμενο σε λέξεισ
        String[] words = text.split("\\s+");

        // τσεκάρω τισ λέξεισ για δύσκολουσ μαγειρικούς όρους
        for (String word : words) {
            if (termReplacements.containsKey(word)) {
                String definition = termReplacements.get(word);
                System.out.println("Term: " + word);
                System.out.println("Definition: " + definition);
                System.out.println(" ");
            }
        }
     }
    public void RecipeFormat(String aiGeneratedRecipeJson) {
        // Desired number of servings
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int desiredServings = scanner.nextInt();
        scanner.close();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the AI-generated JSON response
            JsonNode recipeJson = objectMapper.readTree(aiGeneratedRecipeJson);
            JsonNode recipe = recipeJson.get("recipe");
            int originalServings = recipe.get("servings").asInt();
            ArrayNode ingredients = (ArrayNode) recipe.get("ingredients");
        
            // Adjust the recipe
            ArrayNode adjustedIngredients = adjustServings(ingredients, originalServings, desiredServings);
        
            // Display the adjusted recipe
            System.out.println("Adjusted Recipe for " + desiredServings + " servings:");
            for (JsonNode ingredient : adjustedIngredients) {
                System.out.println(ingredient.get("name").asText() + ": " + ingredient.get("quantity").asDouble());
            }
            boolean isRecipeValid = validateRecipe(aiGeneratedRecipeJson);
        
            // Display validation result
            if (isRecipeValid) {
                System.out.println("The recipe appears to be valid.");
            } else {
                System.out.println("The recipe contains errors or unusual combinations.");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayNode adjustServings(ArrayNode ingredients, int originalServings, int desiredServings) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode adjustedIngredients = objectMapper.createArrayNode();

        for (JsonNode ingredient : ingredients) {
            String name = ingredient.get("name").asText();
            double originalQuantity = ingredient.get("quantity").asDouble();

            // Calculate the adjusted quantity based on desired servings
            double adjustedQuantity = (originalQuantity / originalServings) * desiredServings;
            
            // Create a new JSON object for adjusted ingredient
            ObjectNode adjustedIngredient = objectMapper.createObjectNode();
            adjustedIngredient.put("name", name);
            adjustedIngredient.put("quantity", adjustedQuantity);
            
            adjustedIngredients.add(adjustedIngredient);
        }
        
        return adjustedIngredients;
    }

    public static boolean validateRecipe(String aiGeneratedRecipeJson) {
        boolean isValid = true;
        try{
        // Parse the AI-generated JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode recipeJson = objectMapper.readTree(aiGeneratedRecipeJson);

        JsonNode recipe = recipeJson.get("recipe");
        JsonNode ingredients = recipe.get("ingredients");
        JsonNode steps = recipe.get("steps");

        
        // Check ingredient quantities
        for (JsonNode ingredient : ingredients) {
            double quantity = ingredient.get("quantity").asDouble();

            if (quantity <= 0) {
                isValid = false;
                System.out.println("Invalid quantity for ingredient: " + ingredient.get("name").asText());
            }
        
        }
        // Check recipe steps
         for (JsonNode step : steps) {
            String stepText = step.asText();

            // Perform validation on each step (e.g., checking for missing instructions)
            if (stepText.isEmpty()) {
                isValid = false;
                System.out.println("Empty step found.");
            }
            // Add more step validation as needed
        }
        } catch (Exception e) {
        e.printStackTrace();
        isValid = false;
        }
        return isValid;
    }

     public static boolean isWordInText(String text, String word) {
    // Convert text to lowercase to perform case-insensitive check
    String lowercaseText = text.toLowerCase();
    // Split the text into words using whitespace as a delimiter
    String[] words = lowercaseText.split("\\s+");
    // Iterate through the words to check if the given word exists
    for (String w : words) {
        // Remove punctuation from the word for better matching
        String cleanedWord = w.replaceAll("[^a-zA-Z]", "");
        // Check if the cleaned word matches the specified word
        if (cleanedWord.equals(word.toLowerCase())) {
            return true; // Word found in the text
        }
    }
    return false; // Word not found in the text
    }

    public static String AllergyCheck(String aiText, String allergy) throws Allergyexception {
        boolean found = isWordInText(aiText, allergy);
        if(found) {
            throw new Allergyexception("Allergy found in the recipe");
        } 
        if(found) {
            return "Allergy" + allergy + "found in the recipe, give me a new one";
         } else {
            return null;
        }
    }

    public static void PreferencesCheck(String aiText, ArrayList<String> ingredients){
        int counter = 0;
        for (String ingredient : ingredients) {
            boolean found = isWordInText(aiText, ingredient);
            if (found) {
                counter ++;
            } else {
                System.out.println("the recipe doesn't contain " + ingredient);
            }
        }
    }
}
class Allergyexception extends Exception {
    public Allergyexception(String message) {
        super(message);
    }
}
