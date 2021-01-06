package com.example.citygame.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.citygame.Models.QuestionModel;

public class Marker implements Parcelable {
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

    protected Marker(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        lat = in.readString();
        lon = in.readString();
        title = in.readString();
    }

    public static final Creator<Marker> CREATOR = new Creator<Marker>() {
        @Override
        public Marker createFromParcel(Parcel in) {
            return new Marker(in);
        }

        @Override
        public Marker[] newArray(int size) {
            return new Marker[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(lat);
        parcel.writeString(lon);
        parcel.writeString(title);
    }
}
