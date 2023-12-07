import Entity.LoginRegister;
import Repository.LoginRegisterRepositoryImp;
import Service.LoginRegisterServiceImp;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    private JPanel jpanelRegister;
    private JTextField fieldUsername;
    private JButton btnRegister;
    private JButton btnLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPasswordRepeat;
    private JPasswordField fieldPassword;
    private JPasswordField fieldPasswordRepeat;

    private LoginRegisterServiceImp loginRegisterServiceImp;

    public void setJpanelRegister(JPanel jpanelRegister) {
        this.jpanelRegister = jpanelRegister;
    }

    public Register() {
        loginRegisterServiceImp = new LoginRegisterServiceImp(new LoginRegisterRepositoryImp());
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().getJpanelLogin());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

                JFrame registerFrame = (JFrame) SwingUtilities.getRoot(btnLogin);
                registerFrame.dispose();
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldUsername.getText();
                String password = String.valueOf(fieldPassword.getPassword());
                String passwordRepeat = String.valueOf(fieldPasswordRepeat.getPassword());


                try {
                    if (username.isEmpty() || username.isBlank()) {
                        throw new IllegalArgumentException("Username tidak boleh kosong");
                    } else if (password.isEmpty()) {
                        throw new IllegalArgumentException("Password tidak boleh kosong");
                    } else if (!password.equals(passwordRepeat)) {
                        throw new IllegalArgumentException("Password tidak sama");
                    }

                    loginRegisterServiceImp.registerUser(new LoginRegister(username,password));
                    JOptionPane.showMessageDialog(null, "Registrasi berhasil");
                    fieldUsername.setText("");
                    fieldPassword.setText("");
                    fieldPasswordRepeat.setText("");
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(null, "Registrasi gagal: " + exception.getMessage());
                }
            }
        });



    }

    public JPanel getJpanelRegister() {
        return jpanelRegister;
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Register");
        Register register = new Register();
        frame.setContentPane(register.jpanelRegister);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
