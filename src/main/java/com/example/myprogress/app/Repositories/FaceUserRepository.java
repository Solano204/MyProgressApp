package com.example.myprogress.app.Repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myprogress.app.Entites.faceUser;
import com.example.myprogress.app.HandleProcedures.ProcedureRepository;

@Repository
public interface FaceUserRepository extends CrudRepository<faceUser, String>,ProcedureRepository {

//     // using named query, I call the query throught its name, and I start to follow
//     // a chain to convert
//     @Query(name = "getFaceUserSelected", nativeQuery = true)
//     public faceUser getFaceUser(@Param("personId") String personId);

//     // Recordatorio this has to return the person was created
//     @Query(value = "CALL add_user_facebook (:userId, :userEmail, :typeAuthentication)", nativeQuery = true)
//    public boolean AddUserFacebook(@Param("userId") String userId, 
//                             @Param("userEmail") String userEmail,
//                             @Param("typeAuthentication") String typeAuthentication);

//     // Recordatorio create this procedure
// //     @Query(value = "CALL user_facebook_exist_email(:userEmail)", nativeQuery = true)
// //     public Boolean existEmail(
// //             @Param("userEmail") String email_user);

//     @Query(value = "CALL user_facebook_exist_user(:userId)", nativeQuery = true)
//     public Boolean existUser(
//             @Param("userId") String user);
}
