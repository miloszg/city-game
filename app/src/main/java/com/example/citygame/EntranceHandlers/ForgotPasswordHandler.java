package com.example.citygame.EntranceHandlers;

import android.os.AsyncTask;

import com.example.citygame.api.client.ApiClient;
import com.example.citygame.api.client.EmailNotRecognizedException;
import com.example.citygame.api.client.GenericApiException;

import java.io.IOException;

public class ForgotPasswordHandler extends AsyncTask<String, Void, ForgotPasswordHandlerResult> {

    private String email;
    private ForgotPasswordHandlerListener forgotPasswordInterface;

    public ForgotPasswordHandler(ForgotPasswordHandlerListener forgotPasswordInterface,
                                 String email) {
        this.forgotPasswordInterface = forgotPasswordInterface;
        this.email = email;
    }

    @Override
    protected ForgotPasswordHandlerResult doInBackground(String... strings) {
        ApiClient apiClient = new ApiClient();
        try {
            apiClient.passwordChangeRequest(this.email);
            return ForgotPasswordHandlerResult.TOKEN_SENT;
        } catch (EmailNotRecognizedException e) {
            return ForgotPasswordHandlerResult.EMAIL_NOT_RECOGNIZED;
        } catch (IOException | GenericApiException e) {
            return ForgotPasswordHandlerResult.GENERIC_ERROR;
        }
    }

    @Override
    protected void onPostExecute(ForgotPasswordHandlerResult result) {
        if (this.forgotPasswordInterface == null) return;
        this.forgotPasswordInterface.onFinished(result);
    }

    public interface ForgotPasswordHandlerListener {
        void onFinished(ForgotPasswordHandlerResult result);
    }

}
