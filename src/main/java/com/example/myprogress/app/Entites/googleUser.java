package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Entity
@Table(name = "Google_Users")
public final class googleUser extends User implements Serializable {

     // This is the constructor of the class to do transfer between java and sql
    public googleUser(String user, String email, String typeAuthentication) {
        super(user, email, typeAuthentication);
    }

    // This is the constructor will help the subclasses to can use the class
    // @SqlResultSetMapping,This will be used by the namedQuery
   public googleUser(String user, String email, String typeAuthentication, LocalDate currentStarting,double StartingWeight,
   double CurrentWeight, double EndWeight, int CurrentCalories, double LostWeight, double gainedWeight) {
        super(user, email,typeAuthentication, currentStarting, StartingWeight, CurrentWeight, EndWeight, CurrentCalories, LostWeight, gainedWeight);
    }  
    



}
