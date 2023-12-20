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
import java.util.Date;

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
    private Date birthDate;

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
        // Hash the password using
        this.password = hashPassword(password);
    }

    public boolean verifyPassword(String rawPassword) {
        // Verify the password by comparing the hashed version
        return hashPassword(rawPassword).equals(this.password);
    }

    private String hashPassword(String password) {
        // using Apache Commons Codec DigestUtils
        return DigestUtils.sha256Hex(password);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Calculate age based on birth date using java.time
    public int calculateAge() {
        if (birthDate == null) {
            return 0; // Return 0 if birth date is not set
        }

        LocalDate birthLocalDate = birthDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthLocalDate, currentDate);

        return period.getYears();
    }

    // Getters and setters for Preferences
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
