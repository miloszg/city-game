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

import com.example.citygame.Entrance.LoginActivity;
import com.example.citygame.Entrance.RegisterActivity;
import com.example.citygame.MarkersList.Marker;
import com.example.citygame.MarkersList.MarkerListActivity;

import java.util.Arrays;

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

        Marker scandic = new Marker(1,"54.356492", "18.646619","Hotel Scandic");
        Marker oldTownHall = new Marker(2,"54.353965", "18.648058","Ratusz Staregomiejski");
        Marker heweliusz = new Marker(3,"54.354187", "18.648856","Pomnik Jana Heweliusza");
        Marker market = new Marker(4,"54.352849", "18.652253","Rynek miejski");
        Marker arsenal = new Marker(5,"54.350708", "18.649321","Zbrojownia");
        Marker goldenGate = new Marker(6,"54.349839", "18.648079","Złota Brama");
        Marker townHall = new Marker(7,"54.349009", "18.652174","Ratusz");
        Marker neptun = new Marker(8,"54.348643", "18.653377","Pomnik Neptun");
        Marker greenGate = new Marker(9,"54.348012", "18.655741","Zielona Brama");
        Marker crane = new Marker(10,"54.350573", "18.657599","Żuraw");
        Marker soldek = new Marker(11,"54.351434", "18.658640","Soldek");
        Marker mariacka = new Marker(12,"54.349698", "18.655406","Ulica Mariacka");
        Marker bazylika = new Marker(13,"54.349941", "18.653961","Bazylika Mariacka");

        QuestionModel placsolidarnosciQ = new QuestionModel("Jaką nazwę nosi instytucja mieszcząca się w budynku-muzeum" +
                "wzniesionym tuż obok dla upamiętnienia walki z totalitaryzmem?\n",
                "Europejskie Centrum Solidarności",
                Arrays.asList("Europejskie Centrum Solidarności", "B", "C"));
        QuestionModel wystawadrogadowolnosciQ = new QuestionModel("Ile kosztuje bilet wstępu na wystawę?",
                "6 zł",
                Arrays.asList("100 zł", "6 zł", "30 zł"));
        QuestionModel arsenalQ = new QuestionModel("Kiedy została zbudowana Zbrojownia?",
                "1602 – 1605",
                Arrays.asList("1902 – 1905","2008", "1602 – 1605"));

        QuestionModel goledQ = new QuestionModel("Ile figur- posągów alegorycznych stoi dumnie na szczycie bramy?",
                "8 (po 4 z każdej strony)",
                Arrays.asList("8 (po 4 z każdej strony)", "10 (po 5 z każdej strony)", "1"));
        QuestionModel bazyQ = new QuestionModel("Czy bazylika może pomieścić do 25 tysięcy osób?",
                "Prawda",
                Arrays.asList("Prawda", "Fałsz"));
        QuestionModel mariackaQ = new QuestionModel("Czy znajdują się tu sklepy z srebrem i bursztynem?",
                "Tak",
                Arrays.asList("Tak", "Nie"));
        QuestionModel craneQ = new QuestionModel("Jaki ptak znajduje się na szczycie budynku ?",
                "Żuraw",
                Arrays.asList("Czapla", "Bocian", "Żuraw"));
        QuestionModel soldekQ = new QuestionModel("Czy istnieje połączenie promem przez Motławę między Żurawiem a statkiem-muzeum “Sołdek” ?",
                "Tak",
                Arrays.asList("Tak", "Nie"));
        QuestionModel neptunQ = new QuestionModel("Jak się nazywa grecki bóg odpowiednik Neptuna?",
                "Posejdon",
                Arrays.asList("Zeus", "Apollo", "Posejdon"));
        QuestionModel uphagenQ = new QuestionModel("Gdzie mieści się kamienica?",
                "Dom Uphagena,\n" +
                        "Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 12, Gdańsk",
                Arrays.asList("Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 30, Gdańsk", "Oddział Muzeum Historycznego Miasta Gdańska, ul. Długa 12, Gdańsk", "Oddział Muzeum Historycznego Miasta Gdańska, ul. Krótka 12, Gdańsk"));

        QuestionModel hotelScandicQ = new QuestionModel("Ile gwiazdek ma Hotel Scandic?", "4", Arrays.asList("5", "4", "3"));
        QuestionModel oldTownCityHallQ = new QuestionModel("Jaki słynny astronom pracował w Ratuszu?","Jan Heweliusz", Arrays.asList("Jan Heweliusz", "Mikołaj Kopernik", "Isaac Newton", "Galileusz"));
        QuestionModel statueOfJHeweliuszQ = new QuestionModel("Czy Heweliusz żył w XVII wieku?", "Tak", Arrays.asList("Tak", "Nie"));
        QuestionModel cityMarketQ = new QuestionModel("Jaki znak widnieje nad bramą rynku miejskiego?", "Herb Gdański", Arrays.asList("Lwy Gdańskie","Miecz i tarcza", "Herb Gdański"));
        QuestionModel cityHallQ = new QuestionModel("W jakim stylu został zbudowany Ratusz?", "Gotyckim", Arrays.asList("Romański", "Gotycki", "Neogotycki"));
        QuestionModel greenGateQ = new QuestionModel("Jaki były prezydent RP posiadał biuro w Zielonej Bramie?", "Lech Wałęsa", Arrays.asList("Aleksander Kwaśniewski", "Lech Wałęsa", "Bronisław Komorowski"));


        neptun.setQuestion(neptunQ);
        soldek.setQuestion(soldekQ);
        crane.setQuestion(craneQ);
        mariacka.setQuestion(mariackaQ);
        bazylika.setQuestion(bazyQ);
        arsenal.setQuestion(arsenalQ);
        goldenGate.setQuestion(goledQ);
        scandic.setQuestion(hotelScandicQ);
        oldTownHall.setQuestion(oldTownCityHallQ);
        heweliusz.setQuestion(statueOfJHeweliuszQ);
        market.setQuestion(cityMarketQ);
        townHall.setQuestion(cityHallQ);
        greenGate.setQuestion(greenGateQ);

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
