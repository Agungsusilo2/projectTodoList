package Test;

import Entity.LoginRegister;
import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;

public class testLoginRegisterServiceImp {
    public static void main(String[] args) {
        LoginRegisterRepositoryImp loginRegisterRepositoryImp = new LoginRegisterRepositoryImp();
        LoginRegisterServiceImp loginRegisterServiceImp = new LoginRegisterServiceImp(loginRegisterRepositoryImp);

        loginRegisterServiceImp.registerUser(new LoginRegister("Agung","Agung123"));
        loginRegisterServiceImp.loginService("Agung","Agung123");
    }
}
