package com.example.citygame;

import com.example.citygame.Models.GameResponse;
import com.example.citygame.Models.Marker;
import com.example.citygame.Models.MarkerResponse;
import com.example.citygame.Models.QuestionModel;
import com.example.citygame.Models.QuestionResponse;

import java.util.ArrayList;
import java.util.List;


public class GameApp {
    public String title;
    public GameResponse game;
    public int drawable;

    public GameApp(String title, GameResponse game, int drawable) {
        this.title = title;
        this.game = game;
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Marker> getRouteMarkers(){
        ArrayList<Marker> markers = new ArrayList<Marker>();
        int counter = 0;
        for(MarkerResponse m: game.scenario.markers){
            Marker mm = new Marker(counter,m.lat, m.lon, m.title);
            QuestionModel q = new QuestionModel(m.question.content,m.question.correct, m.question.answers);
            q.markerId = counter;
            mm.question = q;

            markers.add(mm);
            counter++;
        }
        return markers;
    }
}
