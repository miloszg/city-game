package com.example.citygame;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForgotPasswordHandler extends AsyncTask<String, Void, Boolean> {

    private URLs urlGet = new URLs();
    private ForgotPasswordHandlerListener forgotPasswordInterface;


    public ForgotPasswordHandler(ForgotPasswordHandlerListener forgotPasswordInterface) {
        this.forgotPasswordInterface = forgotPasswordInterface;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String urlAppended = new StringBuilder(urlGet.getServerURLpasswordReset()).append("?email=").append(strings[0]).toString();
            URL url = new URL(urlAppended);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            int httpResult = connection.getResponseCode();

            if (httpResult == HttpURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                bufferedReader.close();

                System.out.println(("" + stringBuilder.toString()));
                return true;
            } else {
                System.out.println(connection.getResponseMessage());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (this.forgotPasswordInterface == null) return;
        this.forgotPasswordInterface.onFinished(result);
    }

    public interface ForgotPasswordHandlerListener {
        void onFinished(Boolean result);
    }

}
