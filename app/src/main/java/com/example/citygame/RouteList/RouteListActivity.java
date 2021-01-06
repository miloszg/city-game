package com.example.citygame.RouteList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.citygame.Map.MapActivity;
import com.example.citygame.MarkersList.MarkerListActivity;
import com.example.citygame.Models.Marker;
import com.example.citygame.Models.QuestionModel;
import com.example.citygame.Models.ScenarioResponse;
import com.example.citygame.R;
import com.example.citygame.api.client.ApiClient;

import java.util.ArrayList;
import java.util.List;


public class RouteListActivity extends AppCompatActivity {
    private static final String TAG = "RouteListActivity";
    public static ArrayList<RouteApp> routeList = new ArrayList<>();
    ApiClient apiClient = new ApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView routerList = findViewById(R.id.routeListView);


        final RouteListAdapter adapter = new RouteListAdapter(this, R.layout.custom_view_layout,routeList);
        routerList.setAdapter(adapter);

        if(routeList.isEmpty()) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    RouteApp route = new RouteApp("Gdańsk City Game", MarkerListActivity.list, R.drawable.ic_game);
                    routeList.add(route);
                    adapter.notifyDataSetChanged();

                    try  {
                        ScenarioResponse[] scenarios = apiClient.getAllScenarios();

                        for(int i = 0; i<scenarios.length;i++){
                            ArrayList<Marker> markers = new ArrayList<Marker>();

                            for(int j =0;j<scenarios[i].markers.size();j++) {
                                Marker m = new Marker(j, scenarios[i].markers.get(j).lat,scenarios[i].markers.get(j).lon,scenarios[i].markers.get(j).title);
                                QuestionModel q = new QuestionModel(scenarios[i].markers.get(j).question.content,scenarios[i].markers.get(j).question.correct,scenarios[i].markers.get(j).question.answers);
                                q.markerId = j;

                                m.question = q;

                                markers.add(m);
                            }

                            route = new RouteApp(scenarios[i].name,markers, R.drawable.ic_game);
                            routeList.add(route);
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
                Log.i(TAG,"Option Menu Clicked");
                Toast.makeText(RouteListActivity.this, "chosen option: "+position, Toast.LENGTH_SHORT).show();
                Intent cityGameIntent = new Intent(getApplicationContext(), MapActivity.class);
                cityGameIntent.putExtra("game",position);
                cityGameIntent.putExtra("markers", routeList.get(position).getRouteMarkers());

                ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

                for (Marker routeMarker : routeList.get(position).getRouteMarkers()) {
                    questions.add(routeMarker.question);
                }

                cityGameIntent.putExtra("questions", questions);
                startActivity(cityGameIntent);
            }
        });
    }
}
