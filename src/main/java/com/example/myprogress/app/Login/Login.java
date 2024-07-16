package com.example.myprogress.app.Login;

// In this example, the Login interface is sealed. and I'll apply the design pattern TEMPLATE
public sealed interface Login permits FacebookLogin, GoogleLogin, AppLogin {

    public default boolean loginUser(final String typeLogin,final String user, final String passWord, final String email) {

        
        return switch(typeLogin){
            case "FacebookLogin" ->{
                FacebookLogin facebookLogin = new FacebookLogin();
                yield  facebookLogin.templateLogin(user, passWord, email);
            }                                                       
            case "GoogleLogin" ->{
                GoogleLogin googleLogin  = new GoogleLogin();
                yield  googleLogin.templateLogin(user, passWord, email);
            }
            case "AppLogin" ->{
                 AppLogin appLogin = new AppLogin();
                yield  appLogin.templateLogin(user, passWord, email);
            }
            
            default -> false;
        };
    }

    // This is a default method and the template that all the implementations must follow
    public default boolean templateLogin(final String user, final String passWord, final String email) {

        // if the email exist I log the user automatically with existing account 
        if (validateEmail(email)) {
            return true;
        }

        if (!validateUser(user)) {
            return false;
        }
        
        return true;
    }

    public default boolean logerUserExist() {
        return true;
    }


    // return true if the email exists and was succesful the login
    boolean validateEmail(String email);

    //Return false if the user exists and was unsuccesful the login
    boolean validateUser(String user);

    boolean logerUser(String user, String passWord, String passwordConfirm);

}
