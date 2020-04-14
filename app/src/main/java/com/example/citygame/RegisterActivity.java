package com.example.citygame;

import android.content.Intent;
import android.os.AsyncTask;
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

    RESTHandler restHandler = new RESTHandler();

    class NetTask extends AsyncTask<String, Integer, String> {

        String response;
        String username;
        String password;

        protected void onPreExecute() {
            // Start your progress bar...
        }

        protected String doInBackground(String... params) {

                username = params[0];
                password = params[1];
                try{
                    response = restHandler.registerUser(username, password);
                } catch (NullPointerException e){
                    System.out.println("NUll response");
                }
            return null;
        }

        protected void onPostExecute(String result) {

        }
    }

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

    private String LOGIN = " ";
    private String PASS = " ";
    private String EMAIL = " ";

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
                openMenuActivity();
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
        LOGIN = loginEditText.getText().toString();
        PASS = passwordEditText.getText().toString();
        EMAIL = emailEditText.getText().toString();
        String passCheck = passwordCheckEditText.getText().toString();
        

        if (isValidPassword(PASS, passCheck)) {
            if (isValidEmail(EMAIL)) {
                if(isValidLogin(LOGIN)) {
                    registerUser(LOGIN, PASS);
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

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e){
            throw e;
        }
    }

    String createJson(String Login, String Password, String Email) {
        return "{'UserName':'"+ Login +"',"
                + "'Email':'" + Email +"',"
                + "'PasswordHash':'" + Password +"'}";
    }

    public void openMenuActivity() {
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }

    void registerUser(String username, String password){
        NetTask task = new NetTask();
        task.execute(username, password);
    }


}