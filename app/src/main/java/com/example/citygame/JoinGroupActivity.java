package com.example.citygame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.citygame.Map.MapActivity;
import com.example.citygame.MarkersList.MarkerListActivity;
import com.example.citygame.Models.GameResponse;
import com.example.citygame.Models.Marker;
import com.example.citygame.Models.QuestionModel;
import com.example.citygame.Models.ScenarioResponse;
import com.example.citygame.RouteList.RouteApp;
import com.example.citygame.RouteList.RouteListActivity;
import com.example.citygame.RouteList.RouteListAdapter;
import com.example.citygame.api.client.ApiClient;

import java.util.ArrayList;

public class JoinGroupActivity extends AppCompatActivity {
    public static ArrayList<GameApp> gamesList = new ArrayList<>();
    ApiClient apiClient = new ApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        final ListView routerList = findViewById(R.id.routeListView1);

        final GamesListAdapter adapter = new GamesListAdapter(this, R.layout.game_item, gamesList);
        routerList.setAdapter(adapter);

        if(gamesList.isEmpty()) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        GameResponse[] games = apiClient.getAllGames();

                        for(GameResponse g: games){
                            ScenarioResponse scenario = apiClient.getScenario(g.scenario.id);

                            ArrayList<Marker> markers = new ArrayList<Marker>();

                            for(int j =0;j<scenario.markers.size();j++) {
                                Marker m = new Marker(j, scenario.markers.get(j).lat,scenario.markers.get(j).lon,scenario.markers.get(j).title);
                                QuestionModel q = new QuestionModel(scenario.markers.get(j).question.content,scenario.markers.get(j).question.correct,scenario.markers.get(j).question.answers);
                                q.markerId = j;

                                m.question = q;

                                markers.add(m);
                            }

                            GameApp route = new GameApp(g.title, g,R.drawable.ic_game);
                            gamesList.add(route);
                            adapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
                adapter.notifyDataSetChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        routerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent cityGameIntent = new Intent(getApplicationContext(), MapActivity.class);
                cityGameIntent.putExtra("game",position);
                cityGameIntent.putExtra("tag", "joinGroup");
                cityGameIntent.putExtra("markers", gamesList.get(position).getRouteMarkers());

                ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

                for (Marker routeMarker : gamesList.get(position).getRouteMarkers()) {
                    questions.add(routeMarker.question);
                }

                cityGameIntent.putExtra("questions", questions);
                startActivity(cityGameIntent);
            }
        });
    }
}
