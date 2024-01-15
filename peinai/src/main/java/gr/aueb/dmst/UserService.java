package gr.aueb.dmst;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A service class handling user-related operations using Hibernate for data
 * access.
 */
public class UserService {

    private final SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Constructs a UserService with the provided Hibernate SessionFactory.
     *
     * @param sessionFactory The Hibernate SessionFactory used for database
     *                       operations.
     */
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Signs up a new user by persisting the user entity to the database.
     *
     * @param user The user entity to be signed up.
     * @return True if the sign-up is successful, false otherwise.
     */
    public boolean signUp(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Before persisting user: " + user.getUsername());

            // Persist the user entity to the database
            session.persist(user);

            System.out.println("After persisting user: " + user.getUsername());

            transaction.commit();
            return true;
        } catch (HibernateException e) {
            // Handle sign-up errors, print the exception stack trace
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Signs in a user by querying the database for a matching username and hashed
     * password.
     *
     * @param username The username of the user attempting to sign in.
     * @param password The password of the user attempting to sign in.
     * @return The User object if sign-in is successful, null otherwise.
     */
    public User signIn(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User(username, password);

            // Query to retrieve user by username and hashed password
            Query<User> query = session.createQuery(
                    "FROM User WHERE username = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", user.hashPassword(password));

            return query.uniqueResult();
        } catch (HibernateException e) {
            // Handle sign-in errors, log the exception
            logger.error("Error during sign-in", e);
            return null;
        }
    }
}
