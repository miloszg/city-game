package com.example.citygame.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel implements Parcelable {
    public String correct;
    public List<String> answers;
    public String content;
    public Integer markerId;

    public QuestionModel(String content, String correctAnswer, List<String> answers){
        this.correct = correctAnswer;
        this.answers = answers;
        this.content = content;
    }

    protected QuestionModel(Parcel in) {
        correct = in.readString();
        answers = in.createStringArrayList();
        content = in.readString();
        markerId = in.readInt();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(correct);
        parcel.writeStringList(answers);
        parcel.writeString(content);
        parcel.writeInt(markerId);
    }
}
