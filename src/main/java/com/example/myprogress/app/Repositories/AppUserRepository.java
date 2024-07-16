package com.example.myprogress.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;

import com.example.myprogress.app.Entites.appUser;

public interface AppUserRepository extends JpaRepository<appUser, String> {

    // using named query, I call the query throught its name, and I start to follow
    // a chain to convert
    @Query(name = "getAppUserSelected", nativeQuery = true)
    public appUser getAppUser(@Param("personId") Long personId);

    // Recordatorio this has to return the person was created
    @Query(value = "CALL add_user_app (:userId, :userPassword, :userEmail)", nativeQuery = true)
    appUser getUserByParams(@Param("userId") String userId,
            @Param("userPassword") String userPassword,
            @Param("userEmail") String userEmail);


    // Recordatorio create this procedure
    @Query(value = "CALL user_app_exist_email(:userEmail)", nativeQuery = true)
    Boolean existEmail(
            @Param("userEmail") String email_user);

    @Query(value = "CALL user_app_exist_user(:userId)", nativeQuery = true)
    Boolean existUser(
            @Param("userId") String user);
}
