package com.example.citygame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RESTHandler {

    public static Token get_token() {
        return _token;
    }

    private static Token _token = new Token();

    public static void set_token(String response){

        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<Token>(){}.getType();


        // if response is able to be a token, assign token values
        try{
            _token = gson.fromJson(response, type);
        } catch (Exception e){
            _token = null;
        }
    }


    private URLs URLs = new URLs();


    public String loginUser (String username, String password) {

        // return Token

        try{
            URL Endpoint = new URL(URLs.getServerURLlogin());
            HttpURLConnection myConnection =
                    (HttpURLConnection) Endpoint.openConnection();

            myConnection.setRequestMethod("POST");

            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            myConnection.setRequestProperty("Accept", "application/json");

            // Create the data
            String myData = String.format("{\"username\":\"%s\", \"password\":\"%s\"}",
                    username, password);

            // Enable writing
            myConnection.setDoOutput(true);

            try {
                OutputStream os = myConnection.getOutputStream();
                byte[] input = myData .getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }

            int code = myConnection.getResponseCode();

            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(myConnection.getInputStream(), "utf-8"));

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());

                String response_str = response.toString();

                set_token(response_str);


                return response_str;

            } catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }

            myConnection.disconnect();
        } catch (java.io.IOException e){
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }


    public String registerUser (String username, String password) {

        // return Token

        try {
            URL Endpoint = new URL(URLs.getServerURLregistration());
            HttpURLConnection myConnection =
                    (HttpURLConnection) Endpoint.openConnection();

            myConnection.setRequestMethod("POST");

            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            myConnection.setRequestProperty("Accept", "application/json");

            // Create the data
            String myData = String.format("{\"username\":\"%s\", \"password\":\"%s\"}",
                    username, password);

            // Enable writing
            myConnection.setDoOutput(true);
            myConnection.connect();

            try {
                OutputStream os = myConnection.getOutputStream();
                byte[] input = myData.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }

            int code = myConnection.getResponseCode();

            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(myConnection.getInputStream(), "utf-8"));

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());

                String response_str = response.toString();

                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<Token>() {
                }.getType();

                set_token(response_str);

                return response_str;

            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }

            myConnection.disconnect();
        } catch (java.io.IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }

    public List<NameAndIdScenario> getNameAndIdScenarios () {

        String scenariosURL = URLs.getServerURLlist();
        List<NameAndIdScenario> scenarios = new ArrayList<>();

        try{
            URL Endpoint = new URL(scenariosURL);
            HttpURLConnection conn =
                    (HttpURLConnection) Endpoint.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + _token.get_token());


            if (conn.getResponseCode() == 200) {

                StringBuilder sb = new StringBuilder();

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }

                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<List<NameAndIdScenario>>(){}.getType();
                scenarios = gson.fromJson(sb.toString(), type);
                return scenarios;

            } else {
                System.out.println("fail");
            }
            conn.disconnect();
        } catch (java.io.IOException e){
            System.out.println("Error " + e.getMessage());
        }
        return scenarios;
    }

    public Scenario getScenario (String id) {

        String scenariosURL = URLs.getServerURLscenarios() + id + "/";
        Scenario scenario = new Scenario();

        try{
            URL Endpoint = new URL(scenariosURL);
            HttpURLConnection conn =
                    (HttpURLConnection) Endpoint.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + _token.get_token());


            if (conn.getResponseCode() == 200) {

                StringBuilder sb = new StringBuilder();

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }

                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<Scenario>(){}.getType();
                scenario = gson.fromJson(sb.toString(), type);
                return scenario;

            } else {
                System.out.println("fail");
            }
            conn.disconnect();
        } catch (java.io.IOException e){
            System.out.println("Error " + e.getMessage());
        }
        return scenario;
    }

}
