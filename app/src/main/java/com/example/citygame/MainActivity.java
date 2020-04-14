package com.example.citygame;

import android.Manifest;
import android.content.Intent;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.citygame.MarkersList.Marker;
import com.example.citygame.MarkersList.MarkerListActivity;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        Button loginBtn = (Button) findViewById(R.id.loginButton);
        Button registerBtn = (Button) findViewById(R.id.registerButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });

        Marker scandic = new Marker("54.356492", "18.646619","Hotel Scandic");
        Marker oldTownHall = new Marker("54.353965", "18.648058","Ratusz Staregomiejski");
        Marker heweliusz = new Marker("54.354187", "18.648856","Pomnik Jana Heweliusza");
        Marker market = new Marker("54.352849", "18.652253","Rynek miejski");
        Marker arsenal = new Marker("54.350708", "18.649321","Zbrojownia");
        Marker goldenGate = new Marker("54.349839", "18.648079","Złota Brama");
        Marker townHall = new Marker("54.349009", "18.652174","Ratusz");
        Marker neptun = new Marker("54.348643", "18.653377","Pomnik Neptun");
        Marker greenGate = new Marker("54.348012", "18.655741","Zielona Brama");
        Marker crane = new Marker("54.350573", "18.657599","Żuraw");
        Marker soldek = new Marker("54.351434", "18.658640","Soldek");
        Marker mariacka = new Marker("54.351434", "18.658640","Ulica Mariacka");
        Marker bazylika = new Marker("54.349941", "18.653961","Bazylika Mariacka");

        MarkerListActivity.list.add(scandic);
        MarkerListActivity.list.add(oldTownHall);
        MarkerListActivity.list.add(heweliusz);
        MarkerListActivity.list.add(market);
        MarkerListActivity.list.add(arsenal);
        MarkerListActivity.list.add(goldenGate);
        MarkerListActivity.list.add(townHall);
        MarkerListActivity.list.add(neptun);
        MarkerListActivity.list.add(greenGate);
        MarkerListActivity.list.add(crane);
        MarkerListActivity.list.add(soldek);
        MarkerListActivity.list.add(mariacka);
        MarkerListActivity.list.add(bazylika);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "The language is set to English", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Ustawiono język polski", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }




    @Override
    public void onBackPressed() {

    }
}
