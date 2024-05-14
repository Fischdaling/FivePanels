package org.theShire.domain.RichType;

public class Password {
    private String password;
    private String hashedPassword;

    public Password(String password) {
        setPassword(password);
        this.hashedPassword = String.valueOf(password.hashCode());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
