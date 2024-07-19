package com.example.myprogress.app.Entites;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@NamedNativeQuery(name = "getAppUserSelected", // Name of the namedQuery
        query = "CALL getAppUser(:personId)", resultSetMapping = "appUserMapping" // Here I specify the name of the
                                                                                  // convertor of sql result to object
                                                                                  // java
)

// This is the convertor of sql result to result java
@SqlResultSetMapping(name = "appUserMapping", classes = @ConstructorResult( // Here I start to map the query to java
        targetClass = appUser.class, // I here Specify the name of the class to cast
        columns = { // The columns have to have the same name and follow the order of the class's
                    // constructor
                @ColumnResult(name = "user", type = String.class),
                @ColumnResult(name = "password", type = String.class),
                @ColumnResult(name = "email", type = String.class),
                @ColumnResult(name = "typeAuthentication", type = String.class),
                @ColumnResult(name = "currentStarting", type = String.class),
                @ColumnResult(name = "StartingWeight", type = String.class),
                @ColumnResult(name = "CurrentWeight", type = String.class),
                @ColumnResult(name = "EndWeight", type = String.class),
                @ColumnResult(name = "CurrentCalories", type = String.class),
                @ColumnResult(name = "LostWeight", type = String.class),
                @ColumnResult(name = "gainedWeight", type = String.class)
        }))
@Table(name = "App_Users")
public final class appUser extends User {
    @Column(name = "Password")
    private String passWord;

    // This contructor will be used in the most of the case to create a new user
    public appUser(String idUser, String password, String email, String typeAuthentication) {
        super(idUser, email, typeAuthentication);
        this.passWord = password;
    }

    // This constructor will be used to map the response of the SqlResultSetMapping

    public appUser(String user, String email, String password,String typeAuthentication, LocalDate currentStarting, int StartingWeight,
         int CurrentWeight, int EndWeight, int CurrentCalories, int LostWeight, int gainedWeight) {
        super(user, email,typeAuthentication, currentStarting, StartingWeight, CurrentWeight, EndWeight, CurrentCalories, LostWeight, gainedWeight);
        this.passWord = password;
    }   

    public appUser() {
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}