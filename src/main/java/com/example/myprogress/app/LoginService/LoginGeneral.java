package com.example.myprogress.app.LoginService;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.AppLogin;
import com.example.myprogress.app.LoginService.FacebookLogin;
import com.example.myprogress.app.LoginService.GoogleLogin;
import com.example.myprogress.app.LoginService.Login;
import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.Repositories.FaceUserRepository;
import com.example.myprogress.app.Repositories.GoogleUserRepository;

@Service
public class LoginGeneral {

 private AppUserRepository appUserRepository;
    private FaceUserRepository faceUserRepository; 
    private GoogleUserRepository googleUserRepository;
    private MessagesFinal messagesFinal;

    public LoginGeneral(AppUserRepository appUserRepository, FaceUserRepository faceUserRepository, GoogleUserRepository googleUserRepository,  MessagesFinal messagesFinal) {
        this.appUserRepository = appUserRepository;
        this.faceUserRepository = faceUserRepository;
        this.googleUserRepository = googleUserRepository;
        this.messagesFinal = messagesFinal;
    }

    private Login login;

    // Here I decide How will i login the user
    public boolean LoginUser(appUser user) {
        return switch (user.getTypeAuthentication()) {
            case "Facebook" -> {
                login = new FacebookLogin(faceUserRepository);
                login.setMessagesFinal(messagesFinal);
                yield login.templateLogin(user);
            }
            case "Google" -> {
                login = new GoogleLogin(googleUserRepository);
                login.setMessagesFinal(messagesFinal);
                yield login.templateLogin(user);
            }
            case "App" -> {
                login = new AppLogin(appUserRepository);
                login.setMessagesFinal(messagesFinal);
                yield login.templateLogin(user);
            }
            default -> false;
        };
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
