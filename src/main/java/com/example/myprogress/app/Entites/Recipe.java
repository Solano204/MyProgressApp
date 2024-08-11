package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.FieldDefaults;



@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@Document("Recipes")
@Data
public class Recipe implements Serializable {
    @Id
    ObjectId id;



    @Field(name = "nameRecipe")
    String nameRecipe;

    @Field(name = "user")
    String user;
    
    @Field(name = "Utensils")
    List<String> Utensils;

    @Field(name = "Ingredients")
    Map<String, String> Ingredients; // A map is treat as an object in mongo db and in term of json

    @Field(name = "Steps")
    Map<String,String> Steps;

    @Field(name = "AproximateTime")
    String timeDuration;

    @Field(name = "anyRecomendation")
    String anyRecomendation;
}


