package gr.aueb.dmst;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserService {

    private static final String PERSISTENCE_UNIT_NAME = "peinai";
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static boolean signUp(String username, String password, String email, String gender, LocalDate birthDate,
            String allergy, Set<Ingredient> favIngredients, Set<Ingredient> worstIngredients) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();

            try {
                logger.info("Attempting to sign up user: {}", username);
                System.out.println("Attempting to sign up user: " + username);

                // Check if the user with the given username or email already exists
                if (userExists(username) || userExistsByEmail(email)) {
                    logger.warn("User with username or email already exists.");
                    return false; // User already exists
                }

                // Create user and preferences objects
                User user = new User(username, password, email, gender, birthDate,
                        new Preferences(allergy, favIngredients, worstIngredients));

                // Persist user and preferences to the database
                em.persist(user);
                em.getTransaction().commit();

                logger.info("User signup successful: {}", username);
                return true; // Signup successful
            } catch (PersistenceException e) {
                em.getTransaction().rollback();
                logger.error("Error during signup:", e);
                e.printStackTrace();
                return false; // Signup failed
            }
        }
    }

    public static boolean signIn(String username, String password) {
        try (EntityManager em = factory.createEntityManager()) {
            logger.info("Attempting to sign in user: {}", username);

            // Check if the user with the given username and password exists
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            List<User> results = query.getResultList();

            if (results.size() == 1) {
                User user = results.get(0);
                // Verify the password
                if (user.verifyPassword(password)) {
                    logger.info("User signin successful: {}", username);
                    return true; // Signin successful
                }
            }

            logger.warn("User signin failed: {}", username);
            return false; // Signin failed
        } catch (PersistenceException e) {
            logger.error("Error during signin:", e);
            e.printStackTrace();
            return false; // Signin failed
        }
    }

    private static boolean userExists(String username) {
        try (EntityManager em = factory.createEntityManager()) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username",
                    Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            logger.error("Error during userExists:", e);
            e.printStackTrace();
            return false;
        }
    }

    private static boolean userExistsByEmail(String email) {
        try (EntityManager em = factory.createEntityManager()) {
            final TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email",
                    Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            logger.error("Error during userExistsByEmail:", e);
            e.printStackTrace();
            return false;
        }
    }
}
