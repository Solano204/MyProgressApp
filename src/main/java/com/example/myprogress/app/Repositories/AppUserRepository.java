package com.example.myprogress.app.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.HandleProcedures.ProcedureRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

        @Repository
        public interface AppUserRepository extends CrudRepository<appUser, String>,ProcedureRepository {

        // // using named query, I call the query throught its name, and I start to follow
        // // a chain to convert
        // @Query(name = "getAppUserSelected", nativeQuery = true)
        // public appUser getAppUser(@Param("personId") String personId);

        // // Recordatorio this has to return the person was created
        // @Query(value = "CALL add_user_app (:id_user, :password, :email_user, :typeAuthentication, @in_success)", nativeQuery = true)
        // boolean addUserApp(@Param("id_user") String userId,
        //                 @Param("password") String userPassword,
        //                 @Param("email_user") String userEmail,
        //                 @Param("typeAuthentication") String typeAuthentication);
        // // Recordatorio create this procedure

        // @Query(value = "CALL user_app_exist_user(:user, @in_success)", nativeQuery = true)
        // boolean existUser(
        //                 @Param("user") String user);
}
