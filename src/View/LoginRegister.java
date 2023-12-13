package View;

import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegister {
    private JTextField fieldUsernameLogin;
    private JTextField fieldUsernameRegister;
    private JButton btnRegister;
    private JButton btnLogin;
    private JLabel lblHeaderTodo;
    private JLabel lblLogin;
    private JLabel lblRegister;
    private JLabel lblUsernameLogin;
    private JLabel lblUsernameRegister;
    private JLabel lblPasswordLogin;
    private JLabel lblPasswordPassword;
    private JLabel lblRePasswordRegister;
    private JPanel jpanelLoginRegister;
    private JPasswordField fieldPasswordRegister;
    private JPasswordField fieldRePasswordRegister;
    private JPasswordField fieldPasswordLogin;

    private LoginRegisterServiceImp loginRegisterServiceImp;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginRegister");
        frame.setContentPane(new LoginRegister().jpanelLoginRegister);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public LoginRegister() {
        loginRegisterServiceImp = new LoginRegisterServiceImp(new LoginRegisterRepositoryImp());
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldUsernameLogin.getText();
                String password = String.valueOf(fieldPasswordLogin.getPassword());

                try {
                    if (username.isEmpty() || username.isBlank()) {
                        throw new IllegalArgumentException("Username not Blank");
                    } else if (password.isEmpty()) {
                        throw new IllegalArgumentException("Password not Blank");
                    }

                    loginRegisterServiceImp.loginService(username, password);


                    JFrame frame = new JFrame("TodoList");
                    frame.setContentPane(new Todolist().getJpanelTodo());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                    JFrame loginFrame = (JFrame) SwingUtilities.getRoot(btnLogin);
                    loginFrame.dispose();
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(null, "Login failed: " + exception.getMessage());
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldUsernameRegister.getText();
                String password = String.valueOf(fieldPasswordRegister.getPassword());
                String passwordRepeat = String.valueOf(fieldRePasswordRegister.getPassword());


                try {
                    if (username.isEmpty() || username.isBlank()) {
                        throw new IllegalArgumentException("Username not Blank");
                    } else if (password.isEmpty()) {
                        throw new IllegalArgumentException("Password not Blank");
                    } else if (!password.equals(passwordRepeat)) {
                        throw new IllegalArgumentException("Password not match");
                    }

                    loginRegisterServiceImp.registerUser(new Entity.LoginRegister(username,password));
                    JOptionPane.showMessageDialog(null, "Register successfully");
                    fieldUsernameRegister.setText("");
                    fieldPasswordRegister.setText("");
                    fieldRePasswordRegister.setText("");
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(null, "Register failed: " + exception.getMessage());
                }
            }
        });
    }
}
