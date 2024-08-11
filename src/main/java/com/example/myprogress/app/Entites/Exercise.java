package com.example.myprogress.app.Entites;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@Builder
@Data
public class Exercise implements Serializable {

    @JsonProperty("nameExercise")
    String nameExercise;
    @JsonProperty("repetitions")
    int repetitions;
    @JsonProperty("series")
    int series;
    @JsonProperty("recommendation")
    String recommendation;



    
}