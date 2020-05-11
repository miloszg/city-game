package com.example.citygame;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginHandler extends AsyncTask {

    private URLs urlGet = new URLs();

    @Override
    protected Object doInBackground(Object[] objects) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
       /* try {
            URL url = new URL(urlGet.getServerURLlogin());
            DataOutputStream outputStream;
            DataInputStream inputStream;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();*/

        return null;
    }
}
