package com.example.myprogress.app.Entites;
import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


// This class will serve to get the calories intake by day
@Document(collection = "caloriesIntake")
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@NoArgsConstructor
public class CaloriesIntake implements Serializable { 

    @Id
    @Hidden
    @Field(name = "_id")
    private String Id;

     @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Schema(description = "Total calorie intake", 
            example = "2000", 
            required = true, 
            minimum = "0")
    private int calorieIntake;

    @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Schema(description = "Total proteins consumed in grams", 
            example = "50", 
            required = true, 
            minimum = "0")
    private int proteinsConsumed;

    @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Schema(description = "Total fats consumed in grams", 
            example = "70", 
            required = true, 
            minimum = "0")
    private int fatsConsumed;

    @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Schema(description = "Total carbohydrates consumed in grams", 
            example = "250", 
            required = true, 
            minimum = "0")
    private int carbohydratesConsumed;

}
