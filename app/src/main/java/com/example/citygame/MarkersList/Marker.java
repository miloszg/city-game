package com.example.citygame.MarkersList;

import com.example.citygame.QuestionModel;

public class Marker {
    public String lat;
    public String lon;
    public String title;
    public QuestionModel question;

    public Marker(String lat, String lon, String title) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }
}
