package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "Facebook_Users")
public final class faceUser extends User implements Serializable {

    // This is the constructor of the class to do transfer between java and sql
    public faceUser(String user, String email, String typeAuthentication) {
        super(user, email, typeAuthentication);
    }

    // This is the constructor will help the subclasses to can use the class
    // @SqlResultSetMapping,This will be used by the namedQuery
    public faceUser(String user, String email, String typeAuthentication, LocalDate currentStarting, double StartingWeight,
    double CurrentWeight, double EndWeight, int CurrentCalories, double LostWeight, double gainedWeight) {
        super(user, email,typeAuthentication, currentStarting, StartingWeight, CurrentWeight, EndWeight, CurrentCalories, LostWeight, gainedWeight);
    }  

}
