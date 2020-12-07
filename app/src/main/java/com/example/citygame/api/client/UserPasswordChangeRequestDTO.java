package com.example.citygame.api.client;

public class UserPasswordChangeRequestDTO {

    private String email;

    public UserPasswordChangeRequestDTO (String email){
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
