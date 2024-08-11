package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;


// This is the extra information when the new user was register( Here I including some operations)
@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 

public class infoLogged implements Serializable {



    double currentWeight;
    double lostWeight;
    double gainedWeight;
    int currentCalories;
    int currentProtein;
    int currentCarbohydrates;
    int currentFats;
    LocalDate startingDate; 
    String stateHealth;



}
