package com.example.citygame.EntranceHandlers;

import android.os.AsyncTask;

import com.example.citygame.URLs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginHandler extends AsyncTask<String, Void, Boolean> {

    private URLs urlGet = new URLs();
    private LoginHandlerFinishedListener loginListener;


    public LoginHandler(LoginHandlerFinishedListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String urlAppended = new StringBuilder(urlGet.getServerURLLogin()).append("?password=").append(strings[0]).append("&email=").append(strings[1]).toString();
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
       if (this.loginListener == null) return;
       this.loginListener.onFinished(result);
    }

    public interface LoginHandlerFinishedListener {
        void onFinished(Boolean result);
    }

}
