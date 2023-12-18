package Service;

import Entity.LoginRegister;
import Repository.LoginRegisterRepositoryImp;


public class LoginRegisterServiceImp implements LoginRegisterService {
    private LoginRegisterRepositoryImp loginRegisterRepositoryImp;

    public LoginRegisterServiceImp(LoginRegisterRepositoryImp loginRegisterRepositoryImp) {
        this.loginRegisterRepositoryImp = loginRegisterRepositoryImp;
    }

    @Override
    public void showUserPassword() {
        Integer i = 0;
        for (var user : this.loginRegisterRepositoryImp.findAll()) {
            if (user != null) {
                System.out.println(i + 1 + ". " + " No Identity " + user.getNoIdentity() + " Username : " + user.getUsername() +
                        " Password : " + user.getPassword());
                i++;
            }
        }
    }

    @Override
    public void registerUser(LoginRegister loginRegister) {
        this.loginRegisterRepositoryImp.save(loginRegister);
        System.out.println("Successfully register with username : "+loginRegister.getUsername());
    }


    @Override
    public boolean loginService(String username, String password) {
        if (this.loginRegisterRepositoryImp.LoginUser(username, password)) {
            System.out.println("Succes Login");
            return true;
        } else {
            throw new RuntimeException("Invalid Login");
        }
    }
    

}