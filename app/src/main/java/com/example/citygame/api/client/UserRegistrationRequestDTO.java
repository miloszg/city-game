package com.example.citygame.api.client;

public class UserRegistrationRequestDTO {

    private String name;
    private String email;
    private String password;

    public UserRegistrationRequestDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
