package gr.aueb.dmst;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ResponseCheck {
    public String SpellingAndGrammarCheck(String aiAnswer) {
        try {
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

    private final Map<String, String> termReplacements = new HashMap<>();

    // Create a map of complex cooking terms and their simpler explanations
    public void createCookingDictionary() {
        termReplacements.put("julienne", "To cut meat, vegetables or fruit into long, very thin strips");
        termReplacements.put("marinate",
                "To let food stand in seasonings that include at least one wet ingredient to tenderize and increase the flavor.");
        termReplacements.put("mince", "To cut or chop food into very small pieces.");
        termReplacements.put("parboil",
                "To cook food in a boiling liquid just until partially done. Cooking may be completed using another method or at another time");
        termReplacements.put("pare", "To remove the outer peel or skin of a fruit or vegetable with a knife.");
        termReplacements.put("poach",
                "To cook slowly in a liquid such as water, seasoned water, broth or milk, at a temperature just below the boiling point.");
        termReplacements.put("prove", "To let dough or yeast mixture rise before baking.");
        termReplacements.put("purée",
                "To put food through a sieve, blender or food processor in order to produce a thick pulp.");
        termReplacements.put("render", "To meld solid fat (eg from beef or pork) slowly in the oven.");
        termReplacements.put("roast", "To cook meat or vegetables in an uncovered pan in an oven using dry heat.");
        termReplacements.put("sauté", "To brown or cook meat, fish, vegetables or fruit in a small amount of fat ");
        termReplacements.put("scald",
                "To heat milk until just below the boiling point, when you will see tiny bubbles appearing around the edges of the pan.");
        termReplacements.put("score",
                "To make shallow slits into the food, usually in a rectangular or diamond pattern.");
        termReplacements.put("sear",
                "To cook meat quickly at high heat to seal the surface of the meat and produce a brown color.");
        termReplacements.put("shred", "To cut into long thin strips with a knife or shredder.");
        termReplacements.put("simmer",
                "To cook in liquid that is just below the boiling point. Bubbles will form slowly and burst before reaching the surface.");
        termReplacements.put("sliver", "To cut into long thin pieces with a knife");
        termReplacements.put("steam",
                "To cook in a covered container over boiling water. The container should have small holes in it to allow the steam from the water to rise.");
        termReplacements.put("steep",
                "To let a food stand for a few minutes in just boiled water to increase flavor and color.");
        termReplacements.put("stew", "To simmer slowly in enough liquid to cover.");
        termReplacements.put("stir fry",
                "To cook in a frying pan or wok over high heat in a small amount of fat, stirring constantly.");
        termReplacements.put("sweat",
                "To cook gently, usually in butter, a bit of oil, or the foods own juices to soften but not brown the food.");
        termReplacements.put("toast", "To brown with dry heat in an oven or toaster.");
        termReplacements.put("whip",
                "To beat rapidly with a wire whisk, beater or electric mixer to incorporate air, lighten and increase volume.");
        termReplacements.put("zest",
                "To grate the outer, colored portion of the skin of a citrus fruit, avoiding the white pith. The thin parings that result are also called the zest.");
    }

    // επεξήγηση των περίπλοκων όρων
    public void simplifyTerms(String text) {
        // χωρίζω το κείμενο σε λέξεισ
        text = text.toLowerCase();
        String[] words = text.split("\\s+");
        // τσεκάρω τισ λέξεισ για δύσκολουσ μαγειρικούς όρους
        for (String word : words) {
            if (termReplacements.containsKey(word)) {
                System.out.println (termReplacements.get(word));
            }
        }
        
    }

    public static boolean validateRecipe(Recipe recipe) {
        boolean isValid = true;
        try {

            List<String> cleanedIngredients = new ArrayList<>();
            for (String ingredient : recipe.getIngredients()) {

                // Example: Check if the ingredient is not empty
                if (ingredient.isEmpty()) {
                    isValid = false;
                    System.out.println("Empty ingredient found.");
                } else {
                    cleanedIngredients.add(ingredient);
                }
            }
            recipe.setIngredients(cleanedIngredients.toArray(new String[0]));

            List<String> cleanedSteps = new ArrayList<>();
            for (String step : recipe.getSteps()) {
                // Example: Check if the step is not empty
                if (step.isEmpty()) {
                    isValid = false;
                    System.out.println("Empty step found.");
                } else {
                    cleanedSteps.add(step);
                }
            }
            recipe.setSteps(cleanedSteps.toArray(new String[0]));
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
        if (!allergy.equals("no")){
            boolean found = isWordInText(aiText, allergy);
            if (found) {
            throw new Allergyexception("Allergy found in the recipe");
            }
            if (found) {
            return "Allergy" + allergy + "found in the recipe, give me a new one";
            } else {
                return "no allergy found in the recipe";
            }
        } else {
            return "The user has no allergies";
        }
    }

    public void PreferencesCheck(String aiText, ArrayList<String> ingredients) {
        int counter = 0;
        for (String ingredient : ingredients) {
            boolean found = isWordInText(aiText, ingredient);
            if (found == false) {
                System.out.println("the recipe doesn't contain " + ingredient);
            }
        }
    }

    public String extractRecipeContent(String aiResponse) { // recipe format 1
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode choicesNode = rootNode.get("choices").get(0);
            JsonNode messageNode = choicesNode.get("message");
            String content = messageNode.get("content").asText();
            String processedContent = content.replace("??", "é");
            return processedContent;
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Return empty string in case of any error
        }
    }

    public static Recipe parseRecipe(String recipeString) {// 2
        Recipe recipe = new Recipe();
        // Split recipe into name, ingredients, and steps
        String[] parts = recipeString.split(": ", 2); // Split by the first occurrence of ": "
        recipe.setName(parts[0]); // First part is the name
        
        String[] stepsAndDescription = parts[1].split("\\. "); // Split the remaining part by ". " to get steps and
                                                               // description
        String[] steps = new String[stepsAndDescription.length - 1];
        for (int i = 0; i < stepsAndDescription.length - 1; i++) {
            steps[i] = stepsAndDescription[i] + ".";
        }
        recipe.setSteps(steps); // Steps are everything before the last sentence
        
        // Last sentence is typically the description, but it might vary
        String description = stepsAndDescription[stepsAndDescription.length - 1];
        // Assuming the description is not a step, add it to the steps list
        recipe.setDescription(description);
        
        // Ingredients extraction might vary based on how they are structured in your
        // recipes
        // Here, I'm using a simple approach of splitting by commas and "and"
        String[] ingredients = parts[1].split("\\. ")[0].split(", and |, | and ");
        recipe.setIngredients(ingredients);

        return recipe;
    }

    public Recipe PostProcessingfirst(String aiGeneratedRecipeJson, ArrayList<String> ingredients, String Allergy) {
        try {
            String recipe = extractRecipeContent(aiGeneratedRecipeJson);
            recipe = SpellingAndGrammarCheck(recipe);
            String Allergies = AllergyCheck(recipe, Allergy);
            PreferencesCheck(recipe, ingredients);
            System.out.println(Allergies);
            simplifyTerms(recipe);
            Recipe structuredrecipe = parseRecipe(recipe);
            boolean valid = validateRecipe(structuredrecipe);
            System.out.println("The recipe is " + valid);
            return structuredrecipe;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during post-processing");
            return null;
        }
    }

}

class Allergyexception extends Exception {
    public Allergyexception(String message) {
        super(message);
    }
}

class Recipe {
    private String name;
    private String[] ingredients;
    private String[] steps;
    private String description;

    // Getters and setters
    // ...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
