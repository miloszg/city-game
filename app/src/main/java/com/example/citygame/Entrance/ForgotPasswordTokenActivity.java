package com.example.citygame.Entrance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citygame.EntranceHandlers.ForgotPasswordTokenHandler;
import com.example.citygame.EntranceHandlers.ForgotPasswordTokenHandlerStatus;
import com.example.citygame.MainActivity;
import com.example.citygame.R;
import com.example.citygame.User;


public class ForgotPasswordTokenActivity extends AppCompatActivity {

    private Button buttonChange;
    private Button buttonMain;
    private EditText passwordOneEditText;
    private EditText passwordTwoEditText;
    private EditText tokenEditText;
    private String passwordOne;
    private String passwordTwo;
    private String token;
    private int PASSWORD_MIN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_token);

        buttonChange = findViewById(R.id.ChangeButton);
        buttonMain = findViewById(R.id.MainButton);
        passwordOneEditText = findViewById(R.id.PasswordOneEditText);
        passwordTwoEditText = findViewById(R.id.PasswordTwoEditText);
        tokenEditText = findViewById(R.id.TokenEditText);
        buttonChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePassword(v);
            }
        });

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
    }

    public void changePassword(View v) {
        passwordOne = passwordOneEditText.getText().toString();
        passwordTwo = passwordTwoEditText.getText().toString();
        token = tokenEditText.getText().toString();

        if (isValidPassword(passwordOne, passwordTwo)) {
            String hyphen = "-";
            String fixedToken = insertString(token, hyphen, hyphen, 2, 5);
            String[] credentialsArray = new String[]{fixedToken, passwordOne};
            new ForgotPasswordTokenHandler(new ForgotPasswordTokenHandler.ForgotPasswordTokenHandlerFinishedListener() {
                @Override
                public void onFinished(ForgotPasswordTokenHandlerStatus result) {
                    switch (result) {
                        case PASSWORD_CHANGED:
                            Toast.makeText(ForgotPasswordTokenActivity.this, "Hasło zostało pomyślnie zmienione!", Toast.LENGTH_SHORT).show();
                            User.instanceDestroyer();
                            startMainActivity();
                            break;
                        case INVALID_TOKEN:
                            Toast.makeText(ForgotPasswordTokenActivity.this, "Wprowadzono niepoprawny token", Toast.LENGTH_SHORT).show();
                            break;
                        case GENERIC_ERROR:
                            Toast.makeText(ForgotPasswordTokenActivity.this, "Wysylanie prosby niepomyslne", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }).execute(credentialsArray);
        } else {
            Toast.makeText(ForgotPasswordTokenActivity.this, "Hasła są niepoprawne", Toast.LENGTH_SHORT).show();
        }
    }

    public void startMainActivity() {
        Intent forgotPasswordTokenActivity = new Intent(this, MainActivity.class);
        startActivity(forgotPasswordTokenActivity);
    }

    public boolean isValidPassword(String pass, String secPass){
        return pass.equals(secPass) && pass.length() > PASSWORD_MIN ;
    }

    public boolean isValidToken(String token) {
        return token.length() == 9;
    }

    public static String insertString(String originalString, String stringToBeInserted_1, String stringToBeInserted_2, int index_1, int index_2) {

        String newString = new String();

        for (int i = 0; i < originalString.length(); i++) {

            newString += originalString.charAt(i);

            if (i == index_1) {
                newString += stringToBeInserted_1;
            }

            if (i == index_2) {
                newString += stringToBeInserted_2;
            }
        }

        return newString;
    }

}
