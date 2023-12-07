package View;

import Entity.LoginRegister;
import Service.LoginRegisterServiceImp;

import java.util.Objects;
import java.util.Scanner;

public class ViewLoginRegister {
    private LoginRegisterServiceImp loginRegisterServiceImp;

    public ViewLoginRegister(LoginRegisterServiceImp loginRegisterServiceImp) {
        this.loginRegisterServiceImp = loginRegisterServiceImp;
    }

    public void registerUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("REGISTRASI USER");
        System.out.println("==============================");
        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        // Validate input, check if username already exists, etc.

        this.loginRegisterServiceImp.registerUser(new LoginRegister(username, password));
        System.out.println("User successfully registered!");
    }

    public void loginUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("LOGIN USER");
        System.out.println("==============================");
        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        boolean loginSuccess = this.loginRegisterServiceImp.loginService(username, password);
//        if (loginSuccess) {
//            System.out.println("Login successful for username: " + username);
//        } else {
//            System.out.println("Login failed. Invalid username or password.");
//        }
    }

    public void viewLoginRegister() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Menu ");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("x. Exit");

            System.out.print("Enter your choice: ");
            String userInput = input.nextLine();

            if (Objects.equals(userInput, "1")) {
                registerUser();
            } else if (Objects.equals(userInput, "2")) {
                loginUser();
            } else if (Objects.equals(userInput, "x")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
        System.out.println("Thank you!");
    }
}
