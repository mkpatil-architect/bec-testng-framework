package com.bec.api.automation.domain.account.login;

/**
 * Created by mkpatil on 09/01/19.
 */
public class LoginEntity {

    String   username;

    String password;



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

    @Override
    public String toString() {
        return "{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
