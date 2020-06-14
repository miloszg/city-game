package com.example.citygame.Entrance;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.citygame.EntranceHandlers.RegisterHandler;
import com.example.citygame.MenuActivity;
import com.example.citygame.R;
import com.example.citygame.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private EditText emailEditText;
    private Button RegisterButton;
    private ImageButton goToMenu;
    private String login;
    private String password;
    private String email;

    @SuppressLint("RestrictedApi")

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
        login = loginEditText.getText().toString();
        password = passwordEditText.getText().toString();
        email = emailEditText.getText().toString();
        String[] credentialsArray = new String[]{password, email, login};
        String passCheck = passwordCheckEditText.getText().toString();

        if (isValidPassword(password, passCheck)) {
            if (isValidEmail(email)) {
                if(isValidLogin(login)) {
                    new RegisterHandler(new RegisterHandler.RegistrationHandlerFinishedListener() {
                        @Override
                        public void onFinished(Boolean resultIsOk) {
                            if (resultIsOk) {
                                User.instanceInitializerRegistration(login, email, password);
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