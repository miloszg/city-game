package com.example.citygame.Entrance;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.citygame.EntranceHandlers.LoginHandler;
import com.example.citygame.MenuActivity;
import com.example.citygame.R;
import com.example.citygame.User;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonForgotPassword;
    private EditText emailEditText;
    private EditText passwordEditText;
    private String password;
    private String email;

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
        password = passwordEditText.getText().toString();
        email = emailEditText.getText().toString();
        String[] credentialsArray = new String[]{password, email};

        new LoginHandler(new LoginHandler.LoginHandlerFinishedListener() {

            @Override
            public void onFinished(Boolean resultIsOk) {
                if (resultIsOk) {
                    User.instanceInitializerLogin(email, password);
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
        Intent menuActivity = new Intent(this, MenuActivity.class);
        startActivity(menuActivity);
    }
}