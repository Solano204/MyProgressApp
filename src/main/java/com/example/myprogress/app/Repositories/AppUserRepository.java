package com.example.myprogress.app.Repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.HandleProcedures.ProcedureRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public interface AppUserRepository extends CrudRepository<appUser, String>, ProcedureRepository {

        // // using named query, I call the query throught its name, and I start to
        // follow
        // // a chain to convert
        // @Query(name = "getAppUserSelected", nativeQuery = true)
        // public appUser getAppUser(@Param("personId") String personId);

        // // Recordatorio this has to return the person was created
        // @Query(value = "CALL add_user_app (:id_user, :password, :email_user,
        // :typeAuthentication, @in_success)", nativeQuery = true)
        // boolean addUserApp(@Param("id_user") String userId,
        // @Param("password") String userPassword,
        // @Param("email_user") String userEmail,
        // @Param("typeAuthentication") String typeAuthentication);
        // // Recordatorio create this procedure

        // In this query I validate if the password is correct
        @Query(value = "SELECT CASE WHEN COUNT(u.Password) = 1 THEN true ELSE false END FROM app_users u WHERE u.id_User = :user and u.Password = :password ", nativeQuery = true)
        Integer validatePassword(@Param("user") String user, @Param("password") String password);

        @Transactional
        @Modifying
        @Query(value = "UPDATE app_users SET Password = :newPassword WHERE id_User = :User ", nativeQuery = true)
        int updatePassword(@Param("newPassword") String newPassword,
                        @Param("User") String User);

                        
        @Query("SELECT u FROM appUser u WHERE u.user = :id")
        Optional<appUser> findByIdUser(@Param("id") String id);
}
