package com.example.myprogress.app.Entites;

import jakarta.persistence.*;
@Entity
@NamedNativeQuery(
    name = "getFaceUserSelected", // Name of the namedQuery
    query ="CALL getFaceUser(:personId)",
    resultSetMapping = "faceUserMapping" // Here I specify the name of the convertor of sql result to object java
)

// This is the convertor of sql result to result java 
@SqlResultSetMapping(
    name = "faceUserMapping", 
    classes = @ConstructorResult( // Here I start to map the query to java
            targetClass = appUser.class, // I here Specify the name of the class to cast
            columns = { // The columns have to have the same name and follow the order of the class's constructor
                    @ColumnResult(name = "user", type = String.class),
                    @ColumnResult(name = "email", type = String.class)
            }
            // Recordatorio create the procedure and adapat this names
    )
)

@Table(name = "Facebook_Users")
public class faceUser {

    @Id
    @Column(name = "id_user")
    private String user;
    @Column(name = "email_user")
    private String email;

    public faceUser() {
    }


    public faceUser(String user, String email) {
        this.user = user;
        this.email = email;
    }



    public String getEmail() {
        return email;
    }


    public String getUser() {
        return user;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setUser(String user) {
        this.user = user;
    }

}

