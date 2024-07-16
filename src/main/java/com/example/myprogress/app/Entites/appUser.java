package com.example.myprogress.app.Entites;

import jakarta.persistence.*;

@Entity
@NamedNativeQuery(
    name = "getAppUserSelected", // Name of the namedQuery
    query ="CALL getAppUser(:personId)",
    resultSetMapping = "appUserMapping" // Here I specify the name of the convertor of sql result to object java
)

// This is the convertor of sql result to result java 
@SqlResultSetMapping(
    name = "appUserMapping", 
    classes = @ConstructorResult( // Here I start to map the query to java
            targetClass = appUser.class, // I here Specify the name of the class to cast
            columns = { // The columns have to have the same name and follow the order of the class's constructor
                    @ColumnResult(name = "user", type = String.class),
                    @ColumnResult(name = "password", type = String.class),
                    @ColumnResult(name = "email", type = String.class)
            }
            // Recordatorio create the procedure and adapat this names
    )
)
    @Table(name = "App_Users")
    public class appUser {
        @Id
        @Column(name = "id_user")
        private String user;
        @Column(name = "Password")
        private String passWord;
        @Column(name = "email_user")
        private String email;

        public appUser() {
        }

        public appUser(String user, String passWord, String email) {
            this.user = user;
            this.passWord = passWord;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getPassWord() {
            return passWord;
        }

        public String getUser() {
            return user;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public void setUser(String user) {
            this.user = user;
        }

    }
