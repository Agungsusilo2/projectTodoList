package Test;


import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;
import View.ViewLoginRegister;

public class testLoginRegisterView {


    public static void main(String[] args) {
        LoginRegisterRepositoryImp todoListRepositoryImp = new LoginRegisterRepositoryImp();
        LoginRegisterServiceImp todoListServiceImp = new LoginRegisterServiceImp(todoListRepositoryImp);
        ViewLoginRegister viewTodoList = new ViewLoginRegister(todoListServiceImp);

        viewTodoList.viewLoginRegister();
    }
}
