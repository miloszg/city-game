package com.example.citygame.EntranceHandlers;

import com.example.citygame.api.client.UserLoginResponseDTO;

public class LoginHandlerResult {
    private LoginHandlerResultStatus status;
    private UserLoginResponseDTO user;

    public LoginHandlerResult(LoginHandlerResultStatus status, UserLoginResponseDTO user) {
        this.status = status;
        this.user = user;
    }

    public LoginHandlerResultStatus getStatus() {
        return status;
    }

    public void setStatus(LoginHandlerResultStatus status) {
        this.status = status;
    }

    public UserLoginResponseDTO getUser() {
        return user;
    }

    public void setUser(UserLoginResponseDTO user) {
        this.user = user;
    }
}
