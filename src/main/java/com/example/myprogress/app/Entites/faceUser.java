package com.example.myprogress.app.Entites;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@NamedNativeQuery(name = "getFaceUserSelected", // Name of the namedQuery
        query = "CALL getFaceUser(:personId)", resultSetMapping = "faceUserMapping" // Here I specify the name of the
                                                                                    // convertor of sql result to object
                                                                                    // java
)

// This is the convertor of sql result to result java
@SqlResultSetMapping(name = "faceUserMapping", classes = @ConstructorResult( // Here I start to map the query to java
        targetClass = appUser.class, // I here Specify the name of the class to cast
        columns = { // The columns have to have the same name and follow the order of the class's
                    // constructor
                @ColumnResult(name = "user", type = String.class),
                @ColumnResult(name = "email", type = String.class),
                @ColumnResult(name = "typeAuthentication", type = String.class),
                @ColumnResult(name = "currentStarting", type = String.class),
                @ColumnResult(name = "StartingWeight", type = String.class),
                @ColumnResult(name = "CurrentWeight", type = String.class),
                @ColumnResult(name = "EndWeight", type = String.class),
                @ColumnResult(name = "CurrentCalories", type = String.class),
                @ColumnResult(name = "LostWeight", type = String.class),
                @ColumnResult(name = "gainedWeight", type = String.class)
        }
// Recordatorio create the procedure and adapat this names
))

@Table(name = "Facebook_Users")
public final class faceUser extends User {

    public faceUser() {
    }

    // This is the constructor of the class to do transfer between java and sql
    public faceUser(String user, String email, String typeAuthentication) {
        super(user, email, typeAuthentication);
    }

    // This is the constructor will help the subclasses to can use the class
    // @SqlResultSetMapping,This will be used by the namedQuery
    public faceUser(String user, String email, String typeAuthentication, LocalDate currentStarting, int StartingWeight,
         int CurrentWeight, int EndWeight, int CurrentCalories, int LostWeight, int gainedWeight) {
        super(user, email,typeAuthentication, currentStarting, StartingWeight, CurrentWeight, EndWeight, CurrentCalories, LostWeight, gainedWeight);
    }  

}
