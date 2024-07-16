package com.example.myprogress.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.example.myprogress.app.Entites.googleUser;

public interface GoogleUserRepository extends JpaRepository<googleUser, String> {

    // using named query, I call the query throught its name, and I start to follow
    // a chain to convert
    @Query(name = "getGoogleUserSelected", nativeQuery = true)
    public googleUser getGoogleUser(@Param("personId") Long personId);

    // Recordatorio this has to return the person was created
    @Query(value = "CALL add_user_google (:userId, :userEmail)", nativeQuery = true)
    googleUser getUserByParams(@Param("userId") String userId, 
                            @Param("userEmail") String userEmail);

     // Recordatorio create this procedure
     @Query(value = "CALL user_google_exist_email(:userEmail)", nativeQuery = true)
     Boolean existEmail(
             @Param("userEmail") String email_user);
 
     @Query(value = "CALL user_google_exist_user(:userId)", nativeQuery = true)
     Boolean existUser(
             @Param("userId") String user);
}