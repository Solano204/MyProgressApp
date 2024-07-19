package com.example.myprogress.app.HandleProcedures;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;


//This class is charge of execute all procedures 
public class ProcedureRepositoryImpl implements ProcedureRepository {


    // It's the core of the transactions in sql server(read, write, delete, update)
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean ExistEmail (String email,String Authentication) {
        String nameProcedure = switch (Authentication) {
            case "AppRegister" -> "user_app_exist_email";
            case "GoogleRegister" -> "user_google_exist_email";
            case "FacebookRegister" -> "user_facebook_exist_email";
            default -> null;
        };
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        query.setParameter("email", email);
        query.execute();
        return (Boolean) query.getOutputParameterValue("in_success");
    }


    @Override
    public boolean ExistUser(String user,String Authentication) {
        String nameProcedure = switch (Authentication) {
            case "AppRegister" -> "user_app_exist_user";
            case "GoogleRegister" -> "user_google_exist_user";
            case "FacebookRegister" -> "user_facebook_exist_user";
            default -> null;
        };

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("user", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        query.setParameter("user", user);

        query.execute();

        return (Boolean) query.getOutputParameterValue("in_success");
    }


    //In this method execute the procedure that create a new user
    @Override
    public boolean addUser(String idUser, String password, String emailUser, String typeAuthentication) {
        String nameProcedure = switch (typeAuthentication) {
            case "AppRegister" -> "add_user_app";     
            case "GoogleRegister" -> "add_user_google";
            case "FacebookRegister" -> "add_user_facebook";
            default -> null;
        };

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("id_user", String.class, ParameterMode.IN);
        if (typeAuthentication.equals("AppRegister")) {
            query.registerStoredProcedureParameter("password", String.class, ParameterMode.IN);
        }
        query.registerStoredProcedureParameter("email_user", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("typeAuthentication", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        
        query.setParameter("id_user", idUser);
        query.setParameter("password", password);
        query.setParameter("email_user", emailUser);
        query.setParameter("typeAuthentication", typeAuthentication);

        boolean j = (Boolean) query.getOutputParameterValue("in_success");
        query.execute();

        return (Boolean) query.getOutputParameterValue("in_success");
    }



    // In this method execute the procedure that get data of a new user
    @Override
    public <T> T getUserSelected(String user,String Authentication) {
        String nameProcedure = switch (Authentication) {
            case "AppRegister" -> "getAppUserSelected";     
            case "GoogleRegister" -> "getGoogleUserSelected";
            case "FacebookRegister" -> "getFaceUserSelected";
            default -> null;
        };


        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("user", String.class, ParameterMode.INOUT);
        if (Authentication.equals("AppRegister")) {
            query.registerStoredProcedureParameter("password", String.class, ParameterMode.OUT);
        }
        
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("typeAuthentication", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("currentStarting", LocalDate.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("StartingWeight", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CurrentWeight", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("EndWeight", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CurrentCalories", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("LostWeight", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("gainedWeight", Integer.class, ParameterMode.OUT);
        query.setParameter("user", user);
        query.execute();

        appUser appUser = new appUser();
        if (Authentication.equals("AppRegister")) {
            appUser.setPassWord((String) query.getOutputParameterValue("password"));
            getUserSelectedInformation(appUser, query);
            return (T) appUser;
        }
        return (T) getUserSelectedInformation(appUser, query);
    }

    User getUserSelectedInformation(User normalUser,StoredProcedureQuery query) {
        normalUser.setUser((String) query.getOutputParameterValue("user"));
        normalUser.setEmail((String) query.getOutputParameterValue("email"));
        normalUser.setTypeAuthentication((String) query.getOutputParameterValue("typeAuthentication"));
        normalUser.setCurrentStarting((LocalDate)query.getOutputParameterValue("currentStarting"));
        normalUser.setStartingWeight((Integer) query.getOutputParameterValue("StartingWeight"));
        normalUser.setCurrentWeight((Integer) query.getOutputParameterValue("CurrentWeight"));
        normalUser.setEndWeight((Integer) query.getOutputParameterValue("EndWeight"));
        normalUser.setCurrentCalories((Integer) query.getOutputParameterValue("CurrentCalories"));
        normalUser.setLostWeight((Integer) query.getOutputParameterValue("LostWeight"));
        normalUser.setGainedWeight((Integer) query.getOutputParameterValue("gainedWeight"));
        return normalUser;
    }

    }