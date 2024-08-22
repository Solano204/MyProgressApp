package com.example.myprogress.app.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RoutineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.Data;

@Data
@RestController()

@RequestMapping("/Routine")
@Tag(name = "Routines")
public class RoutineController {
    private final RoutineService routineService;

    @Operation(summary = "Add a new routine", description = "Creates a new routine with the provided details. Returns a success message if the routine was successfully created, or an error message if the creation failed.", tags = {
            "Routines" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The details of the routine to be created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Routine.class)), required = true), responses = {
                    @ApiResponse(responseCode = "201", description = "Routine created successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Routine created successfully"))),
                    @ApiResponse(responseCode = "500", description = "Failed to create the routine", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Failed to create the routine")))
            })
    // In this method add a new Routine
    @PostMapping("/AddNewRoutine")
    public ResponseEntity<?> addNewRoutine(@RequestBody Routine newRoutine) {
        if (routineService.saveRoutine(newRoutine) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Rutina creada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("La rutina no pudo ser creada");
    }

    @Operation(summary = "Delete a routine", description = "Deletes a specified routine for a user. Returns a success message if the routine was successfully deleted.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name= "nameRoutine", example ="New-Routine", description = "The name of the routine to be deleted.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Routine deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Routine deleted successfully"))),
                    @ApiResponse(responseCode = "400", description = "Failed to delete the routine", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Failed to delete the routine")))
            })
    // This method will be used to delete the routine
    @DeleteMapping("/DeleteRoutine")
    public ResponseEntity<?> deleteRoutine(@RequestParam String nameRoutine, @RequestParam String user) {
        routineService.deleteRoutine(nameRoutine, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Rutina fue eliminada exitosamente");
    }

    @Operation(summary = "Change the name of a routine", description = "Changes the name of a specified routine for a user. Returns a success message if the routine name was successfully changed.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name ="oldNameRoutine", example  = "Morning Workout", description = "The current name of the routine to be changed.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "newNameRoutine", example = "New-Routine", description = "The new name for the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name= "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Routine name changed successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Routine name changed successfully"))),
                    @ApiResponse(responseCode = "500", description = "Failed to change the routine name", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Failed to change the routine name")))
            })
    // Method to change the name of the routine
    @PutMapping("/ChangeNameRoutine")
    public ResponseEntity<?> changeNameRoutine(@RequestParam String oldNameRoutine, @RequestParam String newNameRoutine,
            @RequestParam String user) {
        if (routineService.changeNameRoutine(oldNameRoutine, user, newNameRoutine)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("El cambio de nombre a la rutina fue exitoso");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("El cambio de nombre a la rutina no se pudo realizar");
    }

    @Operation(summary = "Retrieve a specific routine", description = "Fetches the details of a specific routine for a user. Returns the routine details if found, otherwise an error message.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name= "nameRoutine", example = "New-Routine", description = "The name of the routine to retrieve.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "Routine retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Routine.class))),
                    @ApiResponse(responseCode = "500", description = "Error retrieving the routine", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error retrieving the routine")))
            })
    // Method to get the routine
    @GetMapping("/GetRoutine")
    public ResponseEntity<?> getRoutine(@RequestParam String nameRoutine, @RequestParam String user) {
        Routine routine = routineService.getRoutine(nameRoutine, user);
        if (routine != null) {
            return ResponseEntity.status(HttpStatus.OK).body(routine);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la rutina");
    }

    @Operation(summary = "Retrieve all routines for a user", description = "Fetches a list of all routines associated with a specific user. Returns the list of routines if found, otherwise a message indicating no routines are available.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name= "user", example = "younowjs2", description = "The username of the user whose routines are to be retrieved.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "200", description = "List of routines retrieved successfully or message indicating no routines", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Routine.class))),
                    @ApiResponse(responseCode = "500", description = "Error retrieving the routines", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error retrieving the routines")))
            })

    @GetMapping("/GetAllRoutines")
    public ResponseEntity<?> getAllRoutines(@RequestParam String user) {
        List<Routine> routines = routineService.getAllRoutines(user);
        if (routines != null && !routines.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(routines);
        }
        return ResponseEntity.status(HttpStatus.OK).body("El usuario no tiene rutinas");
    }

    @Operation(summary = "Add a new exercise to a routine", description = "Adds a new exercise to the specified routine for a given user. Returns a success message if the exercise is added successfully, otherwise an error message.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name= "nameRoutine", example = "New-Routine", description = "The name of the routine to which the exercise will be added.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name= "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The new exercise to be added.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercise.class))), responses = {
                    @ApiResponse(responseCode = "201", description = "Exercise added successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Exercise added successfully"))),
                    @ApiResponse(responseCode = "500", description = "Error adding the exercise", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error adding the exercise")))
            })
    // SECTION RELATED WITH THE EXERCISE
    @PostMapping("/AddNewExercise")
    public ResponseEntity<?> addNewExercise(@Valid @RequestBody Exercise newExercise, @RequestParam String nameRoutine,
            @RequestParam String user) {
        if (routineService.addNewExerciseToRoutine(nameRoutine, user, newExercise)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Ejercicio agregado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El ejercicio no pudo ser agregado");
    }

    @Operation(summary = "Delete an exercise from a routine", description = "Deletes an exercise by name from the specified routine for a given user. Returns a success message if the exercise is deleted successfully, otherwise an error message.", tags = {
            "Routines"}, parameters = {
                    @Parameter(name = "nameExercise", example = "Push-Ups", description = "The name of the exercise to be deleted.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameRoutine" , example = "New-Routine", description = "The name of the routine from which the exercise will be deleted.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user" , example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "202", description = "Exercise deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Exercise deleted successfully"))),
                    @ApiResponse(responseCode = "500", description = "Error deleting the exercise", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error deleting the exercise")))
            })
    @DeleteMapping("/DeleteExercise")
    public ResponseEntity<?> deleteExercise(@RequestParam String nameExercise, @RequestParam String nameRoutine,
            @RequestParam String user) {
        if (routineService.deleteExerciseByName(nameRoutine, user, nameExercise)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Ejercicio eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El ejercicio no pudo ser eliminado");
    }

    @Operation(summary = "Update an exercise in a routine", description = "Updates an existing exercise in the specified routine for a given user. Returns a success message if the exercise is updated successfully, otherwise an error message.", tags = {
            "Routines"}, parameters = {
                    @Parameter(name = "nameRoutine" , example ="Morning Workout", description = "The name of the routine in which the exercise will be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user" , example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "nameExercise", example = "Push-Ups2-New", description = "The name of the exercise to be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated exercise data", required = true, content = @Content(schema = @Schema(implementation = Exercise.class), mediaType = "application/json")), responses = {
                    @ApiResponse(responseCode = "201", description = "Exercise updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Exercise updated successfully"))),
                    @ApiResponse(responseCode = "500", description = "Error updating the exercise", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error updating the exercise")))
            })
    @PutMapping("/UpdateExercise")
    public ResponseEntity<?> updateExercise(@Valid @RequestBody Exercise newExercise, @RequestParam String nameRoutine,
            @RequestParam String user, @RequestParam String nameExercise) {
        if (routineService.updateExercise(nameRoutine, user, nameExercise, newExercise)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Ejercicio fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El ejercicio no pudo ser modificado");
    }

    @Operation(summary = "Update the approximate time of a routine", description = "Updates the approximate time duration of a specified routine for a given user. Returns a success message if the time is updated successfully, otherwise an error message.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name = "nameRoutine", example = "Morning Workout", description = "The name of the routine whose time duration will be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "newTimeDuration", example = "1 a 6 horas", description = "The new approximate time duration to be set for the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Time duration updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Time duration updated successfully"))),
                    @ApiResponse(responseCode = "500", description = "Error updating the time duration", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error updating the time duration")))
            })
    // Method to update the approximate time
    @PutMapping("/UpdateTime")
    public ResponseEntity<?> updateTime(@RequestParam String nameRoutine, @RequestParam String user,
            @RequestParam String newTimeDuration) {
        if (routineService.updateAproximateTime(nameRoutine, user, newTimeDuration)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("El tiempo aproximado fue modificado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El tiempo no pudo ser modificado");
    }

    @Operation(summary = "Update the recommendation of a routine", description = "Updates the recommendation for a specified routine for a given user. Returns a success message if the recommendation is updated successfully, otherwise an error message.", tags = {
            "Routines" }, parameters = {
                    @Parameter(name="nameRoutine", example = "Morning Workout", description = "The name of the routine whose recommendation will be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "user", example = "younowjs2", description = "The username of the user who owns the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
                    @Parameter(name = "newRecomendation", example = "Some Recomendation", description = "The new recommendation to be set for the routine.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            }, responses = {
                    @ApiResponse(responseCode = "201", description = "Recommendation updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Recommendation updated successfully"))),
                    @ApiResponse(responseCode = "500", description = "Error updating the recommendation", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Error updating the recommendation")))
            })
    // Method to update the Recomendation
    @PutMapping("/UpdateRecomendation")
    public ResponseEntity<?> updateRecomendation(@RequestParam String nameRoutine, @RequestParam String user,
            @RequestParam String newRecomendation) {
        if (routineService.updateAnyRecomendation(nameRoutine, user, newRecomendation)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("La recomendación fue modificada exitosamente");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("La recomendación no pudo ser modificada");
    }

}
