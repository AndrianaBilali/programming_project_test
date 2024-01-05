package gr.aueb.dmst;

import jakarta.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingr_id;

    @Column(nullable = false, unique = true)
    private String name;

    // Default constructor
    public Ingredient() {
    }

    // Parameterized constructor
    public Ingredient(String name) {
        this.name = name;
    }

    // Getter for id
    public Long getId() {
        return ingr_id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
