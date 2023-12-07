package Service;

import Entity.LoginRegister;

public interface LoginRegisterService {
    void showUserPassword();
    public void registerUser(LoginRegister loginRegister);

    boolean loginService(String username, String password);
}
