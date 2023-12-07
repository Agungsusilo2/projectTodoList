package Test;


import Entity.LoginRegister;
import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;

public class testLoginRegisterRepositoryImp {
    public static void main(String[] args) {
        LoginRegisterRepositoryImp loginRegisterRepositoryImp = new LoginRegisterRepositoryImp();
        LoginRegisterServiceImp loginRegisterServiceImp = new LoginRegisterServiceImp(loginRegisterRepositoryImp);
        loginRegisterRepositoryImp.save(new LoginRegister("Agung","Agung123"));


        loginRegisterServiceImp.showUserPassword();
    }
}
