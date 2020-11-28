package com.example.citygame;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.citygame.Map.MapActivity;
import com.example.citygame.RouteList.RouteListActivity;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardGame:
                openChooseGameActivity();
                break;
            case R.id.cardPresentation:
                openPresentationActivity();
                break;
            case R.id.cardProfile:
                openProfileActivity();
                break;
            case R.id.cardGroup:
                openGroupActivity();
                break;
            default:
                break;
        }
    }

    public void openPresentationActivity() {
        Intent presentation = new Intent(this, PresentationActivity.class);
        startActivity(presentation);
    }

    public void openProfileActivity() {
        Intent profile = new Intent(this, MapActivity.class);
        startActivity(profile);
    }

    public void openChooseGameActivity() {
        Intent chooseGame = new Intent(this, RouteListActivity.class);
        startActivity(chooseGame);
    }

    public void openGroupActivity() {
        Intent group = new Intent(this, GroupActivity.class);
        startActivity(group);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_map:
                Intent mapIntent = new Intent(this, MapActivity.class);
                startActivity(mapIntent);
                break;
            case R.id.nav_settings:
                Intent intent_settings = new Intent();
                intent_settings.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent_settings.setData(uri);
                startActivity(intent_settings);
                break;
            case R.id.nav_help:
                Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}





