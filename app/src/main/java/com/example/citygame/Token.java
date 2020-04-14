package com.example.citygame;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Token {

    private String token;


    public Token(String access_token) {
        this.token = access_token;
    }

    public Token() {
    }


    public String get_token() {
        return token;
    }

    public void revoke() {
        try {
            this.token = null;
        } catch (NullPointerException e){
            return;
        }
    }

    public void set_token(String access_token) {
        this.token = access_token;
    }

        private static Token getToken(String response){

        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<Token>(){}.getType();


        // if response is able to be a token, assign token values
        try{
            return  gson.fromJson(response, type);
        } catch (Exception e){
            return  null;
        }
    }
}