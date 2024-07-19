package com.example.myprogress.app.HandleProcedures;

public interface ProcedureRepository {
    boolean ExistEmail(String email, String typeAuthentication);
    public boolean ExistUser(String user, String typeAuthentication);
    <T> T getUserSelected(String user,String typeAuthentication);
    boolean addUser(String idUser, String password, String emailUser, String typeAuthentication);
}