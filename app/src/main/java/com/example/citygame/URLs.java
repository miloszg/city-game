package com.example.citygame;

public class URLs {

    private String serverURLregistration = "http://34.91.192.193:3000/api/v1/registration";
    private String serverURLpasswordReset = "http://34.91.192.193:3000/api/v1/password_reset";
    private String serverURLlogin = "http://34.91.192.193:3000/api/v1/session"; //?password=";
    private String serverURLscenarios = "";
    private String serverURLlist = "";

    public URLs() {
    }

    public String getServerURLRegistration() {
        return serverURLregistration;
    }

    public String getServerURLPasswordReset() {
        return serverURLpasswordReset;
    }

    public String getServerURLLogin() {
        return serverURLlogin;
    }

    public String getServerURLScenarios() {
        return serverURLscenarios;
    }

    public String getServerURLList() {
        return serverURLlist;
    }
}
