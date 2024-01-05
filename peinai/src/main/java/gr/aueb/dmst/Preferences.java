package gr.aueb.dmst;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @Column(nullable = true)
    private String allergy;

    @ManyToMany
    @JoinTable(name = "preferences_fav_ingredients", joinColumns = @JoinColumn(name = "preference_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> favIngredients;

    @ManyToMany
    @JoinTable(name = "preferences_worst_ingredients", joinColumns = @JoinColumn(name = "preference_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> worstIngredients;

    // Constructors

    // Default constructor
    public Preferences() {
        this.preferenceId = 0L;
    }

    // Parameterized constructor
    public Preferences(String allergy, Set<Ingredient> favIngredients, Set<Ingredient> worstIngredients) {
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

    public Set<Ingredient> getFavIngredients() {
        return favIngredients;
    }

    public void setFavIngredients(Set<Ingredient> favIngredients) {
        this.favIngredients = favIngredients;
    }

    public Set<Ingredient> getWorstIngredients() {
        return worstIngredients;
    }

    public void setWorstIngredients(Set<Ingredient> worstIngredients) {
        this.worstIngredients = worstIngredients;
    }
}
