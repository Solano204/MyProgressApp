package com.example.myprogress.app.MongoTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.example.myprogress.app.ConfigurationTest.TestMongoConfig;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Repositories.RecipesRepository;
import com.mongodb.client.result.UpdateResult;


// HERE I TEST THE RECIPE REPOSITORY IN MONGO (Collection: Recipes)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { TestMongoConfig.class })
public class TestRecipeRepository {

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String user = "Carlos";
    private final String recipeName = "Spaghetti Bolognese";
    private final String utensil = "Pot";
    private final String newUtensil = "Pan";
    private final String ingredientName = "Tomato";
    private final String ingredientValue = "2 cups";
    private final String newIngredientName = "Onion";
    private final String newIngredientValue = "3 cups";
    private final String newStep = "Step 2";
    private final String stepNumber = "Step 1";
    private final String stepValue = "Chop the tomatoes";
    private final String newStepValue = "Chop the tomatoes and onions";
    private final String newUserName = "Paco";
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Recipe.class); // Clear the collection before each test
        mongoTemplate.createCollection(Recipe.class);
        recipe = new Recipe();
        recipe.setUser(user);
        recipe.setNameRecipe(recipeName);
        recipe.setUtensils(new ArrayList<>(Collections.singletonList(utensil)));
        recipe.setIngredients(new HashMap<>(Map.of(ingredientName, ingredientValue)));
        recipe.setSteps(new HashMap<>(Map.of(stepNumber, stepValue)));
        recipe.setTimeDuration("30 minutes");
        recipe.setAnyRecomendation("Serve with garlic bread.");
        mongoTemplate.save(recipe);
    }

    @Test
    void testAddNewUtensil() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().addToSet("Utensils").value(newUtensil);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertTrue(updatedRecipe.getUtensils().contains(newUtensil)));
    }

    @Test
    void testAddNewIngredient() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().set("Ingredients." + newIngredientName, newIngredientValue);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertEquals(newIngredientValue, updatedRecipe.getIngredients().get(newIngredientName)));
    }

    @Test
    void testAddOrUpdateStep() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().set("Steps." + newStep, newStepValue);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertEquals(newStepValue, updatedRecipe.getSteps().get(newStep)));
    }

    @Test
    void testDeleteUtensil() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().pull("Utensils", utensil);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertFalse(updatedRecipe.getUtensils().contains(utensil)));
    }

    @Test
    void testDeleteIngredient() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().unset("Ingredients." + ingredientName);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertNull(updatedRecipe.getIngredients().get(ingredientName)));
    }

    @Test
    void testDeleteStep() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().unset("Steps." + stepNumber);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertNull(updatedRecipe.getSteps().get(stepNumber)));
    }

    @Test
    void testUpdateUtensil() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().pull("Utensils", utensil);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Query query2 = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update2 = new Update().addToSet("Utensils").value(newUtensil);
        UpdateResult result2 = mongoTemplate.updateFirst(query2, update2, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query2, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(result2),
                () -> assertEquals(1, result2.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertTrue(updatedRecipe.getUtensils().contains(newUtensil)));
    }

    @Test
    void testUpdateIngredient() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().unset("Ingredients." + ingredientName);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Query query2 = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update2 = new Update().set("Ingredients." + ingredientName, newIngredientValue);
        UpdateResult result2 = mongoTemplate.updateFirst(query2, update2, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query2, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(result2),
                () -> assertEquals(1, result2.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertEquals(newIngredientValue, updatedRecipe.getIngredients().get(ingredientName)));
    }

    @Test
    void testUpdateStep() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().unset("Steps." + stepNumber);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        Query query2 = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update2 = new Update().set("Steps." + stepNumber, newStepValue);
        UpdateResult result2 = mongoTemplate.updateFirst(query2, update2, Recipe.class);

        Recipe updatedRecipe = mongoTemplate.findOne(query2, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> assertNotNull(result2),
                () -> assertEquals(1, result2.getModifiedCount()),
                () -> assertNotNull(updatedRecipe),
                () -> assertEquals(newStepValue, updatedRecipe.getSteps().get(stepNumber)));
    }

    @Test
    void testUpdateUserInRecipes() {
        Query query = new Query(Criteria.where("user").is(user));
        Update update = new Update().set("user", newUserName);
        UpdateResult result = mongoTemplate.updateMulti(query, update, Recipe.class);

        List<Recipe> updatedRecipes = mongoTemplate.find(query, Recipe.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getModifiedCount()),
                () -> updatedRecipes.forEach(recipe -> assertEquals(newUserName, recipe.getUser())));
    }


    @Test
    void testDeleteRecipesByUser() {
        Query query = new Query(Criteria.where("user").is(user));
        mongoTemplate.remove(query, Recipe.class);

        // Validation within assertAll
        assertAll("Delete Recipes By User",
            () -> assertNull(mongoTemplate.findOne(query, Recipe.class), "Recipe should be null after deletion")
        );
    }

    @Test
    void testSaveRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setUser(user);
        newRecipe.setNameRecipe("New Recipe");
        newRecipe.setUtensils(Collections.singletonList("Bowl"));
        newRecipe.setIngredients(Map.of("Flour", "2 cups"));
        newRecipe.setSteps(Map.of("Step 1", "Mix the flour with water"));
        newRecipe.setTimeDuration("15 minutes");
        newRecipe.setAnyRecomendation("Mix well");

        Recipe savedRecipe = recipesRepository.save(newRecipe);

        // Validation within assertAll
        assertAll("Save Recipe",
            () -> assertNotNull(savedRecipe, "Saved recipe should not be null"),
            () -> assertEquals("New Recipe", savedRecipe.getNameRecipe(), "Recipe name should match")
        );
    }

    @Test
    void testGetAllRecipes() {
        List<Recipe> recipes = recipesRepository.findByUser(user);

        // Validation within assertAll
        assertAll("Get All Recipes",
            () -> assertNotNull(recipes, "Recipes list should not be null"),
            () -> assertFalse(recipes.isEmpty(), "Recipes list should not be empty")
        );
    }

    @Test
    void testGetRecipe() {
        Recipe retrievedRecipe = recipesRepository.findByUserAndNameRecipe(user, recipeName);

        // Validation within assertAll
        assertAll("Get Recipe",
            () -> assertNotNull(retrievedRecipe, "Retrieved recipe should not be null"),
            () -> assertEquals(recipeName, retrievedRecipe.getNameRecipe(), "Recipe name should match")
        );
    }

    @Test
    void testDeleteRecipe() {
        recipesRepository.deleteByUserAndNameRecipe(user, recipeName);

        Recipe deletedRecipe = mongoTemplate.findOne(Query.query(Criteria.where("nameRecipe").is(recipeName)), Recipe.class);

        // Validation within assertAll
        assertAll("Delete Recipe",
            () -> assertNull(deletedRecipe, "Deleted recipe should be null")
        );
    }

    @Test
    void testChangeNameRecipe() {
        String newRecipeName = "New Spaghetti Bolognese";
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Recipe originalRecipe = mongoTemplate.findOne(query, Recipe.class);

        if (originalRecipe != null) {
            originalRecipe.setNameRecipe(newRecipeName);
            mongoTemplate.save(originalRecipe);
            Recipe updatedRecipe = mongoTemplate.findOne(Query.query(Criteria.where("nameRecipe").is(newRecipeName)), Recipe.class);

            // Validation within assertAll
            assertAll("Change Name Recipe",
                () -> assertNotNull(updatedRecipe, "Updated recipe should not be null"),
                () -> assertEquals(newRecipeName, updatedRecipe.getNameRecipe(), "Updated recipe name should match")
            );
        }
    }

    @Test
    public void testUpdateAproximateTime() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().set("timeDuration", "new 20 minutes");
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        // Validation within assertAll
        assertAll("Update Approximate Time",
            () -> assertEquals(1, result.getModifiedCount(), "One document should be updated"),
            () -> assertEquals("new 20 minutes", mongoTemplate.findOne(query, Recipe.class).getTimeDuration(), "Time duration should be updated")
        );
    }

    @Test
    public void testUpdateAnyRecommendation() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRecipe").is(recipeName));
        Update update = new Update().set("anyRecomendation", "new anyRecomendation");
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);

        // Validation within assertAll
        assertAll("Update Any Recommendation",
            () -> assertEquals(1, result.getModifiedCount(), "One document should be updated"),
            () -> assertEquals("new anyRecomendation", mongoTemplate.findOne(query, Recipe.class).getAnyRecomendation(), "Recommendation should be updated")
        );
    }

}
