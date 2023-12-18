package Entity;


import java.util.UUID;

public class LoginRegister {
    private UUID noIdentity;
    private String username;
    private String password;

    public LoginRegister() {
    }

    public LoginRegister(String username, String password) {
        this.noIdentity = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public UUID getNoIdentity() {
        return noIdentity;
    }

    public void setNoIdentity(UUID noIdentity) {
        this.noIdentity = noIdentity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
