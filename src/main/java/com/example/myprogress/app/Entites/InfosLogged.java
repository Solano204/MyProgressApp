package com.example.myprogress.app.Entites;

    

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

// This is the extra information when the new user was register( Here I including some operations)
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
public class InfosLogged {

    @JsonProperty("currentWeight")
    @Schema(description = "Current weight of the user in kilograms", 
            example = "75.0", 
            required = true)
    private double currentWeight;

    @JsonProperty("lostWeight")
    @Schema(description = "Weight lost by the user in kilograms", 
            example = "0.0")
    private double lostWeight;

    @JsonProperty("gainedWeight")
    @Schema(description = "Weight gained by the user in kilograms", 
            example = "0.0")
    private double gainedWeight;

    @JsonProperty("currentCalories")
    @Schema(description = "Current calorie intake", 
            example = "3397", 
            required = true)
    private int currentCalories;

    @JsonProperty("currentProtein")
    @Schema(description = "Current protein intake in grams", 
            example = "169", 
            required = true)
    private int currentProtein;

    @JsonProperty("currentCarbohydrates")
    @Schema(description = "Current carbohydrate intake in grams", 
            example = "467", 
            required = true)
    private int currentCarbohydrates;

    @JsonProperty("currentFats")
    @Schema(description = "Current fat intake in grams", 
            example = "94", 
            required = true)
    private int currentFats;

    @JsonProperty("startingDate")
    @Schema(description = "Date when the tracking started", 
            example = "2024-08-20", 
            required = true)
    private LocalDate startingDate;

    @JsonProperty("stateHealth")
    @Schema(description = "Health status of the user", 
            example = "Pasado de peso", 
            allowableValues = {"Bajo de peso", "Peso Normal", "Pasado de peso"}, 
            required = true)
    private String stateHealth;



}
