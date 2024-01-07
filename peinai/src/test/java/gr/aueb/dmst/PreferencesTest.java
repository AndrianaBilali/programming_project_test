package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PreferencesTest {

    @Test
    void testGetPreferenceId() {
        // Creating a Preferences object
        Preferences preferences = new Preferences();

        // Verifying that PreferenceId is not null
        assertNotNull(preferences.getPreferenceId(), "PreferenceId should not be null");
    }

    @Test
    void testAllergy() {
        // Creating a Preferences object
        Preferences preferences = new Preferences();

        // Setting the allergy
        preferences.setAllergy("Peanuts");

        // Verifying that the allergy is as expected
        assertEquals("Peanuts", preferences.getAllergy());
    }

    @Test
    void testFavoriteIngredients() {
        // Creating a Preferences object
        Preferences preferences = new Preferences();

        // Creating a set of favorite ingredients
        Set<String> favIngredients = new HashSet<>();
        favIngredients.add("Chocolate");
        favIngredients.add("Strawberries");

        // Setting the favorite ingredients
        preferences.setFavIngredients(favIngredients);

        // Verifying that the favorite ingredients are as expected
        assertEquals(favIngredients, preferences.getFavIngredients());
    }

    @Test
    void testWorstIngredients() {
        // Creating a Preferences object
        Preferences preferences = new Preferences();

        // Creating a set of worst ingredients
        Set<String> worstIngredients = new HashSet<>();
        worstIngredients.add("Onions");
        worstIngredients.add("Mushrooms");

        // Setting the worst ingredients
        preferences.setWorstIngredients(worstIngredients);

        // Verifying that the worst ingredients are as expected
        assertEquals(worstIngredients, preferences.getWorstIngredients());
    }

    @Test
    void testParameterizedConstructor() {
        // Creating a set of favorite ingredients
        Set<String> favIngredients = new HashSet<>();
        favIngredients.add("Chocolate");
        favIngredients.add("Strawberries");

        // Creating a set of worst ingredients
        Set<String> worstIngredients = new HashSet<>();
        worstIngredients.add("Onions");
        worstIngredients.add("Mushrooms");

        // Creating a Preferences object using the parameterized constructor
        Preferences preferences = new Preferences("Peanuts", favIngredients, worstIngredients);

        // Verifying that the fields are set correctly
        assertEquals("Peanuts", preferences.getAllergy());
        assertEquals(favIngredients, preferences.getFavIngredients());
        assertEquals(worstIngredients, preferences.getWorstIngredients());
    }
}
