package com.example.myprogress.app.Entites;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@Document("Routines")
@Data
public class    Routine {

    @Id
    @Field(name = "_id")
    @JsonProperty("nameRoutine")
    String nameRoutine;

    @Field(name = "user")
    @JsonProperty("user")
    String user;
    
    //This name will make up by the user and the name of the routine to avoid repetitions

    
    @Field(name = "listExercises")
    @JsonProperty("listExercises")
    List<Exercise> listExercises;

    @JsonProperty("timeDuration")
    @Field(name = "AproximateTime")
    String timeDuration;

    @Field(name = "anyRecomendation")
    @JsonProperty("anyRecomendation")

    String anyRecomendation;


}
