package gr.aueb.dmst;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("peinai");
        em = emf.createEntityManager();
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testIngredientPersistence() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Chocolate");

        // Persist the ingredient
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(ingredient);
        transaction.commit();

        // Retrieve the ingredient from the database
        Ingredient retrievedIngredient = em.find(Ingredient.class, ingredient.getId());

        assertNotNull(retrievedIngredient);
        assertEquals("Chocolate", retrievedIngredient.getName());
    }

    // Additional test methods can be added as needed

    private void cleanDatabase() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.createQuery("DELETE FROM Ingredient").executeUpdate();

        transaction.commit();
    }
}
