package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceTest {

    private static SessionFactory sessionFactory;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeAll
    public static void setup() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        MetadataSources metadataSources = new MetadataSources(standardRegistry);
        sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
    }

    @BeforeEach
    public void beforeEach() {
        userService = new UserService(sessionFactory);
    }

    @Test
    public void testSignUpSuccess() {
        // Create a user for testing
        User testUser = new User("testUser", "testPassword");

        // Perform sign-up
        assertTrue(userService.signUp(testUser));

        // Additional check: Verify that the user is actually persisted
        User retrievedUser = userService.signIn("testUser", "testPassword");
        assertTrue(retrievedUser != null && retrievedUser.getUsername().equals("testUser"));
    }

    @Test
    public void testSignUpFailure() {
        // Create a user for testing
        User testUser = new User("testUser", "testPassword");

        // Save the user to simulate an existing user in the database
        assertTrue(userService.signUp(testUser));

        // Perform sign-up, should fail since the user already exists
        assertFalse(userService.signUp(testUser));
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
