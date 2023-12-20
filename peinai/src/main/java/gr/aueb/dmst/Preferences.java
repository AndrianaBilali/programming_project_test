package gr.aueb.dmst;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @Column(nullable = true)
    private String allergy;

    @ManyToMany
    private Set<String> favIngredients;

    @ManyToMany
    private Set<String> worstIngredients;

    // Constructors

    // Default constructor
    public Preferences() {
        this.preferenceId = 0L;
    }

    // Parameterized constructor
    public Preferences(String allergy, Set<String> favIngredients, Set<String> worstIngredients) {
        this.allergy = allergy;
        this.favIngredients = favIngredients;
        this.worstIngredients = worstIngredients;
    }

    // Getters and Setters
    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getAllergy() {
        return allergy;
    }

    public Set<String> getFavIngredients() {
        return favIngredients;
    }

    public void setFavIngredients(Set<String> favIngredients) {
        this.favIngredients = favIngredients;
    }

    public Set<String> getWorstIngredients() {
        return worstIngredients;
    }

    public void setWorstIngredients(Set<String> worstIngredients) {
        this.worstIngredients = worstIngredients;
    }
}
