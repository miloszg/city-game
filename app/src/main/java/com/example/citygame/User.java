package com.example.citygame;

public class User {
    private String login;
    private String email;
    private String password;
    private static User user;

    private User() {
    }



/*    public void setLogin(String name) {
        this.login = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public static void instanceInitializerRegistration(String login, String email, String password) {
        user = new User();
        user.login = login;
        user.email = email;
        user.password = password;
    }

    public static void instanceInitializerLogin(String email, String password) {
        user = new User();
        user.email = email;
        user.password = password;
    }

    public static void instanceInitializerForgotPassword(String email) {
        user = new User();
        user.email = email;
    }

    public static User getInstance() {
        return user;
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
