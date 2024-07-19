package com.example.myprogress.app.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.AppUserRepository;

public final class AppRegister extends Register {

    private  AppUserRepository appUserRepository;
    


    public AppRegister(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean validateEmail(String email,String typeAuthentication) {
        return appUserRepository.ExistEmail(email, typeAuthentication); // return true if the email exists
    }

    @Override
    public boolean validateUser(String user,String typeAuthentication) {
        return !appUserRepository.ExistUser(user, typeAuthentication); // Here I return the opposite (exits == false) (does not exist == true)
    }

    @Override
    public boolean logerUser(String user, String passWord, String email,String typeAuthentication) {
            if (!appUserRepository.addUser(user, passWord, email, typeAuthentication)) {
                new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception 
                return false;
            }
            return true; // return true if the user was registered
    }

    @Override
    public <T> T getInformationUser(String idUser, String typeAuthentication) {
        return (T) appUserRepository.getUserSelected(idUser, typeAuthentication);
    }

   
    


}
