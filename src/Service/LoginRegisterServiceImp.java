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
        System.out.println("DATABASE USER PASSWORD");
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
    }


    @Override
    public boolean loginService(String username, String password) {
        if (this.loginRegisterRepositoryImp.LoginUser(username, password)) {
            System.out.println("Sukses login");
        } else {
            System.out.println("Gagal login");
        }
        return true;
    }
}
