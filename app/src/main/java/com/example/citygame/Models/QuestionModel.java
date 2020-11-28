package com.example.citygame.Models;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    public String correctAnswer;
    public List<String> answers;
    public String content;

    public QuestionModel(String content, String correctAnswer, List<String> answers){
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.content = content;
    }
}
