package com.foodapp.food_app;

// used to define the mapping between Java objects and database tables
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

//used for the calculation of the user's age
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity // instances of the class should be treated as JPA entities, and their state
        // will be persisted to the database.
public class User {

    @Id // specifies that the userId field is the primary key for the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // is used for databases that support auto-incrementing primary
                                                        // keys
    private Long userId;

    // These annotations define the mapping of the fields username, password, gender and
    // email to database columns
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String gender;

    @Temporal(TemporalType.DATE) // specifies that the birthDate field should be mapped to a SQL DATE column
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, this.password);
    }


    public void setBirthDate(Date birthDate) { //πρεπει να βαλω και getBirthDate?
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
