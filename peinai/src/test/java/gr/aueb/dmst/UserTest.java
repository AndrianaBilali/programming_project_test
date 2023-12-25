package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testCalculateAge() {
        // create user object for User class
        User user = new User();
        user.setBirthDate(LocalDate.of(1990, 2, 1));

        assertEquals(33, user.calculateAge());
    }

    @Test
    void testCalculateAgeWithFutureBirthDate() {
        User user = new User();
        user.setBirthDate(LocalDate.now().plusYears(1));
        assertEquals(0, user.calculateAge());
    }

    @Test
    void testVerifyPassword() {
        User user = new User();
        user.setPassword("secret");

        assertFalse(user.verifyPassword("wrongpassword"));
    }

    @Test
    void testHashPassword() {
        User user = new User();
        String hashedPassword = user.hashPassword("password123");
        assertNotNull(hashedPassword);
    }

    @Test
    void testPreferencesAssociation() {
        // Create a new User instance
        User user = new User();

        // Create a new Preferences instance
        Preferences preferences = new Preferences();

        // Set the Preferences for the User
        user.setPreferences(preferences);

        // Verify that the Preferences set for the User is the same as the one retrieved
        assertEquals(preferences, user.getPreferences());
    }

}
