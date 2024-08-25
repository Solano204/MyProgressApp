package com.example.myprogress.app.Entites;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@Document("Routine")
@Data
public class Routine {

        
    @Id
    @Hidden
    ObjectId id;

    @Field(name = "nameRoutine")
    @Schema(description = "Name of the routine", example = "Morning Workout", required = true)
    String nameRoutine;

    @Field(name = "user")
    @JsonProperty("user")
    @Schema(description = "Username associated with the routine", example = "younowjs2", required = true)
    String user;

    @JsonProperty("timeDuration")
    @Field(name = "AproximateTime")
    @Schema(description = "Approximate duration of the routine", 
            example = "30 minutos a 1 hora", 
            allowableValues = {"0 a 30 minutos", "30 minutos a 1 hora", "1 a 1 hora y 30 minutos", "1 y 30 minutos a 2 horas", "2 a 3 horas"}, 
            required = true)
    String timeDuration;

    @Field(name = "anyRecomendation")
    @JsonProperty("anyRecomendation")
    @Schema(description = "Any additional recommendation for the routine", 
            example = "Stay hydrated during the workout")
    String anyRecomendation;
    
    @Field(name = "listExercises")
    @JsonProperty("listExercises")
    List<Exercise> listExercises;


}
