package com.example.myprogress.app.LoginService;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.PasswordIncorrect;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.Repositories.AppUserRepository;

public non-sealed class AppLogin extends Login {

    private AppUserRepository appUserRepository;

    public AppLogin(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return appUserRepository.ExistEmail(email, typeAuthentication); // return true if the email exists
    }

    // In the case of the an user that was registered in the app normal I have to
    // also validate the password
    @Override
    public boolean validateUser(String user, String password) {
        if (!appUserRepository.ExistUser(user)) {
            throw new UserExistException("The user Not found, please register");
        }
        if (appUserRepository.validatePassword(user, password) == 0) {
            throw new PasswordIncorrect("The password is incorrect, please fix it up");
        }
        ; // HERE i return false if the user not exists
        return true;
    }

    @Override
    public <T> T getInformationUser(String idUser, String typeAuthentication) {
        return (T) appUserRepository.getUserSelected(idUser, typeAuthentication);
    }

}
