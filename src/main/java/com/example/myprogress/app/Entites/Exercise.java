package com.example.myprogress.app.Entites;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@Builder
@Data
public class Exercise implements Serializable {

   @JsonProperty("nameExercise")
    @Schema(description = "Name of the exercise", 
            example = "Push-Ups2-New", 
            required = true)
    private String nameExercise;

    @JsonProperty("repetitions")
    @Schema(description = "Number of repetitions for the exercise", 
            example = "15", 
            required = true, 
            minimum = "1")
    private int repetitions;

    @JsonProperty("series")
    @Schema(description = "Number of series or sets for the exercise", 
            example = "3", 
            required = true, 
            minimum = "1")
    private int series;

    @JsonProperty("recommendation")
    @Schema(description = "Additional recommendations or tips for the exercise", 
            example = "Keep your back straight and core engaged")
    private String recommendation;



    
}