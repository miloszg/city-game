package com.example.citygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private User user = new User();

    private Button buttonLogin;
    private Button buttonForgotPassword;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.LogButton);
        buttonForgotPassword = findViewById(R.id.ForgotPasswordButton);
        emailEditText = findViewById(R.id.LoginEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgotPasswordActivity();
            }
        });
    }

    public void login(View v) {
        user.setPassword(passwordEditText.getText().toString());
        user.setEmail(emailEditText.getText().toString());
        String[] credentialsArray = new String[]{user.getPassword(), user.getEmail()};

        new LoginHandler(new LoginHandler.LoginHandlerFinishedListener() {

            @Override
            public void onFinished(Boolean resultIsOk) {
                if (resultIsOk) {
                    startMenuActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Logowanie niepomyslne", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(credentialsArray);
    }

    public void startForgotPasswordActivity() {
        Intent forgotPasswordActivity = new Intent(this,ForgotPasswordActivity.class);
        startActivity(forgotPasswordActivity);
    }

    public void startMenuActivity() {
        Intent menuActivity = new Intent(this,MenuActivity.class);
        startActivity(menuActivity);
    }
}
