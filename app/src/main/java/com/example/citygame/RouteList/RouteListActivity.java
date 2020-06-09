package com.example.citygame.RouteList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.citygame.MainActivity;
import com.example.citygame.Map.MapActivity;
import com.example.citygame.MarkersList.Marker;
import com.example.citygame.MarkersList.MarkerListActivity;
import com.example.citygame.R;

import java.util.ArrayList;



public class RouteListActivity extends AppCompatActivity {
    private static final String TAG = "RouteListActivity";
    public static ArrayList<RouteApp> routeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView routerList = findViewById(R.id.routeListView);


        RouteListAdapter adapter = new RouteListAdapter(this, R.layout.custom_view_layout,routeList);
        routerList.setAdapter(adapter);

        if(routeList.isEmpty()) {
            RouteApp route = new RouteApp("Gdańsk City Game", MarkerListActivity.list, R.drawable.ic_game);
            routeList.add(route);
        }

        routerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"Option Menu Clicked");
                Toast.makeText(RouteListActivity.this, "chosen option: "+position, Toast.LENGTH_SHORT).show();
                Intent cityGameIntent = new Intent(getApplicationContext(), MapActivity.class);
                cityGameIntent.putExtra("game","1");
                startActivity(cityGameIntent);
            }
        });
    }
}
