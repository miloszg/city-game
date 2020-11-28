package com.example.citygame.Models;

import com.example.citygame.Models.QuestionModel;

public class Marker {
    public Integer id;
    public String lat;
    public String lon;
    public String title;
    public QuestionModel question;

    public Marker(Integer id, String lat, String lon, String title) {
        this.id = id;
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
