package com.example.citygame;

public class URLs {


    public URLs() {
    }

    private String serverURLregistration = "http://35.214.223.15:3000/api/v1/registration";
    private String serverURLpasswordReset = "http://35.214.223.15:3000/api/v1/password_reset";
    private String serverURLlogin = "http://35.214.223.15:3000/api/v1/login";
    private String serverURLscenarios = "";
    private String serverURLlist = "";



    public String getServerURLregistration() {
        return serverURLregistration;
    }

    public String getServerURLpasswordReset() {
        return serverURLpasswordReset;
    }

    public String getServerURLlogin() {
        return serverURLlogin;
    }

    public String getServerURLscenarios() {
        return serverURLscenarios;
    }

    public String getServerURLlist() {
        return serverURLlist;
    }
}
