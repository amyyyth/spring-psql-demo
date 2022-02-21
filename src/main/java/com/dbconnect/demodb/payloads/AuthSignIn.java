package com.dbconnect.demodb.payloads;

public class AuthSignIn {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthSignIn() {
        super();
    }

    public AuthSignIn(String usernameOrEmail, String password) {
        super();
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
