package com.example.myprogress.app.HandleProcedures;

import java.sql.Date;
import java.time.LocalDate;

import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.RegisteredSynchronization;
import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;

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
    public boolean ExistEmail(String email, String Authentication) {
        String nameProcedure = switch (Authentication) {
            case "App" -> "user_app_exist_email";
            case "Google" -> "user_google_exist_email";
            case "Facebook" -> "user_facebook_exist_email";
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
    public boolean ExistUser(String user) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("user_database_exist_user");
        query.registerStoredProcedureParameter("user", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        query.setParameter("user", user);

        query.execute();

        return (Boolean) query.getOutputParameterValue("in_success");
    }

    // In this method execute the procedure that create a new user
    @Override
    public boolean addUser(String idUser, String password, String emailUser, String typeAuthentication) {
        String nameProcedure = switch (typeAuthentication) {
            case "App" -> "add_user_app";
            case "Google" -> "add_user_google";
            case "Facebook" -> "add_user_facebook";
            default -> null;
        };

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("id_user", String.class, ParameterMode.IN);
        if (typeAuthentication.equals("App")) {
            query.registerStoredProcedureParameter("password", String.class, ParameterMode.IN);
        }
        query.registerStoredProcedureParameter("email_user", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("typeAuthentication", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        query.setParameter("id_user", idUser);
        if (typeAuthentication.equals("App")) {
            query.setParameter("password", password);
        }
        query.setParameter("email_user", emailUser);
        query.setParameter("typeAuthentication", typeAuthentication);

        try {
            query.execute();
        } catch (Exception e) {
            return false;
        }

        Boolean success = (Boolean) query.getOutputParameterValue("in_success");
        return (Boolean) query.getOutputParameterValue("in_success");
    }

    // This method to register all data about the progress of the user
    public boolean addProgressUser(User user) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("fill_in_information");
        // Register parameters
        query.registerStoredProcedureParameter("User", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NameUserS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CountryS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("HeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("AgeS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartingWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CurrentWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("LostWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("GainedWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CurrentCalories", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ProteinS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CarbohydratesS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FatS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Goal", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("GenderS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("State_healthS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("level_activity", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);

        // Set parameter values
        query.setParameter("User", user.getUser());
        // This object contain the information to saved
        InfoRegister registerInfo = user.getRegisterInformation();
        infoLogged loggedInfo = user.getInfoLogged();
        query.setParameter("NameUserS", registerInfo.getName());
        query.setParameter("CountryS", registerInfo.getCountry());
        query.setParameter("HeightS", registerInfo.getHeight());
        query.setParameter("AgeS", registerInfo.getAge());
        query.setParameter("StartingWeightS", registerInfo.getStartingWeight());
        query.setParameter("CurrentWeightS", registerInfo.getCurrentWeight());
        query.setParameter("EndWeightS", registerInfo.getEndWeight());
        query.setParameter("LostWeightS", loggedInfo.getLostWeight());
        query.setParameter("GainedWeightS", loggedInfo.getGainedWeight());
        query.setParameter("CurrentCalories", loggedInfo.getCurrentCalories());
        query.setParameter("ProteinS", loggedInfo.getCurrentProtein());
        query.setParameter("CarbohydratesS", loggedInfo.getCurrentCarbohydrates());
        query.setParameter("FatS", loggedInfo.getCurrentFats());
        query.setParameter("Goal", registerInfo.getGoal());
        query.setParameter("GenderS", registerInfo.getGender());
        query.setParameter("State_healthS", loggedInfo.getStateHealth());
        query.setParameter("level_activity", registerInfo.getLevelActivity());
        // Execute stored procedure
        query.execute();
        // Get output parameter value
        Boolean success = (Boolean) query.getOutputParameterValue("in_success");
        return success != null && success;
    }

    // In this method execute the procedure that get data of a new user
    @Override
    public <T> T getUserSelected(String user, String Authentication) {

        String nameProcedure = switch (Authentication) {
            case "App" -> "getAppUserSelected";
            case "Google" -> "getGoogleUserSelected";
            case "Facebook" -> "getFaceUserSelected";
            default -> null;
        };

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        query.registerStoredProcedureParameter("UserS", String.class, ParameterMode.INOUT);
        if (Authentication.equals("App")) {
            query.registerStoredProcedureParameter("passwordS", String.class, ParameterMode.OUT);
        }
        query.registerStoredProcedureParameter("EmailS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("TypeAuthenticationS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("NameUserS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CountryS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("heightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("AgeS", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("DateStartingS", LocalDate.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("StartingWeightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CurrentWeightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("EndWeightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("LostWeightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("GainedWeightS", Double.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CurrentCaloriesS", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("ProteinS", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("CarbohydratesS", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("FatS", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("GoalS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("GenderS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("State_healthS", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("TypeLevelS", String.class, ParameterMode.OUT);
        System.out.println(user);
        query.setParameter("UserS", user);
        boolean hj = query.execute();
        appUser appUser = new appUser();
        appUser.setInfoLogged(new infoLogged());
        appUser.setRegisterInformation(new InfoRegister());
        if (Authentication.equals("App")) {
            appUser.setPassWord((String) query.getOutputParameterValue("passwordS"));
            getUserSelectedInformation(appUser, query);
            return (T) appUser;
        }
        return (T) getUserSelectedInformation(appUser, query);
    }

    // In this method execute the procedure that get data of a new user
    User getUserSelectedInformation(User normalUser, StoredProcedureQuery query) {
        // This object contain the information to saved
        InfoRegister registerInfo = normalUser.getRegisterInformation();
        infoLogged loggedInfo = normalUser.getInfoLogged();

        normalUser.setUser((String) query.getOutputParameterValue("UserS"));
        normalUser.setEmail((String) query.getOutputParameterValue("EmailS"));
        normalUser.setTypeAuthentication((String) query.getOutputParameterValue("TypeAuthenticationS"));
        registerInfo.setName((String) query.getOutputParameterValue("NameUserS"));
        registerInfo.setCountry((String) query.getOutputParameterValue("CountryS"));
        registerInfo.setHeight((Double) query.getOutputParameterValue("heightS"));
        registerInfo.setAge((Integer) query.getOutputParameterValue("AgeS"));
        loggedInfo.setStartingDate((LocalDate) query.getOutputParameterValue("DateStartingS"));
        registerInfo.setStartingWeight((Double) query.getOutputParameterValue("StartingWeightS"));
        loggedInfo.setCurrentWeight((Double) query.getOutputParameterValue("CurrentWeightS"));
        registerInfo.setCurrentWeight((Double) query.getOutputParameterValue("CurrentWeightS"));
        registerInfo.setEndWeight((Double) query.getOutputParameterValue("EndWeightS"));
        loggedInfo.setLostWeight((Double) query.getOutputParameterValue("LostWeightS"));
        loggedInfo.setGainedWeight((Double) query.getOutputParameterValue("GainedWeightS"));
        loggedInfo.setCurrentCalories((Integer) query.getOutputParameterValue("CurrentCaloriesS"));
        loggedInfo.setCurrentProtein((Integer) query.getOutputParameterValue("ProteinS"));
        loggedInfo.setCurrentCarbohydrates((Integer) query.getOutputParameterValue("CarbohydratesS"));
        loggedInfo.setCurrentFats((Integer) query.getOutputParameterValue("FatS"));
        registerInfo.setGoal((String) query.getOutputParameterValue("GoalS"));
        registerInfo.setGender((String) query.getOutputParameterValue("GenderS"));
        loggedInfo.setStateHealth((String) query.getOutputParameterValue("State_healthS"));
        registerInfo.setLevelActivity((String) query.getOutputParameterValue("TypeLevelS"));
        // Here I get the information got in the query
        normalUser.setInfoLogged(loggedInfo);
        normalUser.setRegisterInformation(registerInfo);
        return normalUser;
    }

    @Override
    public boolean changeUser(appUser user, String newUser) {
        String nameProcedure = switch (user.getTypeAuthentication()) {
            case "App" -> "updateUserApp";
            case "Google" -> "updateUserGoogle";
            case "Facebook" -> "updateUserFacebook";
            default -> null;
        };

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);

        // Register input parameters
        query.registerStoredProcedureParameter("userOld", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("newUser", String.class, ParameterMode.IN);
        if (user.getTypeAuthentication().equals("App")) {
            query.registerStoredProcedureParameter("PasswordS", String.class, ParameterMode.IN);
        }
        // Register output parameter
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);

        // Set input parameters
        query.setParameter("userOld", user.getUser());
        query.setParameter("newUser", newUser);
        if (user.getTypeAuthentication().equals("App")) {
            query.setParameter("PasswordS", user.getPassWord());
        }

        // Execute the stored procedure
        boolean t = query.execute();

        // Retrieve the output parameter
        return (Boolean) query.getOutputParameterValue("in_success");
    }

    // Here I update the information of the user in the database each 24 hours
    @Override
    public boolean updateInformation24Hours(appUser normalUser) {
        InfoRegister registerInfo = normalUser.getRegisterInformation();
        infoLogged loggedInfo = normalUser.getInfoLogged();

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("updateStateUser");

        // Register input parameters
        query.registerStoredProcedureParameter("User", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CurrentWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("LostWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("GainedWeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CurrentCaloriesS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ProteinS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CarbohydratesS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FatS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("State_healthS", String.class, ParameterMode.IN);

        // Register output parameter
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);
        // Set input parameters
        query.setParameter("User", normalUser.getUser());
        query.setParameter("CurrentWeightS", loggedInfo.getCurrentWeight());
        query.setParameter("LostWeightS", loggedInfo.getLostWeight());
        query.setParameter("GainedWeightS", loggedInfo.getGainedWeight());
        query.setParameter("CurrentCaloriesS", loggedInfo.getCurrentCalories());
        query.setParameter("ProteinS", loggedInfo.getCurrentProtein());
        query.setParameter("CarbohydratesS", loggedInfo.getCurrentCarbohydrates());
        query.setParameter("FatS", loggedInfo.getCurrentFats());
        query.setParameter("State_healthS", loggedInfo.getStateHealth());

        // Execute the stored procedure
        boolean t = query.execute();

        // Retrieve the output parameter
        return (Boolean) query.getOutputParameterValue("in_success");
    }

    @Override
    public boolean updateDataSelected(appUser normalUser) {
        InfoRegister registerInfo = normalUser.getRegisterInformation();
        infoLogged loggedInfo = normalUser.getInfoLogged();
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("updateData");

        // Register input parameters
        query.registerStoredProcedureParameter("User", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NameUserS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CountryS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("HeightS", Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("AgeS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CurrentCalories", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ProteinS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CarbohydratesS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FatS", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Goal", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("GenderS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("State_healthS", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("level_activity", String.class, ParameterMode.IN);

        // Register output parameter
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);

        // Set input parameters
        query.setParameter("User", normalUser.getUser());
        query.setParameter("NameUserS", registerInfo.getName());
        query.setParameter("CountryS", registerInfo.getCountry());
        query.setParameter("HeightS", registerInfo.getHeight());
        query.setParameter("AgeS", registerInfo.getAge());
        query.setParameter("CurrentCalories", loggedInfo.getCurrentCalories());
        query.setParameter("ProteinS", loggedInfo.getCurrentProtein());
        query.setParameter("CarbohydratesS", loggedInfo.getCurrentCarbohydrates());
        query.setParameter("FatS", loggedInfo.getCurrentFats());
        query.setParameter("Goal", registerInfo.getGoal());
        query.setParameter("GenderS", registerInfo.getGender());
        query.setParameter("State_healthS", loggedInfo.getStateHealth());
        query.setParameter("level_activity", registerInfo.getLevelActivity());
        // Execute the stored procedure
        boolean t = query.execute();

        // Retrieve the output parameter
        return (Boolean) query.getOutputParameterValue("in_success");
    }

    @Override
    public boolean deleteUser(appUser user) {

        String nameProcedure = switch (user.getTypeAuthentication()) {
            case "App" -> "deleteUserApp";
            case "Google" -> "deleteUserGoogle";
            case "Facebook" -> "deleteUserFacebook";
            default -> null;
        };
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameProcedure);
        // Register input parameters
        query.registerStoredProcedureParameter("userOld", String.class, ParameterMode.IN);
        if (user.getTypeAuthentication().equals("App")) {
            query.registerStoredProcedureParameter("PasswordS", String.class, ParameterMode.IN);
        }

        // Register output parameter
        query.registerStoredProcedureParameter("in_success", Boolean.class, ParameterMode.OUT);

        // Set input parameters
        query.setParameter("userOld", user.getUser());

        if (user.getTypeAuthentication().equals("App")) {
            query.setParameter("PasswordS", user.getPassWord());
        }

        // Execute the stored procedure
        boolean t = query.execute();

        // Retrieve the output parameter
        return (Boolean) query.getOutputParameterValue("in_success");
    }

}