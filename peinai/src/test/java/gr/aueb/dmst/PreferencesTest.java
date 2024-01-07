package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
        Set<Ingredient> favIngredients = new HashSet<>();
        favIngredients.add(new Ingredient("Chocolate"));
        favIngredients.add(new Ingredient("Strawberries"));

        preferences.setFavIngredients(favIngredients);

        assertEquals(favIngredients, preferences.getFavIngredients());
    }

    @Test
    void testParameterizedConstructor() {
        Set<Ingredient> favIngredients = new HashSet<>();
        favIngredients.add(new Ingredient("Chocolate"));
        favIngredients.add(new Ingredient("Strawberries"));

        Set<Ingredient> worstIngredients = new HashSet<>();
        worstIngredients.add(new Ingredient("Onions"));
        worstIngredients.add(new Ingredient("Mushrooms"));

        Preferences preferences = new Preferences("Peanuts", favIngredients, worstIngredients);

        assertEquals("Peanuts", preferences.getAllergy());
    }
}
