package com.example.citygame.EntranceHandlers;

import android.os.AsyncTask;

import com.example.citygame.URLs;
import com.example.citygame.api.client.ApiClient;
import com.example.citygame.api.client.InvalidCredentialsException;
import com.example.citygame.api.client.UserLoginResponseDTO;

import java.io.IOException;

public class LoginHandler extends AsyncTask<String, Void, LoginHandlerResult> {

    private URLs urlGet = new URLs();
    private LoginHandlerFinishedListener loginListener;

    public LoginHandler(LoginHandlerFinishedListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    protected LoginHandlerResult doInBackground(String... strings) {
        ApiClient apiClient = new ApiClient();
        try {
            UserLoginResponseDTO user = apiClient.login(strings[1], strings[0]);
            return new LoginHandlerResult(LoginHandlerResultStatus.SUCCESS, user);
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            return new LoginHandlerResult(LoginHandlerResultStatus.INVALID_CREDENTIALS, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new LoginHandlerResult(LoginHandlerResultStatus.GENERIC_ERROR, null);
        }
    }

    @Override
    protected void onPostExecute(LoginHandlerResult result) {
       if (this.loginListener == null) return;
       this.loginListener.onFinished(result);
    }

    public interface LoginHandlerFinishedListener {
        void onFinished(LoginHandlerResult result);
    }

}
