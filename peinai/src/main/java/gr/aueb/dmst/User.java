package gr.aueb.dmst;

/** Jakarta Persistence API annotations for defining the mapping between objects and database tables */ 
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/** Apache Commons Codec for password hashing */
import org.apache.commons.codec.digest.DigestUtils;

/** Jakarta Persistence API annotations for cascading and joining */
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/** used for the calculation of the user's age */
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

/** instances of the class should be treated as JPA entities and their state
 *  will be persisted to the database
 */
@Entity
public class User {
    /**
     * Creates the User Profile
     * 
     * @return username, password, email, gender and age
     */
    
    @Id /** specifies that the username field is the primary key for the entity */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * These annotations define the mapping of the fields
     * password, gender and email to database columns
     */
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String gender;

    /** specifies that the birthDate field should be mapped to a SQL DATE column */
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preference_id", referencedColumnName = "preferenceId")
    private Preferences preferences;

    /** getters and setters */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public boolean verifyPassword(String rawPassword) {
        /** Verify the password by comparing the hashed version */
        return hashPassword(rawPassword).equals(this.password);
    }

    /** Protected method to hash the password using Apache Commons Codec DigestUtils */
    protected String hashPassword(String password) {
        try {
            return DigestUtils.sha256Hex(password);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    /** method for calculating user's age */
    public int calculateAge() {
        try {
            if (birthDate == null) {
                return 0;
            }

            LocalDate currentDate = LocalDate.now();

            if (birthDate.isAfter(currentDate)) {
                return 0;
            }

            Period period = Period.between(birthDate, currentDate);

            return period.getYears();
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error calculating age", e);
        }
    }

    /** Getters and setters for Preferences */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    /** Exception Handling */
    public User(String username, String password, String email, String gender, LocalDate birthDate,
            Preferences preferences) {
        validateUsername(username);
        validatePassword(password);
        validateEmail(email);
        validateGender(gender);
        validateBirthDate(birthDate);

        this.username = username;
        this.password = hashPassword(password);
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.preferences = preferences;
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    private void validateGender(String gender) {
        if (!"female".equals(gender) && !"male".equals(gender) && !"other".equals(gender)) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid birth date");
        }
    }
}
