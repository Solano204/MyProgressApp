package com.example.myprogress.app.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.FeauturesServices.RecipesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @Operation(summary = "Retrieve All Recipes for a User", description = "Fetch all recipes associated with a specified user. Returns a list of recipes or a message indicating no recipes are found.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "user", description = "Username for which the recipes are to be retrieved.", required = true, example = "younowjs2", schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "List of recipes retrieved successfully or a message indicating no recipes found", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Recipe.class))),
                    @ApiResponse(responseCode = "404", description = "User not found or no recipes available", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
            })
    // Method to get all the recipes
    @GetMapping("/GetAllRecipes")
    public ResponseEntity<?> getAllRecipes(@RequestParam String user) {
        List<Recipe> recipes = recipeService.getAllRecipes(user);
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(recipes);
        }
        return ResponseEntity.status(HttpStatus.OK).body("El usuario no tiene recetas");
    }

    @Operation(summary = "Add a New Recipe", description = "Create a new recipe with the provided details. Returns a success message if the recipe is created successfully, otherwise an error message.", tags = {
            "Recipes" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipe object containing details for the new recipe", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))), responses = {
                    @ApiResponse(responseCode = "201", description = "Recipe created successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Receta creada exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, recipe could not be created", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "La receta no pudo ser creada")))
            })
    // Method to add a new Recipe
    @PostMapping("/AddNewRecipe")
    public ResponseEntity<?> addNewRecipe(@Valid @RequestBody Recipe newRecipe) {
        if (recipeService.saveRecipe(newRecipe) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Receta creada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La receta no pudo ser creada");
    }

    @Operation(summary = "Delete a Recipe", description = "Deletes a recipe based on the provided recipe name and user. Returns a success message if the recipe is deleted successfully.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe to be deleted", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Recipe deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Receta eliminada exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, recipe could not be deleted", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "La receta no pudo ser eliminada")))
            })
    // Method to delete the recipe
    @DeleteMapping("/DeleteRecipe")
    public ResponseEntity<?> deleteRecipe(
            @RequestParam String nameRecipe,
            @RequestParam String user) {
        recipeService.deleteRecipe(nameRecipe, user);
        return ResponseEntity.status(HttpStatus.OK).body("Receta fue eliminada exitosamente");
    }

    @Operation(summary = "Change the name of a recipe", description = "Updates the name of an existing recipe. Returns a success message if the name change is successful, or an error message if the operation fails.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "oldNameRecipe", example =  "Spaghetti Bolognese", description = "The current name of the recipe to be renamed", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "newNameRecipe", example =  "MRecipe", description = "The new name for the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2",  description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Recipe name changed successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El cambio de nombre a la receta fue exitoso"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, unable to change the recipe name", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El cambio de nombre a la receta no se pudo realizar")))
            })
    // Method to change the name of the recipe
    @PutMapping("/ChangeNameRecipe")
    public ResponseEntity<?> changeNameRecipe(
            @RequestParam String oldNameRecipe,
            @RequestParam String newNameRecipe,
            @RequestParam String user) {
        if (recipeService.changeNameRecipe(oldNameRecipe, user, newNameRecipe)) {
            return ResponseEntity.status(HttpStatus.OK).body("El cambio de nombre a la receta fue exitoso");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El cambio de nombre a la receta no se pudo realizar");
    }

    @Operation(summary = "Retrieve a specific recipe", description = "Fetches a recipe by its name for a specific user. Returns the recipe details if found, or a not found message if the recipe does not exist for the user.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe to retrieve", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the recipe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
                    @ApiResponse(responseCode = "404", description = "Recipe not found for the user", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "La receta no fue encontrada para el usuario")))
            })
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

    @Operation(summary = "Add a new utensil to a recipe", description = "Adds a new utensil to the list of utensils for a specified recipe and user. Returns a success message if the utensil was added successfully or an error message if the addition failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name ="newUtensil" , example =  "newUtensil", description = "The new utensil to add to the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="nameRecipe", example = "MRecipe", description = "The name of the recipe to which the utensil will be added", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Utensil added successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Utensilio agregado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to add the utensil", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El utensilio no pudo ser agregado")))
            })

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

    @Operation(summary = "Delete a utensil from a recipe", description = "Deletes a specified utensil from the list of utensils for a given recipe and user. Returns a success message if the utensil was successfully removed or an error message if the removal failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name ="nameUtl" , example =  "spoon", description = "The name of the utensil to be deleted", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="nameRecipe", example = "MRecipe", description = "The name of the recipe from which the utensil will be removed", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Utensil deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Utensilio eliminado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to delete the utensil", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El utensilio no pudo ser eliminado")))
            })
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

    @Operation(summary = "Update a utensil in a recipe", description = "Updates the name of an existing utensil in a specified recipe for a given user. Returns a success message if the utensil was successfully updated, or an error message if the update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name ="newUtl" , example =   "newUtl", description = "The new name for the utensil", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name= "nameRecipe" , example="Spaghetti Bolognese", description = "The name of the recipe in which the utensil will be updated", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name="nameUtl" , example = "pot", description = "The current name of the utensil that will be updated", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Utensil updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El utensilio fue modificado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to update the utensil", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El utensilio no pudo ser modificado")))
            })
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

    @Operation(summary = "Add a new ingredient to a recipe", description = "Adds a new ingredient to the specified recipe for a given user. Returns a success message if the ingredient was successfully added, or an error message if the addition failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name="nameIngr" , example= "Potatoes", description = "The name of the ingredient to be added", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name="valueIngr" , example = "30 grams", description = "The amount or value of the ingredient to be added", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe to which the ingredient will be added", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Ingredient added successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Ingrediente agregado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to add the ingredient", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El ingrediente no pudo ser agregado")))
            })
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

    @Operation(summary = "Delete an ingredient from a recipe", description = "Removes an ingredient from the specified recipe for a given user. Returns a success message if the ingredient was successfully removed, or an error message if the removal failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "nameIngr" , example= "Potatoes", description = "The name of the ingredient to be deleted", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe from which the ingredient will be removed", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example =  "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Ingredient deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Ingrediente eliminado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to delete the ingredient", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El ingrediente no pudo ser eliminado")))
            })
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

    @Operation(summary = "Update an ingredient in a recipe", description = "Modifies the value of an existing ingredient in the specified recipe for a given user. Returns a success message if the ingredient was successfully updated, or an error message if the update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "newValue", example ="30 grams ",  description = "The new value for the ingredient", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name ="nameRecipe",  example = "Spaghetti Bolognese", description = "The name of the recipe where the ingredient will be updated", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameIngr", example = "Onion", description = "The name of the ingredient to be updated", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Ingredient updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Ingrediente modificado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to update the ingredient", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El ingrediente no pudo ser modificado")))
            })
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

    @Operation(summary = "Add a new step to a recipe", description = "Adds a new step or updates an existing step in the specified recipe for a given user. Returns a success message if the step was successfully added or updated, or an error message if the addition or update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "numberStep", example = "Step 4", description = "The step number in the recipe. Used to identify the sequence of steps.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "value", example = "Fried rice", description = "The description of the step to be added or updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe where the step will be added or updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Step added successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Paso agregado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to add or update the step", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El paso no pudo ser agregado o actualizado")))
            })
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

    @Operation(summary = "Delete a step from a recipe", description = "Deletes a specific step from the specified recipe for a given user. Returns a success message if the step was successfully deleted, or an error message if the deletion failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name =  "numberStep", example = "Step 2", description = "The step number to be deleted from the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRecipe", example = "MRecipe", description = "The name of the recipe from which the step will be deleted.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Step deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Paso eliminado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to delete the step", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El paso no pudo ser eliminado")))
            })
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

    @Operation(summary = "Update a step in a recipe", description = "Updates a specific step in the given recipe for a user. Returns a success message if the step was successfully updated, or an error message if the update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "newValue", example = "newValue", description = "The new value for the step to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRecipe", example = "Spaghetti Bolognese", description = "The name of the recipe containing the step to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "numberStep", example = "Step 1", description = "The number of the step to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Step updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El paso fue modificado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to update the step", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El paso no pudo ser modificado")))
            })
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

    @Operation(summary = "Update the approximate time of a recipe", description = "Updates the approximate time required for the given recipe for a user. Returns a success message if the time was successfully updated, or an error message if the update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "nameRecipe", example = "Spaghetti Bolognese", description = "The name of the recipe whose time is to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name= "newTimeDuration" , example = "newTimeDuration", description = "The new approximate time duration to be set for the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Time updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El tiempo aproximado fue modificado exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to update the time", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El tiempo no pudo ser modificado")))
            })
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

    @Operation(summary = "Update the recommendation for a recipe", description = "Updates the recommendation for a specified recipe for a user. Returns a success message if the recommendation was successfully updated, or an error message if the update failed.", tags = {
            "Recipes" }, parameters = {
                    @Parameter(name = "nameRecipe", example = "Spaghetti Bolognese", description = "The name of the recipe whose recommendation is to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name= "newRecomendation" , example = "newRecomendation", description = "The new recommendation to be set for the recipe.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Recommendation updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "La recomendación fue modificada exitosamente"))),
                    @ApiResponse(responseCode = "400", description = "Failed to update the recommendation", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "La recomendación no pudo ser modificada")))
            })
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