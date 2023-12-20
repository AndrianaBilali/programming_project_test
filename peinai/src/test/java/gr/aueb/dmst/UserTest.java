package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testCalculateAge() {
        //create user object for User class
        User user = new User();
        // Ορίζουμε την ημερομηνία γέννησης για τον έλεγχο
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        assertEquals(33, user.calculateAge());
        //
    }

@Test
    void testVerifyPassword() {
        User user = new User();
        user.setPassword("secret");

        assertFalse(user.verifyPassword("wrongpassword"));
    }
}