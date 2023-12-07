package Repository;

import Entity.LoginRegister;

public interface LoginRegisterRepository {
    void save(LoginRegister loginRegister);

    LoginRegister[] findAll();

    boolean LoginUser(String username, String paswword);
}

