package gr.aueb.dmst;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserService {

    private static final String PERSISTENCE_UNIT_NAME = "peinai";
    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static boolean signUp(String username, String password, String email, String gender, LocalDate birthDate,
            String allergy, Set<String> favIngredients, Set<String> worstIngredients) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        try {
            // Check if the user with the given username or email already exists
            if (userExists(username) || userExistsByEmail(email)) {
                return false; // User already exists
            }

            // Create user and preferences objects
            User user = new User(username, password, email, gender, birthDate,
                    new Preferences(allergy, favIngredients, worstIngredients));
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setGender(gender);
            user.setBirthDate(birthDate);

            Preferences preferences = new Preferences(allergy, favIngredients, worstIngredients);
            user.setPreferences(preferences);

            // Persist user and preferences to the database
            em.persist(user);
            em.getTransaction().commit();
            return true; // Signup successful
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false; // Signup failed
        } finally {
            em.close();
        }
    }

    public static boolean signIn(String username, String password) {
        EntityManager em = factory.createEntityManager();

        try {
            // Check if the user with the given username and password exists
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            List<User> results = query.getResultList();

            if (results.size() == 1) {
                User user = results.get(0);
                // Verify the password
                if (user.verifyPassword(password)) {
                    return true; // Signin successful
                }
            }

            return false; // Signin failed
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Signin failed
        } finally {
            em.close();
        }
    }

    private static boolean userExists(String username) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        Long count = query.getSingleResult();
        em.close();
        return count > 0;
    }

    private static boolean userExistsByEmail(String email) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        Long count = query.getSingleResult();
        em.close();
        return count > 0;
    }
}
