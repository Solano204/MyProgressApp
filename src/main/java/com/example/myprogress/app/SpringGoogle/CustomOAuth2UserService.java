package com.example.myprogress.app.SpringGoogle;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Controller.FacebookGoogleController;
import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.EmailExistException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.Repositories.FaceUserRepository;
import com.example.myprogress.app.Repositories.GoogleUserRepository;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;

import lombok.Data;

// This class is charge of the save a object user in the session or general context

@Service
@Data
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final GoogleUserRepository googleUserRepository;
    private final FaceUserRepository faceUserRepository;
    private final RegisterGeneral registerGeneral;
    private final caloriesIntakeService caloriesIntakeService;
    private final BuildToken buildToken;
    private final MessagesFinal messagesFinal;
    private final FacebookGoogleController facebookGoogleController;
    private final LoginGeneral loginGeneral;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String auth = userRequest.getClientRegistration().getClientName();
        // Retrieve user attributes from the OAuth2User
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        
        if(facebookGoogleController.getCurrentProcess().equalsIgnoreCase("register")) {
            registerUser(auth, email);
        }else{
            loginUser(auth, email);
        }

        // Create a custom OAuth2User with additional attributes and save this object in
        // the general context, to use it later in whole application is like
        // getContexGlobal
        return new CustomOAuth2User(oAuth2User.getAuthorities(), attributes, "sub");
    }


    // In this method I register the user in the database(Google or Facebook)
    public void registerUser(String auth, String email) {
        if (auth.equals("Google") && googleUserRepository.ExistEmail(email, auth)) {
            throw new EmailExistException("Lo sentimos ese correo ya esta en uso,Elige Otro"); 
        }

        if (auth.equals("Facebook") && faceUserRepository.ExistEmail(email, auth)) {
            throw new EmailExistException("Lo sentimos ese correo ya esta en uso,Elige Otro"); 
        }
        ;
        // If the user doesn't exist in the database I register him
        appUser currentUserToRegisterGF = facebookGoogleController.getCurrentUserToRegisterGF(); // Here I call the
                                                                                                 // user to register
                                                                                                 // in the database
        currentUserToRegisterGF.setEmail(email);
        currentUserToRegisterGF.setTypeAuthentication(auth); // Here I modify the type of authentication GOOGLRE or
                                                             // FACEBOOK
        if (!registerGeneral.RegisterUser(currentUserToRegisterGF)) {
            throw new UnsuccessfulRegisterException("El usuario no pudo ser registrado con su cuenta de ".concat(auth));
        }

        CaloriesIntake newUser = new CaloriesIntake();
        newUser.setId(currentUserToRegisterGF.getUser());
        caloriesIntakeService.saveUser(newUser);
    }


    //In this part I check if the email exist in the database(Google or Facebook)
    public void loginUser(String auth,String email) {

        if (auth.equals("Google") && !googleUserRepository.ExistEmail(email, auth)) {
            throw new EmailExistException("Lo sentimos ese correo no esta registrado,Verifique su correo"); 
        }

        if (auth.equals("Facebook") && faceUserRepository.ExistEmail(email, auth)) {
            throw new EmailExistException("Lo sentimos ese correo no esta registrado,Verifique su correo"); 
        }

    }

}
