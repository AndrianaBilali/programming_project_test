package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
//test for Preferences class
class PreferencesTest {

    @Test
    void testGetPreferenceId() {
        Preferences preferences = new Preferences();

        assertNotNull(preferences.getPreferenceId(), "PreferenceId should not be null");
    }

    @Test
    void testAllergy() {
        Preferences preferences = new Preferences();
        preferences.setAllergy("Peanuts");

        assertEquals("Peanuts", preferences.getAllergy());
    }

    @Test
    void testFavoriteIngredients() {
        Preferences preferences = new Preferences();
        Set<String> favIngredients = new HashSet<>();
        favIngredients.add("Chocolate");
        favIngredients.add("Strawberries");

        preferences.setFavIngredients(favIngredients);

        assertEquals(favIngredients, preferences.getFavIngredients());
       
    }

    @Test
    void testParameterizedConstructor() {
        Set<String> favIngredients = new HashSet<>();
        favIngredients.add("Chocolate");
        favIngredients.add("Strawberries");

        Set<String> worstIngredients = new HashSet<>();
        worstIngredients.add("Onions");
        worstIngredients.add("Mushrooms");

        Preferences preferences = new Preferences("Peanuts", favIngredients, worstIngredients);

        assertEquals("Peanuts", preferences.getAllergy());
    }
}
