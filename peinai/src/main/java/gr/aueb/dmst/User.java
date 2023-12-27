package gr.aueb.dmst;

// Jakarta Persistence API annotations for defining the mapping between objects and database tables
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Apache Commons Codec for password hashing
import org.apache.commons.codec.digest.DigestUtils;

// Jakarta Persistence API annotations for cascading and joining
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

//used for the calculation of the user's age
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException; // Import for handling date parsing exceptions

/*instances of the class should be treated as JPA entities, and their state
will be persisted to the database.*/
@Entity
public class User {

    @Id // specifies that the userId field is the primary key for the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // is used for databases that support auto-incrementing primary
                                                        // keys
    private Long userId;

    /*
     * These annotations define the mapping of the fields username,
     * password,gender and email to database columns
     */
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String gender;

    // specifies that the birthDate field should be mapped to a SQL DATE column
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate birthDate; // Changed to LocalDate

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preference_id", referencedColumnName = "preferenceId")
    private Preferences preferences;

    // getters and setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

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
        // Verify the password by comparing the hashed version
        return hashPassword(rawPassword).equals(this.password);
    }

    // Protected method to hash the password using Apache Commons Codec DigestUtils
    protected String hashPassword(String password) {
        try {
            // using Apache Commons Codec DigestUtils
            return DigestUtils.sha256Hex(password);
        } catch (Exception e) {
            // Handle hashing errors
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

    // method for calculating user's age
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
            // Handle date parsing errors
            throw new RuntimeException("Error calculating age", e);
        }
    }

    // Getters and setters for Preferences
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    //Exception Handling
    public User(String username, String password, LocalDate birthDate) {
        try {
            validateUsername(username);
            validatePassword(password);
            validateBirthDate(birthDate);

            this.username = username;
            this.password = hashPassword(password);
            this.birthDate = birthDate;

        } catch (IllegalArgumentException e) {
            System.err.println("User creation failed: " + e.getMessage());
        }
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

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid birth date");
        }
    }
}
