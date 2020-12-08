package com.example.citygame.EntranceHandlers;

import android.os.AsyncTask;

import com.example.citygame.api.client.ApiClient;
import com.example.citygame.api.client.GenericApiException;
import com.example.citygame.api.client.InvalidTokenException;

import java.io.IOException;

public class ForgotPasswordTokenHandler extends AsyncTask<String, Void, ForgotPasswordTokenHandlerStatus> {

    private ForgotPasswordTokenHandler.ForgotPasswordTokenHandlerFinishedListener listener;

    public ForgotPasswordTokenHandler(ForgotPasswordTokenHandler.ForgotPasswordTokenHandlerFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected ForgotPasswordTokenHandlerStatus doInBackground(String... strings) {
        ApiClient apiClient = new ApiClient();
        try {
            apiClient.passwordChangeTokenResponse(strings[0], strings[1]);
            return ForgotPasswordTokenHandlerStatus.PASSWORD_CHANGED;
        } catch (InvalidTokenException e) {
            return ForgotPasswordTokenHandlerStatus.INVALID_TOKEN;
        } catch (IOException | GenericApiException e) {
            return ForgotPasswordTokenHandlerStatus.GENERIC_ERROR;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    protected void onPostExecute(ForgotPasswordTokenHandlerStatus result) {
        if (this.listener == null) return;
        this.listener.onFinished(result);
    }

    public interface ForgotPasswordTokenHandlerFinishedListener {
        void onFinished(ForgotPasswordTokenHandlerStatus result);
    }
}
