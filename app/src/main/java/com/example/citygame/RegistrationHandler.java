package com.example.citygame;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class RegistrationHandler extends AsyncTask<String, Void, Boolean> {

    private URLs urlPost = new URLs();
    private RegistrationHandlerFinishedListener listener;

    public RegistrationHandler(RegistrationHandlerFinishedListener listener) {
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(String... strings) {

        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlPost.getServerURLregistration());
            DataOutputStream outputStream;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            // create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("password", strings[0]);
            jsonParam.put("email", strings[1]);
            jsonParam.put("name", strings[2]);

            // send POST output
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonParam.toString());
            outputStream.flush();
            outputStream.close();

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
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
    protected void onPostExecute(Boolean result) {
        if (this.listener == null) return;
        this.listener.onFinished(result);
    }

    public interface RegistrationHandlerFinishedListener {
        void onFinished(Boolean result);

    }


}
