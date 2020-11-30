package com.example.citygame;

public class User {
    private String login;
    private String email;
    private String password;
    private String accessToken;
    private static User user;

    private User() {
    }

    public static void instanceInitializerRegistration(String login, String email, String password) {
        user = new User();
        user.login = login;
        user.email = email;
        user.password = password;
    }

    public static void instanceInitializerLogin(String email, String password, String accessToken) {
        user = new User();
        user.email = email;
        user.password = password;
        user.accessToken = accessToken;
    }

    public static void instanceInitializerForgotPassword(String email) {
        user = new User();
        user.email = email;
    }

    public static void instanceDestroyer() {
        User.user = null;
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
