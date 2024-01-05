package gr.aueb.dmst;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Set;

public class UserService {

    private static final String PERSISTENCE_UNIT_NAME = "peinai";
    private final EntityManagerFactory factory;

    public UserService(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public boolean signUp(String username, String password, String email, String gender, LocalDate birthDate,
            String allergy, Set<Ingredient> favIngredients, Set<Ingredient> worstIngredients) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();

            try {
                // Check if the user with the given username or email already exists
                if (userExists(username) || userExistsByEmail(email)) {
                    System.out.println("User already exists.");
                    return false; // User already exists
                } else {
                    // Create user and preferences objects
                    Preferences preferences = new Preferences(allergy, favIngredients, worstIngredients);
                    User user = new User(username, password, email, gender, birthDate, preferences);

                    // Persist user and preferences to the database
                    System.out.println("Persisting user and preferences to the database...");
                    em.persist(preferences);
                    em.persist(user);
                    em.getTransaction().commit();
                    System.out.println("Signup successful.");
                    return true; // Signup successful
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                System.err.println("Signup failed. Error: " + e.getMessage());
                return false; // Signup failed
            }
        }
    }

    public boolean signIn(String username, String password) {
        try (EntityManager em = factory.createEntityManager()) {
            // Check if the user with the given username and password exists
            User user = em.find(User.class, username);

            if (user != null && user.verifyPassword(password)) {
                return true; // Signin successful
            }

            return false; // Signin failed
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Signin failed
        }
    }

    private boolean userExists(String username) {
        try (EntityManager em = factory.createEntityManager()) {
            return em.find(User.class, username) != null;
        }
    }

    private boolean userExistsByEmail(String email) {
        try (EntityManager em = factory.createEntityManager()) {
            return em.find(User.class, email) != null;
        }
    }
}
