package com.example.myprogress.app.updateInformationService;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.Repositories.AppUserRepository;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Service
public class updateInformationUserService {

    GeneratorDataUser generatorDataUser;
    AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public updateInformationUserService(GeneratorDataUser generatorDataUser, AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {
        this.generatorDataUser = generatorDataUser;
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    // Here I change the user of the app
    public boolean changeUser(appUser user, String newUser) {

        // First validate if already exists a user with the newUser. then Its error
        // because cant there is two users with the same user
        if (appUserRepository.ExistUser(newUser)) {
            throw new UserExistException("Already exits a user with this user, please choose another one");
        }
        if (!appUserRepository.ExistUser(user.getUser())) {
            throw new UserExistException("User incorrect please check its the user, not exists");
        }

        if (user.getTypeAuthentication().equalsIgnoreCase("App")) {
            Optional<appUser> getUser = appUserRepository.findByIdUser(user.getUser());
            // The order is very important here
            if (!passwordEncoder.matches(user.getPassWord(), getUser.get().getPassWord())) {
                return false;
            }
            ;
        }
        return appUserRepository.changeUser(user, newUser);
    }

    // Update the information of the user each 24 hours, The difference is Here I
    // provided the calories consumed today, and here the user didn't decide change
    // its data
    public boolean updateInformationUser24(appUser user, int caloriesConsumed) {
        if (!appUserRepository.ExistUser(user.getUser())) {
            throw new UserExistException("User incorrect please check its the user, not exists");
        }
        generatorDataUser.setCurrentUser(user);
        generatorDataUser.getregisterInfo();
        generatorDataUser.calculateTheGainedAndLostWeight(caloriesConsumed);
        generatorDataUser.generateCaloriesRecommended();
        generatorDataUser.generateMacrosnutrientsRecommended();
        generatorDataUser.generateCurrentStateHealth();
        generatorDataUser.updateInformationRecommended();
        return appUserRepository.updateInformation24Hours(user);
    }

    // When the user I decided change its data (Gender,Height,Age,Goal,Activity)
    // Update the information of the user and here the user decided change its data
    public boolean updateInformationUserRecommendedData(appUser user) {
        if (!appUserRepository.ExistUser(user.getUser())) {
            throw new UserExistException("User incorrect please check its the user, not exists");
        }
        generatorDataUser.setCurrentUser(user);
        generatorDataUser.getregisterInfo();
        generatorDataUser.generateCaloriesRecommended();
        generatorDataUser.generateMacrosnutrientsRecommended();
        generatorDataUser.generateCurrentStateHealth();
        generatorDataUser.updateInformationRecommended();
        return appUserRepository.updateDataSelected(user);
    }

    // Here I change the password
    public int changePassword(String newPass, String user, String oldPass) {
        if (!appUserRepository.ExistUser(user)) {
            throw new UserExistException("User incorrect please check its the user, not exists");
        }

        Optional<appUser> getUser = appUserRepository.findByIdUser(user);
        // The order is very important here
        if (!passwordEncoder.matches(oldPass, getUser.get().getPassWord())) {
            return 0;
        }
        ;

        int h = appUserRepository.updatePassword(passwordEncoder.encode(newPass), user);
        return h;
    }

    // Here I delete the user
    public boolean deleteUser(appUser user) {
        if (!appUserRepository.ExistUser(user.getUser())) {
            throw new UserExistException("User incorrect please check its the user, not exists");
        }

        if (user.getTypeAuthentication().equalsIgnoreCase("App")) {
            Optional<appUser> getUser = appUserRepository.findByIdUser(user.getUser());
            // The order is very important here
            if (!passwordEncoder.matches(user.getPassWord(), getUser.get().getPassWord())) {
                return false;
            }
        }

        boolean j = appUserRepository.deleteUser(user);
        return j;
    }

    public appUser  getDataUpdated(String user, String typeAuthentication) {
        return appUserRepository.getUserSelected(user, typeAuthentication);
    }

    // In this method i evaluate if the user's objetive is completed 
    public String evaluateObjetive(appUser user) {
        if (user.getRegisterInformation().getGoal().equals("Ganar Peso")
                && user.getRegisterInformation().getEndWeight() < user.getInfoLogged().getCurrentWeight()) {
            return "El objetivo fue alcanzado felicitaciones, Logro ganar el peso deseado";
        } else if (user.getRegisterInformation().getGoal().equals("Perder Peso")
                && user.getRegisterInformation().getEndWeight() > user.getInfoLogged().getCurrentWeight()) {
            return "El objetivo fue alcanzado felicitaciones, Logro Perder el peso deseado";
        } else {
            return " ";
        }
    }

}
