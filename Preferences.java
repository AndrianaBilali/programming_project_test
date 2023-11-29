package com.foodapp.food_app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Preferences {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @Column(nullable = true)
    private String allergy;

    // Constructors

    // Default constructor
    public Preferences() {
    }

    // Parameterized constructor
    public Preferences(String allergy) {
        this.allergy = allergy;
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
}
