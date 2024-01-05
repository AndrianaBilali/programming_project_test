import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ResponseCheck {
    public String spellingCheck(String aiAnswer) {
        try{
            JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());
            // Detecting spelling errors
            List<RuleMatch> matches = languageTool.check(aiAnswer);
            String correctedText = aiAnswer; // Initialize with original text

            for (int i = matches.size() - 1; i >= 0; i--) {
                org.languagetool.rules.RuleMatch match = matches.get(i);
                // Check if the error is related to spelling
                if (match.getRule().getId().equals("MORFOLOGIK_RULE_EN_US")) {
                    List<String> suggestions = match.getSuggestedReplacements();

                    // Apply the first suggested correction to the text
                    if (!suggestions.isEmpty()) {
                        correctedText = correctedText.substring(0, match.getFrom()) +
                                suggestions.get(0) +
                                correctedText.substring(match.getTo());
                    }
                }
            }
                return correctedText;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String grammarCheck(String aiAnswer){
        // Create a language tool for American English
        Language language = new AmericanEnglish();
        JLanguageTool langTool = new JLanguageTool(language);

        try {
            // Check for grammar errors
            List<org.languagetool.rules.RuleMatch> matches = langTool.check(aiAnswer);

            // Correct errors if found
            String correctedText = aiAnswer; // Initialize with original text

            for (int i = matches.size() - 1; i >= 0; i--) {
                org.languagetool.rules.RuleMatch match = matches.get(i);
                List<String> suggestions = match.getSuggestedReplacements();

                // Apply the first suggested correction to the text
                if (!suggestions.isEmpty()) {
                    correctedText = correctedText.substring(0, match.getFrom()) +
                            suggestions.get(0) +
                            correctedText.substring(match.getTo());
                }
            }
            System.out.println("Corrected Text: " + correctedText);
            return correctedText;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Map<String, String> termReplacements = new HashMap<>();
    public static void cookingDictionary() {
        // Create a map of complex cooking terms and their simpler explanations
        Map<String, String> termReplacements = new HashMap<>();
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
        
        // Parse the AI-generated JSON response
        JSONObject recipeJson = new JSONObject(aiGeneratedRecipeJson);
        JSONObject recipe = recipeJson.getJSONObject("recipe");
        int originalServings = recipe.getInt("servings");
        JSONArray ingredients = recipe.getJSONArray("ingredients");
        
        // Adjust the recipe
        JSONArray adjustedIngredients = adjustServings(ingredients, originalServings, desiredServings);
        
        // Display the adjusted recipe
        System.out.println("Adjusted Recipe for " + desiredServings + " servings:");
        for (int i = 0; i < adjustedIngredients.length(); i++) {
            JSONObject ingredient = adjustedIngredients.getJSONObject(i);
            System.out.println(ingredient.getString("name") + ": " + ingredient.getDouble("quantity"));
        }
        boolean isRecipeValid = validateRecipe(aiGeneratedRecipeJson);
        
        // Display validation result
        if (isRecipeValid) {
            System.out.println("The recipe appears to be valid.");
        } else {
            System.out.println("The recipe contains errors or unusual combinations.");
        }
    }
    
    public static JSONArray adjustServings(JSONArray ingredients, int originalServings, int desiredServings) {
        JSONArray adjustedIngredients = new JSONArray();
        
        for (int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredient = ingredients.getJSONObject(i);
            String name = ingredient.getString("name");
            double originalQuantity = ingredient.getDouble("quantity");
            
            // Calculate the adjusted quantity based on desired servings
            double adjustedQuantity = (originalQuantity / originalServings) * desiredServings;
            
            // Create a new JSON object for adjusted ingredient
            JSONObject adjustedIngredient = new JSONObject();
            adjustedIngredient.put("name", name);
            adjustedIngredient.put("quantity", adjustedQuantity);
            
            adjustedIngredients.put(adjustedIngredient);
        }
        
        return adjustedIngredients;
    }

    public static boolean validateRecipe(String aiGeneratedRecipeJson) {
        boolean isValid = true;
        
        // Parse the AI-generated JSON response
        JSONObject recipeJson = new JSONObject(aiGeneratedRecipeJson);
        JSONObject recipe = recipeJson.getJSONObject("recipe");
        JSONArray ingredients = recipe.getJSONArray("ingredients");
        JSONArray steps = recipeJson.getJSONArray("steps");
        
        // Check ingredient quantities
        for (int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredient = ingredients.getJSONObject(i);
            double quantity = ingredient.getDouble("quantity");
            
            if (quantity <= 0) {
                isValid = false;
                System.out.println("Invalid quantity for ingredient: " + ingredient.getString(ingredients[i]));
            }
            // Add more checks as needed (e.g., negative quantities, unusual values)
        }
        
        // Check recipe steps
        for (int i = 0; i < steps.length(); i++) {
            String step = steps.getString(i);
            
            // Perform validation on each step (e.g., checking for missing instructions)
            if (step.isEmpty()) {
                isValid = false;
                System.out.println("Empty step found.");
            }
            // Add more step validation as needed
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
