package com.example.myprogress.app.HandleProcedures;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;

public interface ProcedureRepository {
    boolean ExistEmail(String email, String typeAuthentication);
    public boolean ExistUser(String user);
    <T> T getUserSelected(String user,String typeAuthentication);
    boolean addUser(String idUser, String password, String emailUser, String typeAuthentication);
    public boolean addProgressUser(User user);
    public boolean updateInformation24Hours(appUser user);
    public boolean changeUser(appUser user,String newUser);
    public boolean updateDataSelected(appUser user);
    public boolean deleteUser(appUser user);

}