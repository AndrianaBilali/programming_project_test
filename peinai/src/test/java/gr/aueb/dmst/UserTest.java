package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UserTest {

    // Test for calculating age based on the birth date
    @Test
    void testCalculateAge() {
        // Create a user object for the User class with a known birth date
        User user = new User("George02", "p34wqR71a!", "george@example.com", "male", LocalDate.of(1990, 2, 1),
                new Preferences());
        user.setBirthDate(LocalDate.of(1990, 2, 1));

        // Get the calculated age
        int calculatedAge = user.calculateAge();

        // Verify that the calculateAge method returns the expected age
        assertTrue(calculatedAge == 33, "Calculate age should return the correct age. Actual age: " + calculatedAge);
    }

    // Test for calculating age when the birth date is in the future
    @Test
    void testCalculateAgeWithFutureBirthDate() {
        // Create a user object for the User class with a future birth date
        User user = new User("George02", "p34wqR71a!", "george@example.com", "male", LocalDate.of(1990, 2, 1),
                new Preferences());
        user.setBirthDate(LocalDate.now().plusYears(1));

        // Verify that the calculateAge method returns 0 for a future birth date
        assertEquals(0, user.calculateAge());
    }

    // Test for verifying user password
    @Test
    void testVerifyPassword() {
        // Create a user object for the User class with a known password
        User user = new User("George02", "p34wqR71a!", "george@example.com", "male", LocalDate.of(1990, 2, 1),
                new Preferences());
        user.setPassword("secret");

        // Verify that the verifyPassword method correctly checks passwords
        assertFalse(user.verifyPassword("wrongpassword"));
    }

    // Test for hashing a user password
    @Test
    void testHashPassword() {
        // Create a user object for the User class with a known birth date
        User user = new User("George02", "p34wqR71a!", "george@example.com", "male", LocalDate.of(1990, 2, 1),
                new Preferences());

        // Verify that the hashPassword method returns a non-null hashed password
        assertNotNull(user.hashPassword("p34wqR71a!"));
    }

    // Test for establishing an association between User and Preferences
    @Test
    void testPreferencesAssociation() {
        // Create a new User instance
        User user = new User("George02", "p34wqR71a!", "george@example.com", "male", LocalDate.of(1990, 2, 1),
                new Preferences());

        // Create a new Preferences instance
        Preferences preferences = new Preferences();

        // Set the Preferences for the User
        user.setPreferences(preferences);

        // Verify that the Preferences set for the User is the same as the one retrieved
        assertEquals(preferences, user.getPreferences());
    }

    // Test for creating a valid user with proper data
    @Test
    void testCreateValidUser() {
        LocalDate validBirthDate = LocalDate.of(1990, 1, 1);

        // Create a user object for the User class with valid data
        User user = new User("validUsername", "validPassword123", "validEmail", "male", validBirthDate,
                new Preferences());

        // Verify that the created user has the expected properties
        assertEquals("validUsername", user.getUsername());
        assertTrue(user.verifyPassword("validPassword123"));
        assertEquals(validBirthDate, user.getBirthDate());
    }

    // Test for creating a user with invalid data, expecting an exception
    @Test
    void testCreateUserWithInvalidData() {
        // Verify that creating a user with an empty username triggers an
        // IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new User("", "validPassword123", "validEmail", "male",
                LocalDate.of(1990, 1, 1), new Preferences()));
    }

    // Test for creating a user with invalid gender, expecting an exception
    @Test
    void testCreateUserWithInvalidGender() {
        // Verify that creating a user with an invalid gender triggers an
        // IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> new User("invalidUser", "validPassword123", "validEmail", "invalidGender",
                        LocalDate.of(1990, 1, 1),
                        new Preferences()));
    }
}
