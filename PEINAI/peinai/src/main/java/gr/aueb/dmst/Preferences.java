package PEINAI.peinai.src.main.java.gr.aueb.dmst;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
