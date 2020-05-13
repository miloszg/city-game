package com.example.citygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    private RegistrationHandler handler;
    private String registrationURLPoint = "";
    private static final String TAG = "RegisterActivity";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private EditText emailEditText;
    private Button RegisterButton;
    private ImageButton goToMenu;
    private ProgressBar progressBar;

    @SuppressLint("RestrictedApi")
    private User newUser = new User();

    private int PASSWORD_MIN = 5;
    private int LOGIN_MIN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginEditText = findViewById(R.id.LoginEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);
        passwordCheckEditText = findViewById(R.id.ReplayPasswordEditText);
        emailEditText = findViewById(R.id.EmailEditText);
        RegisterButton = findViewById(R.id.registerButton);
        goToMenu = findViewById(R.id.goToMenu);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });


        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenuActivity();
            }
        });
}

    public boolean isValidPassword(String pass, String secPass){
        return pass.equals(secPass) && pass.length() > PASSWORD_MIN ;
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidLogin(CharSequence target) {
        return !TextUtils.isEmpty(target) && ( target.length() > LOGIN_MIN );
    }

    public void register(View v){
        newUser.setLogin(loginEditText.getText().toString());
        newUser.setPassword(passwordEditText.getText().toString());
        newUser.setEmail(emailEditText.getText().toString());
        String[] credentialsArray = new String[]{newUser.getPassword(), newUser.getEmail(), newUser.getLogin()};
        String passCheck = passwordCheckEditText.getText().toString();

        if (isValidPassword(newUser.getPassword(), passCheck)) {
            if (isValidEmail(newUser.getEmail())) {
                if(isValidLogin(newUser.getLogin())) {
                    new RegistrationHandler(new RegistrationHandler.RegistrationHandlerFinishedListener() {
                        @Override
                        public void onFinished(Boolean resultIsOk) {
                            if (resultIsOk) {
                                startMenuActivity();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Rejestracja niepomyslna", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute(credentialsArray);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Niepoprawny login", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(RegisterActivity.this, "Niepoprawny mail", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterActivity.this, "Niepoprawne lub niezgodne hasło", Toast.LENGTH_SHORT).show();
        }
    }


    public void startMenuActivity() {
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }




}