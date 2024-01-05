package gr.aueb.dmst;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("peinai");
        em = emf.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testSignUpAndSignIn() {
        // Test signup
        boolean signUpResult = UserService.signUp("testuser", "testpassword", "testuser@example.com",
                "male", LocalDate.of(1990, 1, 1), "none", new HashSet<>(), new HashSet<>());

        assertTrue(signUpResult);

        // Test signin
        boolean signInResult = UserService.signIn("testuser", "testpassword");
        assertTrue(signInResult);
    }

    @Test
    void testDuplicateSignUp() {
        // Attempt to signup with the same username and email
        boolean signUpResult1 = UserService.signUp("duplicateuser", "password1", "duplicate@example.com",
                "female", LocalDate.of(1985, 5, 10), "peanuts", new HashSet<>(), new HashSet<>());
        assertTrue(signUpResult1);

        // Attempt to signup with the same username and email again
        boolean signUpResult2 = UserService.signUp("duplicateuser", "password2", "duplicate@example.com",
                "male", LocalDate.of(1995, 8, 20), "eggs", new HashSet<>(), new HashSet<>());
        assertFalse(signUpResult2);
    }

    @Test
    void testInvalidSignUp() {
        // Attempt to signup with invalid data
        boolean signUpResult = UserService.signUp(".", "short", "invalid@example.com",
                "unknownGender", LocalDate.of(2000, 1, 1), "allergies", new HashSet<>(), new HashSet<>());
        assertFalse(signUpResult);
    }

    @Test
    void testSignInWithInvalidCredentials() {
        // Test signin with invalid credentials
        boolean signInResult = UserService.signIn("nonexistentuser", "wrongpassword");
        assertFalse(signInResult);
    }
}
