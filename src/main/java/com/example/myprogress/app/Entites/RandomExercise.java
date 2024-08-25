package com.example.myprogress.app.Entites;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomExercise {
    @JsonProperty("id")
    @Schema(description = "Unique identifier for the exercise", example = "12345", required = true)
    private String id;

    @JsonProperty("name")
    @Schema(description = "Name of the exercise", example = "Push-Up", required = true)
    private String name;

    @JsonProperty("description")
    @Schema(description = "Description of the exercise", example = "An exercise performed by pushing the body up and down with arms.", required = false)
    private String description;
    
    @JsonProperty("muscleGroups")
    @Schema(description = "Muscle groups targeted by the exercise", 
            example = "[\"pectorales\", \"tríceps\"]", 
            required = true, 
            allowableValues = {
                "cuadriceps", "gluteos", "isquiotibiales", "aductores",
                "bíceps", "trapecios", "tríceps", "abdomen", "oblicuos", 
                "core", "espalda", "Pectorales Inferiores", "Pectorales", 
                "Pectorales Superiores", "Pectorales Internos", 
                "antebrazos", "gemelos", "hombros"})
    private List<String> muscleGroups;
}
