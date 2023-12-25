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
}
