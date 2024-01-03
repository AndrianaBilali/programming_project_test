package gr.aueb.dmst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ResponseCheckTest {
    ResponseCheck obj = new ResponseCheck();

    @Test
    public void SpellingAndGrammarCheck() {
        String aiAnswer = "This is a text with mitsakes.";
        String expectedCorrection = "This is a text with mistakes.";
        String correctedAnswer = obj.SpellingAndGrammarCheck(aiAnswer);
        assertEquals(expectedCorrection, correctedAnswer);
    }

    @Test
    public void testSimplifyTerms_Mince() {
        obj.createCookingDictionary();
        String output = obj.simplifyTerms("To mince the garlic.");

        String expectedOutput = "Term: mince\nDefinition: To cut or chop food into very small pieces.\n\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void ExtractRecipeContent() {
        String aiResponse = "{\"id\": \"chatcmpl-8YhmKgQfhGah6Z3WZaTqbzPk0i2Cx\",\"object\": \"chat.completion\",\"created\": 1703282128,\"model\": \"gpt-3.5-turbo-0613\",\"choices\": [{\"index\": 0,\"message\": {\"role\": \"assistant\",\"content\": \"Creamy Garlic Parmesan Spaghetti: Cook spaghetti according to package instructions. In a separate pan, melt butter and add minced garlic, saut??ing until fragrant. Stir in flour and gradually whisk in milk until smooth. Add grated Parmesan cheese, salt, and pepper, stirring until cheese is melted and sauce is creamy. Toss cooked spaghetti in the sauce until well coated. Serve hot, garnishing with a sprinkle of freshly chopped parsley and an extra sprinkle of Parmesan cheese. This simple yet indulgent dish is a perfect combination of tender spaghetti and velvety cheese sauce, creating a satisfying and comforting meal.\"},\"logprobs\": null,\"finish_reason\": \"stop\"}],\"usage\": {\"prompt_tokens\": 29,\"completion_tokens\": 126,\"total_tokens\": 155},\"system_fingerprint\": null}";

        String expectedProcessedContent = "Creamy Garlic Parmesan Spaghetti: Cook spaghetti according to package instructions. In a separate pan, melt butter and add minced garlic, sautéing until fragrant. Stir in flour and gradually whisk in milk until smooth. Add grated Parmesan cheese, salt, and pepper, stirring until cheese is melted and sauce is creamy. Toss cooked spaghetti in the sauce until well coated. Serve hot, garnishing with a sprinkle of freshly chopped parsley and an extra sprinkle of Parmesan cheese. This simple yet indulgent dish is a perfect combination of tender spaghetti and velvety cheese sauce, creating a satisfying and comforting meal.";

        String processedContent = obj.extractRecipeContent(aiResponse);

        assertEquals(expectedProcessedContent, processedContent);
    }

    @Test
    public void testAdjustServings() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode ingredients = objectMapper.createArrayNode();

        ObjectNode ingredient1 = objectMapper.createObjectNode();
        ingredient1.put("name", "Flour");
        ingredient1.put("quantity", 200);

        ObjectNode ingredient2 = objectMapper.createObjectNode();
        ingredient2.put("name", "Sugar");
        ingredient2.put("quantity", 100);

        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        int originalServings = 4;
        int desiredServings = 8;

        ArrayNode adjustedIngredients = obj.adjustServings(ingredients, originalServings, desiredServings);

        // Assuming the adjustment is linear, 2x servings should double the quantities
        double expectedFlourQuantity = 400;
        double expectedSugarQuantity = 200;

        assertEquals(expectedFlourQuantity, adjustedIngredients.get(0).get("quantity").asDouble());
        assertEquals(expectedSugarQuantity, adjustedIngredients.get(1).get("quantity").asDouble());
    }

    @Test
    public void testValidateRecipe_EmptyStepAndIngredient() {
        Recipe recipe = new Recipe();
        String[] ingredients = { "Flour", "Sugar", "" };
        String[] steps = { "Mix the flour and sugar.", "" }; // An empty step and ingredient

        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);

        boolean isValid = obj.validateRecipe(recipe);

        assertFalse(isValid); // Both empty step and empty ingredient found
        assertArrayEquals(new String[] { "Flour", "Sugar" }, recipe.getIngredients()); // Empty ingredient removed
        assertArrayEquals(new String[] { "Mix the flour and sugar." }, recipe.getSteps()); // Empty step removed
    }

    @Test
    public void testAllergyCheck_AllergyFound() {
        String aiText = "This recipe contains peanuts and dairy.";
        String allergy = "peanuts";

        Allergyexception exception = assertThrows(Allergyexception.class, () -> {
            obj.AllergyCheck(aiText, allergy);
        });

        assertEquals("Allergy found in the recipe", exception.getMessage());
    }

    @Test
    public void testAllergyCheck_AllergyNotFound() {
        String aiText = "This recipe contains milk, strawberries and ice cream.";
        String allergy = "peanuts";

        assertDoesNotThrow(() -> {
            String result = obj.AllergyCheck(aiText, allergy);
            assertNull(result);
        });
    }

    @Test
    public void testPreferencesCheck_IngredientsFound() {
        String aiText = "This recipe contains tomatoes, onions, and garlic.";
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("tomatoes");
        ingredients.add("onions");

        obj.PreferencesCheck(aiText, ingredients);
        // Ensure no print statements (ingredient found message) are present in the
        // console
    }

    @Test
    public void testPreferencesCheck_IngredientsNotFound() {
        String aiText = "This recipe does not contain tomatoes or onions.";
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("tomatoes");
        ingredients.add("onions");

        obj.PreferencesCheck(aiText, ingredients);
        // Ensure console output contains messages for missing ingredients
    }

    @Test
    public void testParseRecipe() {
        String recipeString = "Creamy Garlic Parmesan Spaghetti: Cook spaghetti according to package instructions. In a separate pan, melt butter and add minced garlic, sautéing until fragrant. Stir in flour and gradually whisk in milk until smooth. Add grated Parmesan cheese, salt, and pepper, stirring until cheese is melted and sauce is creamy. Toss cooked spaghetti in the sauce until well coated. Serve hot, garnishing with a sprinkle of freshly chopped parsley and an extra sprinkle of Parmesan cheese. This simple yet indulgent dish is a perfect combination of tender spaghetti and velvety cheese sauce, creating a satisfying and comforting meal.";

        Recipe recipe = ResponseCheck.parseRecipe(recipeString);

        assertEquals("Creamy Garlic Parmesan Spaghetti", recipe.getName());

        String[] expectedIngredients = {
                "Cook spaghetti according to package instructions.",
                "In a separate pan, melt butter and add minced garlic, sautéing until fragrant.",
                "Stir in flour and gradually whisk in milk until smooth.",
                "Add grated Parmesan cheese, salt, and pepper, stirring until cheese is melted and sauce is creamy.",
                "Toss cooked spaghetti in the sauce until well coated.",
                "Serve hot, garnishing with a sprinkle of freshly chopped parsley and an extra sprinkle of Parmesan cheese."
        };
        assertArrayEquals(expectedIngredients, recipe.getSteps());

        assertEquals(
                "This simple yet indulgent dish is a perfect combination of tender spaghetti and velvety cheese sauce, creating a satisfying and comforting meal.",
                recipe.getDescription());
    }

    @Test
    public void testPostProcessingFirst() {
        String aiGeneratedRecipeJson = "{\"id\": \"chatcmpl-8YhmKgQfhGah6Z3WZaTqbzPk0i2Cx\",\"object\": \"chat.completion\",\"created\": 1703282128,\"model\": \"gpt-3.5-turbo-0613\",\"choices\": [{\"index\": 0,\"message\": {\"role\": \"assistant\",\"content\": \"Creamy Garlic Parmesan Spaghetti: Cook spaghetti according to package instructions. In a separate pan, melt butter and add minced garlic, saut??ing until fragrant. Stir in flour and gradually whisk in milk until smooth. Add grated Parmesan cheese, salt, and pepper, stirring until cheese is melted and sauce is creamy. Toss cooked spaghetti in the sauce until well coated. Serve hot, garnishing with a sprinkle of freshly chopped parsley and an extra sprinkle of Parmesan cheese. This simple yet indulgent dish is a perfect combination of tender spaghetti and velvety cheese sauce, creating a satisfying and comforting meal.\"},\"logprobs\": null,\"finish_reason\": \"stop\"}],\"usage\": {\"prompt_tokens\": 29,\"completion_tokens\": 126,\"total_tokens\": 155},\"system_fingerprint\": null}";

        // Mock ingredients and allergy data if needed
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("tomatoes");
        ingredients.add("onions");
        String allergy = "peanuts";

        // Call the method and validate the result
        Recipe result = obj.PostProcessingfirst(aiGeneratedRecipeJson, ingredients, allergy);

        assertNotNull(result);
        assertEquals("Creamy Garlic Parmesan Spaghetti", result.getName());
        assertNotNull(result.getSteps());
        assertTrue(result.getSteps().length > 0); // Assuming steps are present
        assertNotNull(result.getDescription());
        assertFalse(result.getDescription().isEmpty());

        assertNotNull(result); // Assert that the result is not null
        // Perform additional assertions as needed to validate the result
    }

}