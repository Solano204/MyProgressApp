package com.example.myprogress.app.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.FeauturesServices.RecipesService;

import jakarta.validation.Valid;
import lombok.Data;
@Data
@RestController
@RequestMapping("/Recipe")
public class RecipeController {

    private final RecipesService recipeService;

    public RecipeController(RecipesService recipeService) {
        this.recipeService = recipeService;
    }

    // Method to get all the recipes
    @GetMapping("/GetAllRecipes")
    public ResponseEntity<?> getAllRecipes(@RequestParam String user) {
        List<Recipe> recipes = recipeService.getAllRecipes(user); 
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(recipes);
        }
        return ResponseEntity.status(HttpStatus.OK).body("El usuario no tiene recetas");      
    }

    // Method to add a new Recipe
    @PostMapping("/AddNewRecipe")
    public ResponseEntity<?> addNewRecipe(@Valid @RequestBody Recipe newRecipe) {
        if (recipeService.saveRecipe(newRecipe) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Receta creada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La receta no pudo ser creada");
    }

    // Method to delete the recipe
    @DeleteMapping("/DeleteRecipe")
    public ResponseEntity<?> deleteRecipe(
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        recipeService.deleteRecipe(nameRecipe, user);
        return ResponseEntity.status(HttpStatus.OK).body("Receta fue eliminada exitosamente");
    }

    // Method to change the name of the recipe
    @PutMapping("/ChangeNameRecipe")
    public ResponseEntity<?> changeNameRecipe(
        @RequestParam String oldNameRecipe, 
        @RequestParam String newNameRecipe, 
        @RequestParam String user) {
        if (recipeService.changeNameRecipe(oldNameRecipe, user, newNameRecipe)) {
            return ResponseEntity.status(HttpStatus.OK).body("El cambio de nombre a la receta fue exitoso");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cambio de nombre a la receta no se pudo realizar");
    }

    // Method to get the recipe
    @GetMapping("/GetRecipe")
    public ResponseEntity<?> getRecipe(
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        Recipe recipe = recipeService.getRecipe(nameRecipe, user);
        if (recipe != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no tiene recetas");
    }

    // Method to add a new utensil to the list
    @PostMapping("/AddNewUtensil")
    public ResponseEntity<?> addNewUtensil(
        @RequestParam String newUtensil, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.addNewUtensil(nameRecipe, user, newUtensil)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Utensilio agregado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El utensilio no pudo ser agregado");
    }

    // Method to delete a utensil from the list
    @DeleteMapping("/DeleteUtensil")
    public ResponseEntity<?> deleteUtensil(
        @RequestParam String nameUtl, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.deleteUtensil(nameRecipe, user, nameUtl)) {
            return ResponseEntity.status(HttpStatus.OK).body("Utensilio eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El utensilio no pudo ser eliminado");
    }

    // Method to update a utensil in the list
    @PutMapping("/UpdateUtensil")
    public ResponseEntity<?> updateUtensil(
        @RequestParam String newUtl, 
        @RequestParam String nameRecipe, 
        @RequestParam String user, 
        @RequestParam String nameUtl) {
        if (recipeService.updateUtensil(nameRecipe, user, nameUtl, newUtl)) {
            return ResponseEntity.status(HttpStatus.OK).body("El utensilio fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El utensilio no pudo ser modificado");
    }

    // Method to add a new ingredient to the list
    @PostMapping("/AddNewIngredient")
    public ResponseEntity<?> addNewIngredient(
        @RequestParam String nameIngr, 
        @RequestParam String valueIngr, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.addNewIngredient(nameRecipe, user, nameIngr, valueIngr)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Ingrediente agregado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ingrediente no pudo ser agregado");
    }

    // Method to delete an ingredient from the list
    @DeleteMapping("/DeleteIngredient")
    public ResponseEntity<?> deleteIngredient(
        @RequestParam String nameIngr, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.deleteIngredient(nameRecipe, user, nameIngr)) {
            return ResponseEntity.status(HttpStatus.OK).body("Ingrediente eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ingrediente no pudo ser eliminado");
    }

    // Method to update an ingredient in the list
    @PutMapping("/UpdateIngredient")
    public ResponseEntity<?> updateIngredient(
        @RequestParam String newValue, 
        @RequestParam String nameRecipe, 
        @RequestParam String user, 
        @RequestParam String nameIngr) {
        if (recipeService.updateIngredient(nameRecipe, user, nameIngr, newValue)) {
            return ResponseEntity.status(HttpStatus.OK).body("El ingrediente fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ingrediente no pudo ser modificado");
    }

    // Method to add a new step to the recipe
    @PostMapping("/AddNewStep")
    public ResponseEntity<?> addNewStep(
        @RequestParam String numberStep, 
        @RequestParam String value, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.addOrUpdateStep(nameRecipe, user, numberStep, value)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Paso agregado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paso no pudo ser agregado");
    }

    // Method to delete a step from the recipe
    @DeleteMapping("/DeleteStep")
    public ResponseEntity<?> deleteStep(
        @RequestParam String numberStep, 
        @RequestParam String nameRecipe, 
        @RequestParam String user) {
        if (recipeService.deleteStep(nameRecipe, user, numberStep)) {
            return ResponseEntity.status(HttpStatus.OK).body("Paso eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paso no pudo ser eliminado");
    }

    // Method to update a step in the recipe
    @PutMapping("/UpdateStep")
    public ResponseEntity<?> updateStep(
        @RequestParam String newValue, 
        @RequestParam String nameRecipe, 
        @RequestParam String user, 
        @RequestParam String numberStep) {
        if (recipeService.updateStep(nameRecipe, user, numberStep, newValue)) {
            return ResponseEntity.status(HttpStatus.OK).body("El paso fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paso no pudo ser modificado");
    }

    // Method to update the approximate time of the recipe
    @PutMapping("/UpdateTime")
    public ResponseEntity<?> updateTime(
        @RequestParam String nameRecipe, 
        @RequestParam String user,  
        @RequestParam String newTimeDuration) {
        if (recipeService.updateAproximateTime(nameRecipe, user, newTimeDuration)) {
            return ResponseEntity.status(HttpStatus.OK).body("El tiempo aproximado fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tiempo no pudo ser modificado");
    }

    // Method to update the recommendation for the recipe
    @PutMapping("/UpdateRecomendation")
    public ResponseEntity<?> updateRecomendation(
        @RequestParam String nameRecipe, 
        @RequestParam String user,  
        @RequestParam String newRecomendation) {
        if (recipeService.updateAnyRecommendation(nameRecipe, user, newRecomendation)) {
            return ResponseEntity.status(HttpStatus.OK).body("La recomendación fue modificada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La recomendación no pudo ser modificada");
    }
}