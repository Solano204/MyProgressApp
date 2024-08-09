package com.example.myprogress.app.FeauturesServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Repositories.RecipesRepository;
import com.mongodb.client.result.UpdateResult;

import lombok.Data;
import lombok.val;

@Data
@Service
public class RecipesService {
    
     
    private final  MongoTemplate mongoTemplate;
    private final RecipesRepository recipesRepository;



/////// SECTIONS RELATED WITH THE EXERCISES OF THE RECIPES ///////

// Here I add a new utensil to a recipe of the user
public boolean addNewUtensil(String nameRecipe, String user, String newUtensil) {
    Query criteria = new Query(Criteria.where("_id").is(user.concat("/" + nameRecipe)));
    Update update = new Update().addToSet("Utensils").value(newUtensil);
    UpdateResult result = mongoTemplate.updateFirst(criteria, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Method to add or update an ingredient in the Ingredients map
public boolean addNewIngredient(String recipeName, String user, String ingredientName, String ingredientValue) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().set("Ingredients." + ingredientName, ingredientValue);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// This method adds or updates a step or instruction in the recipe
public boolean addOrUpdateStep(String recipeName, String user, String numberStep, String stepValue) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().set("Steps." + numberStep, stepValue);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Method to delete a specific utensil from the Utensils list
public boolean deleteUtensil(String nameRecipe, String user, String utensilToDelete) {
    Query criteria = new Query(Criteria.where("_id").is(user.concat("/" + nameRecipe)));
    Update update = new Update().pull("Utensils", utensilToDelete);
    UpdateResult result = mongoTemplate.updateFirst(criteria, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Method to delete an ingredient from the Ingredients map
public boolean deleteIngredient(String recipeName, String user, String ingredientName) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().unset("Ingredients." + ingredientName);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Method to delete a step from the Steps map
public boolean deleteStep(String recipeName, String user, String stepName) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().unset("Steps." + stepName);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Here I update an element in the array of utensils
public boolean updateUtensil(String nameRecipe, String user, String nameUtensil, String newUtensil) {
    return deleteUtensil(nameRecipe, user, nameUtensil) && addNewUtensil(nameRecipe, user, newUtensil);
}

// Method to update an ingredient
public boolean updateIngredient(String nameRecipe, String user, String nameIngredient, String valueIngredient) {
    return deleteIngredient(nameRecipe, user, nameIngredient) && addNewIngredient(nameRecipe, user, nameIngredient, valueIngredient);
}

// Method to update a step
public boolean updateStep(String nameRecipe, String user, String numberStep, String valueStep) {
    return deleteStep(nameRecipe, user, numberStep) && addOrUpdateStep(nameRecipe, user, numberStep, valueStep);
}


/////////////// SECTION RELATED WITH THE USER /////////////////////////

// This method updates all the recipes when the user changes its name to avoid inconsistencies
public void updateUserInRecipes(String oldUserName, String newUserName) {
    Query query = new Query(Criteria.where("user").is(oldUserName));
    Update update = new Update().set("user", newUserName);
    mongoTemplate.updateMulti(query, update, Recipe.class);
}

// This method deletes all the recipes when the user changes its name to avoid inconsistencies
public void deleteRecipesByUser(String userName) {
    Query query = new Query(Criteria.where("user").is(userName));
    mongoTemplate.remove(query, Recipe.class);
}


//////// SECTION RELATED WITH THE RECIPE ///////////////////////

// This method will only be executed when a new user is registered
public Recipe saveRecipe(Recipe recipe) {
    recipe.setNameRecipe(recipe.getUser().concat("/" + recipe.getNameRecipe()));
    return recipesRepository.save(recipe);
}

// This method gets the current recipe by name and user
public Recipe getRecipe(String nameRecipe, String user) {
    return recipesRepository.findById(user.concat("/" + nameRecipe)).orElse(null);
}

// This method retrieves all recipes for a user
public List<Recipe> getAllRecipes(String user) {
    return recipesRepository.findByUser(user);
}

// This method will be used to delete a recipe for a user
public void deleteRecipe(String nameRecipe, String user) {
    try {
        recipesRepository.deleteById(user.concat("/" + nameRecipe));
    } catch (Exception e) {
        throw new FieldIncorrectException("La receta no pudo ser eliminada");
    }
}

// In this method, pass the current data to another entity when the recipe changes its name
public boolean changeNameRecipe(String oldName, String user, String newName) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + oldName)));
    Recipe originalDocument = mongoTemplate.findOne(query, Recipe.class, "Recipes");
    if (originalDocument != null) {
        originalDocument.setNameRecipe(user.concat("/" + newName)); // Here I change the name of the recipe combining the user and new name
        mongoTemplate.save(originalDocument, "Recipes");
        mongoTemplate.remove(query, "Recipes");
        return true;
    }
    return false;
}

// Method to update the AproximateTime field
public boolean updateAproximateTime(String recipeName, String user, String newTimeDuration) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().set("AproximateTime", newTimeDuration);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

// Method to update the anyRecomendation field
public boolean updateAnyRecommendation(String recipeName, String user, String newRecommendation) {
    Query query = new Query(Criteria.where("_id").is(user.concat("/" + recipeName)));
    Update update = new Update().set("anyRecomendation", newRecommendation);
    UpdateResult result = mongoTemplate.updateFirst(query, update, Recipe.class);
    return result.getModifiedCount() > 0;
}

}


