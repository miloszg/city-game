package com.example.citygame;

import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.citygame.Map.MapActivity;
import com.example.citygame.Models.Scenario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private Scenario scenario;
    private Button backToMap;
    private com.google.android.gms.maps.model.LatLng position;
    private int taskCounter = 0;
    private List<Scenario.Task> actualTasks;
    private List<Scenario.PossibleAnswers> actualAnswers = new ArrayList<>();
    private Scenario.Objective actualObjective;
    private boolean gameEnd = false;
    private int correctAnswers = 0;
    private double impossibleValue = 777;
     // private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Double latitiude;
        Double longitiude;

        scenario = (Scenario)getIntent().getSerializableExtra("scenario");
        latitiude = getIntent().getDoubleExtra("lat",impossibleValue );
        longitiude = getIntent().getDoubleExtra("long",impossibleValue );

        position = new com.google.android.gms.maps.model.LatLng(latitiude, longitiude);


        backToMap = findViewById(R.id.backToMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();
            }
        });

        if( latitiude == impossibleValue  && longitiude == impossibleValue )
            return;

        doObjective();
    }

    public void openMapActivity() {
        Intent intent = new Intent(this, MapActivity.class);

        if (actualObjective != null && gameEnd)
            scenario.deleteObjective(actualObjective);

        intent.putExtra("scenario", scenario);
        startActivity(intent);
    }

    public void doObjective(){

        if (scenario.getObjectives().size() < 1){
            return;
        }

        backToMap.setVisibility(View.INVISIBLE);

        for(Scenario.Objective objective : scenario.getObjectives()){

            LatLng pos = new LatLng(objective.getCoordinates().getLat(), objective.getCoordinates().getLon());

            if (pos.equals(position)){

                actualObjective = objective;
                TextView text = findViewById(R.id.QuestionT);
                actualTasks = objective.getTasks();
                doTasks(text);

                }
            }
        }

        public void doTasks(TextView text){

            int number = actualTasks.size();
            if (number > 0 && taskCounter < number){
                doTask(actualTasks.get(taskCounter), text);
            }
            else if (taskCounter >= number){
                finishGame(text);
            }
        }

        public void doTask(Scenario.Task task, final TextView text){

            text.setText(task.getQuestion());

            for(final Scenario.PossibleAnswers answer :task.getPossibleAnswers()) {

                Button myButton = new Button(this);
                myButton.setText(answer.getAnswer());

                LinearLayout ll = findViewById(R.id.layoutForAnswers);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(myButton, lp);


                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LinearLayout col2 = findViewById(R.id.layoutForAnswers);
                        col2.removeAllViews();

                        handlerAnswer(answer);
                        taskCounter = taskCounter + 1;
                        doTasks(text);
                    }
                });
            }
        }

        public void handlerAnswer(Scenario.PossibleAnswers answer){
        actualAnswers.add(answer);

        }

        public void finishGame(TextView text){

            TextView finalInfo = findViewById(R.id.finishInfo);

            for(Scenario.PossibleAnswers answer : actualAnswers){
                if(answer.getCorrect()){
                    correctAnswers = correctAnswers + 1;
                }
            }

            String finalMessage = "Zadanie wykonane \n" +
                                    "Poprawne odpowiedzi: " + correctAnswers;

            String mainMessage = "Koniec zadania";

            text.setText(mainMessage);
            finalInfo.setText(finalMessage);

            backToMap.setVisibility(View.VISIBLE);
            gameEnd = true;

        }
}

