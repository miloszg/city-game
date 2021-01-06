package com.example.citygame.Entrance;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.citygame.EntranceHandlers.LoginHandler;
import com.example.citygame.EntranceHandlers.LoginHandlerResult;
import com.example.citygame.EntranceHandlers.LoginHandlerResultStatus;
import com.example.citygame.MenuActivity;
import com.example.citygame.R;
import com.example.citygame.Models.User;

public class LoginActivity extends AppCompatActivity {

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
        final String password = passwordEditText.getText().toString();
        final String email = emailEditText.getText().toString();

        new LoginHandler(new LoginHandler.LoginHandlerFinishedListener() {

            @Override
            public void onFinished(LoginHandlerResult result) {

                switch (result.getStatus()) {
                    case SUCCESS:
                        User.instanceInitializerLogin(email, password, result.getUser().getAccessToken());
                        startMenuActivity();
                        break;
                    case INVALID_CREDENTIALS:
                        Toast.makeText(LoginActivity.this, "Niepoprawne dane logowania", Toast.LENGTH_SHORT).show();
                        break;
                    case GENERIC_ERROR:
                        Toast.makeText(LoginActivity.this, "Logowanie niepomyslne", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }).execute(password, email);
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
