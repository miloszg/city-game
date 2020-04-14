package com.example.citygame;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameInfoActivity extends AppCompatActivity {

    class NetTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... params) {
            scenario = rester.getScenario(params[0]);
            return null;
        }
        protected void onPostExecute(String result) {
        // Stop your progress bar...
        setText();
    }
    }

    private Scenario scenario;
    private RESTHandler rester = new RESTHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        final String ID = getIntent().getStringExtra("ID");
        setScenario(ID);

        Button playBtn = (Button) findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

    }

    public void setScenario(String ID){
        NetTask task = new NetTask();
        task.execute(ID);
    }

    public void setText(){
        TextView gameName = (TextView) findViewById(R.id.scenarioName);
        TextView objectivesNum = findViewById(R.id.scenarioObjNum);

        gameName.setText(scenario.getScenarioName());
        objectivesNum.setText(String.valueOf(scenario.getObjectives().size()));
    }

    public void startGame(){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("scenario", scenario);
        startActivity(intent);
    }
}
