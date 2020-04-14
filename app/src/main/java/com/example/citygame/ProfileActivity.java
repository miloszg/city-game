package com.example.citygame;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button profileButton = findViewById(R.id.twojButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YourProfileActivity();
            }
        });
    }

    public void YourProfileActivity() {
        Intent twojProfil = new Intent(this, YourProfileActivity.class);
        startActivity(twojProfil);
    }
}
