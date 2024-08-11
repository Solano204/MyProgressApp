package com.example.myprogress.app.Entites;
import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @Field(name = "_id")
    private String Id;

    @Min (value = 0, message = "The value must be greater than 0")
    private int calorieIntake;
    @Min (value = 0, message = "The value must be greater than 0")
    private int proteinsConsumed;
    @Min (value = 0, message = "The value must be greater than 0")
    private int fatsConsumed;
    @Min (value = 0, message = "The value must be greater than 0")  
    private int carbohydratesConsumed;

}
