package com.example.citygame;

public class User {
    private String login;
    private String email;
    private String password;

    public User() {
    }


    public void setLogin(String name) {
        this.login = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
