package com.example.myprogress.app.Entites;

import java.io.Serializable;

import com.example.myprogress.app.validations.RegisterInformation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

// This is class is the DTO about the information the client sent me when The new user is registered
@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
@RegisterInformation
public class InfoRegister  implements Serializable {

    String name;
    int age;
    double height;
    String country;
    String gender;
    String levelActivity;
    double valueActivity;

  // @Pattern(regexp = "^(Ganar Peso|Perder Peso|Mantener Peso)$", message = "Genero Incorrecto : Debe elegir Gana Peso o Perder Peso")
    String goal;
    double startingWeight;
    double currentWeight;
    
    double endWeight;


}
