package com.example.myprogress.app.Entites;

import java.io.Serializable;

import com.example.myprogress.app.validations.RegisterInformation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 

// This is class is the DTO about the information the client sent me when The new user is registered
@Data
@RegisterInformation

public class InfoRegister implements Serializable {
@JsonProperty("name")
    @Schema(description = "Name of the user", 
            example = "Esmeralda", 
            required = true)
    private String name;

    @JsonProperty("age")
    @Schema(description = "Age of the user", 
            example = "30", 
            required = true, 
            minimum = "0")
    private int age;

    @JsonProperty("height")
    @Schema(description = "Height of the user in meters", 
            example = "1.75", 
            required = true)
    private double height;

    @JsonProperty("country")
    @Schema(description = "Country of the user", 
            example = "USA", 
            required = true)
    private String country;

    @JsonProperty("gender")
    @Schema(description = "Gender of the user", 
            example = "Masculino", 
            required = true, 
            allowableValues = {"Femenino", "Masculino", "Prefiero N"})
    private String gender;

    @JsonProperty("levelActivity")
    @Schema(description = "Activity level of the user", 
            example = "Sedentario", 
            required = true, 
            allowableValues = {
                "Sedentario", 
                "Un Poco Activo", 
                "Moderadamente Activo", 
                "Bastante Activo", 
                "Super Activo"})
    private String levelActivity;

    @JsonProperty("valueActivity")
    @Schema(description = "Activity level multiplier for calculating caloric needs", 
            example = "1.55", 
            required = true, 
            allowableValues = {
                "1", 
                "1.2", 
                "1.4", 
                "1.55", 
                "1.8", 
                "2"})
    private double valueActivity;

    @JsonProperty("goal")
    @Schema(description = "User's fitness goal", 
            example = "Mantener Peso", 
            required = true, 
            allowableValues = {
                "Perder Peso", 
                "Ganar Peso", 
                "Mantener Peso"})
    private String goal;

    @JsonProperty("startingWeight")
    @Schema(description = "Starting weight of the user in kilograms", 
            example = "75.0", 
            required = true)
    private double startingWeight;

    @JsonProperty("currentWeight")
    @Schema(description = "Current weight of the user in kilograms", 
            example = "75.0", 
            required = true)
    private double currentWeight;

    @JsonProperty("endWeight")
    @Schema(description = "End weight goal of the user in kilograms", 
            example = "80.0", 
            required = true)
    private double endWeight;
    
    // @Pattern(regexp = "^(Ganar Peso|Perder Peso|Mantener Peso)$", message = "Genero Incorrecto : Debe elegir Gana Peso o Perder Peso")

}
