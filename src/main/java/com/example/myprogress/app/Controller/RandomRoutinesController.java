package com.example.myprogress.app.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RandomRoutineService;
import com.example.myprogress.app.validations.RegisterInformation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;

@Data
@RestController
@RequestMapping("/RandomRoutines")

public class RandomRoutinesController {

    private final RandomRoutineService randomRoutineService;

    @Operation(
        summary = "Generate a Workout Routine Based on Muscle and Time Available",
        description = "Retrieve a workout routine based on the specified muscle group and available time. The muscle group and available time parameters are used to generate an appropriate routine.",
        tags = {"Workout Routine"},
        parameters = {
            @Parameter(
                name = "muscle",
                description = "Muscle group for which the routine is to be generated. ",
                required = true,
                example = "bíceps",
                schema = @Schema(
                    type = "string",
                    allowableValues = {
                        "cuadriceps", "gluteos", "isquiotibiales", "aductores",
                        "bíceps", "trapecios", "tríceps", "abdomen", "oblicuos", 
                        "core", "espalda", "Pectorales Inferiores", "Pectorales", 
                        "Pectorales Superiores", "Pectorales Internos", 
                        "antebrazos", "gemelos", "hombros"
                    }
                )
            ),
            @Parameter(
                name = "timeAvailable",
                description = "Time available for the workout routine",
                required = true,
                example = "30 minutos a 1 hora",
                schema = @Schema(
                    type = "string",
                    allowableValues = {
                        "0 a 30 minutos", 
                        "30 minutos a 1 hora", 
                        "1 a 1 hora y 30 minutos", 
                        "1 y 30 minutos a 2 horas", 
                        "2 a 3 horas"
                    }
                )
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Routine successfully generated",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Routine.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Error generating the routine",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(type = "string", example = "Error generating the routine")
                    )
            )
        }
    )
    @GetMapping("/GenerateOneMuscle")
   public ResponseEntity<?> getRecipe(@RequestParam String muscle,@RequestParam String timeAvailable) { 
    Routine routine = randomRoutineService.selectSeriesRepsOnlyMuscle(timeAvailable, muscle.toLowerCase());
    if(routine != null) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routine);
    }  
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al generar la rutina");

}


@Operation(
    summary = "Generate a Workout Routine for Two Muscle Groups Based on Time Available",
    description = "Retrieve a workout routine based on the specified two muscle groups and available time. The routine will include exercises for both muscle groups within the given time.",
    tags = {"Workout Routine"},
    parameters = {
        @Parameter(
            name = "muscle",
            description = "First muscle group for which the routine is to be generated.",
            required = true,
            example = "bíceps",
            schema = @Schema(
                type = "string",
                allowableValues = {
                    "cuadriceps", "gluteos", "isquiotibiales", "aductores",
                    "bíceps", "trapecios", "tríceps", "abdomen", "oblicuos", 
                    "core", "espalda", "Pectorales Inferiores", "Pectorales", 
                    "Pectorales Superiores", "Pectorales Internos", 
                    "antebrazos", "gemelos", "hombros"
                }
            )
        ),
        @Parameter(
            name = "muscle2",
            description = "Second muscle group for which the routine is to be generated.",
            required = true,
            example = "tríceps",
            schema = @Schema(
                type = "string",
                allowableValues = {
                    "cuadriceps", "gluteos", "isquiotibiales", "aductores",
                    "bíceps", "trapecios", "tríceps", "abdomen", "oblicuos", 
                    "core", "espalda", "Pectorales Inferiores", "Pectorales", 
                    "Pectorales Superiores", "Pectorales Internos", 
                    "antebrazos", "gemelos", "hombros"
                }
            )
        ),
        @Parameter(
                name = "timeAvailable",
                description = "Time available for the workout routine",
                required = true,
                example = "30 minutos a 1 hora",
                schema = @Schema(
                    type = "string",
                    allowableValues = {
                        "0 a 30 minutos", 
                        "30 minutos a 1 hora", 
                        "1 a 1 hora y 30 minutos", 
                        "1 y 30 minutos a 2 horas", 
                        "2 a 3 horas"
                    }
                )
            )
    },
    responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Routine successfully generated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Routine.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error generating the routine",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "Error al generar la rutina")
            )
        )
    }
)
    @GetMapping("/GenerateTwoMuscle")
   public ResponseEntity<?> generateTwoMuscle(@RequestParam String muscle,@RequestParam String muscle2,@RequestParam String timeAvailable) { 
        Routine routine = randomRoutineService.selectSeriesRepsTwoMuscle(timeAvailable, muscle.toLowerCase(), muscle2.toLowerCase());
    if(routine != null) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routine);
    }  
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al generar la rutina");

}

}
