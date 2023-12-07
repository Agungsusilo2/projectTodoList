

import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JPanel jpanelLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField fieldByUsername;


    public JPanel getJpanelLogin() {
        return jpanelLogin;
    }

    public Login() {
        LoginRegisterServiceImp loginServiceImp = new LoginRegisterServiceImp(new LoginRegisterRepositoryImp());

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldUsername.getText();
                String password = String.valueOf(fieldPassword.getPassword());

                try {
                    if (username.isEmpty() || username.isBlank()) {
                        throw new IllegalArgumentException("Username tidak boleh kosong");
                    } else if (password.isEmpty()) {
                        throw new IllegalArgumentException("Password tidak boleh kosong");
                    }


                    boolean loginSuccessful = loginServiceImp.loginService(username, password);
                    if (loginSuccessful) {
                        JOptionPane.showMessageDialog(null, "Login berhasil");
                    } else {
                        throw new RuntimeException("Username atau password salah");
                    }
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(null, "Login gagal: " + exception.getMessage());
                }
            }
        });

        btnRegister.addActionListener(e -> {
            JFrame frame = new JFrame("Register");
            frame.setContentPane(new Register().getJpanelRegister());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            JFrame loginFrame = (JFrame) SwingUtilities.getRoot(btnLogin);
            loginFrame.dispose();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        Login login = new Login();
        frame.setContentPane(login.getJpanelLogin());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
