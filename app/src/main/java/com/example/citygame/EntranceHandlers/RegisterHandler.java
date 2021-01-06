package com.example.citygame.EntranceHandlers;
import android.os.AsyncTask;

import com.example.citygame.api.client.ApiClient;
import com.example.citygame.api.client.EmailTakenException;
import com.example.citygame.api.client.GenericApiException;
import com.example.citygame.api.client.UserRegistrationFailedException;

import java.io.IOException;


public class RegisterHandler extends AsyncTask<Void, Void, RegisterHandlerResult> {

    private String userName, email, password;
    private RegistrationHandlerFinishedListener listener;

    public RegisterHandler(RegistrationHandlerFinishedListener listener,
                           String userName,
                           String email,
                           String password) {
        this.listener = listener;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Override
    protected RegisterHandlerResult doInBackground(Void... voids) {

        ApiClient apiClient = new ApiClient();
        try {
            apiClient.registerNewUser(this.userName, this.email, this.password);
            return RegisterHandlerResult.SUCCESS;
        } catch (EmailTakenException e) {
            e.printStackTrace();
            return RegisterHandlerResult.EMAIL_TAKEN;
        } catch (UserRegistrationFailedException | IOException | GenericApiException e) {
            e.printStackTrace();
            return RegisterHandlerResult.GENERIC_ERROR;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    @Override
    protected void onPostExecute(RegisterHandlerResult result) {
        if (this.listener == null) return;
        this.listener.onFinished(result);
    }

    public interface RegistrationHandlerFinishedListener {
        void onFinished(RegisterHandlerResult result);
    }
}
