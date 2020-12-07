package com.example.citygame.Entrance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citygame.EntranceHandlers.ForgotPasswordHandler;
import com.example.citygame.EntranceHandlers.ForgotPasswordHandlerResult;
import com.example.citygame.MenuActivity;
import com.example.citygame.R;
import com.example.citygame.User;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button buttonRequest;
    private EditText emailEditText;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        buttonRequest = findViewById(R.id.RequestButton);
        emailEditText = findViewById(R.id.EmailEditText);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(v);
            }
        });
    }

    public void request(View v) {
        email = emailEditText.getText().toString();
        new ForgotPasswordHandler(new ForgotPasswordHandler.ForgotPasswordHandlerListener() {
            @Override
            public void onFinished(ForgotPasswordHandlerResult result) {
                switch (result) {
                    case TOKEN_SENT:
                        User.instanceInitializerForgotPassword(email);
                        startForgotPasswordTokenActivity();
                    case EMAIL_NOT_RECOGNIZED:
                        Toast.makeText(ForgotPasswordActivity.this, "Taki adres email nie istnieje", Toast.LENGTH_SHORT).show();
                    case GENERIC_ERROR:
                        Toast.makeText(ForgotPasswordActivity.this, "Wysylanie prosby niepomyslne", Toast.LENGTH_SHORT).show();
                }
            }
        }, email).execute();
    }

    public void startForgotPasswordTokenActivity() {
        Intent forgotPasswordTokenActivity = new Intent(this,ForgotPasswordTokenActivity.class);
        startActivity(forgotPasswordTokenActivity);
    }
}

