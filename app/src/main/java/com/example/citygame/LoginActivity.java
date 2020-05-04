package com.example.citygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    RESTHandler restHandler = new RESTHandler();

    @SuppressLint("NewApi")
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
                    response = restHandler.loginUser(username, password);
                } catch (NullPointerException e){
                    System.out.println("NUll response");
                }

            return null;
        }

        protected void onPostExecute(String result) {

            try {
                if ( restHandler.get_token().get_token() != null){
                    openMenuActivity();
                    finish();
                }
            } catch (NullPointerException e) {
                System.out.println(e.toString());
            }
        }
        }


    private static final String TAG = "LoginActivity";
    private static final String KEY_LOGIN = "Login";
    private static final String KEY_PASS = "Haslo";

    private Button button;
    private EditText loginEditText;
    private EditText passwordEditText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteRef = db.collection("Uzytkownik");

    private String LOGIN = " ";
    private String PASS = " ";

    private boolean ACCES = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.LogButton);
        loginEditText = findViewById(R.id.LoginEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                LOGIN = login;
                PASS = pass;
                //loginUser(LOGIN, PASS);
                openMenuActivity();
            }
        });

        restHandler.get_token().revoke();
    }
    public void openMenuActivity() {
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }

    public void logged() {
        openMenuActivity();
    }

    public void login(View v) {

        // network task has to be deploy on AsyncTask
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                // build http request for login
                Request request = new Request.Builder()
                        .url("http://citygame.hostingasp.pl/api/User?UserName=" +
                                LOGIN + "&Password=" + PASS)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    // ! response.body().string() can be call only once
                    String responseString = response.body().string();
                    System.out.println(responseString);
                    try {
                        JSONObject json = new JSONObject(responseString);
                        System.out.println(json.get("UserName"));
                        if(json.get("UserName").equals(LOGIN)) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Zalogowano", Toast.LENGTH_SHORT).show();
                                    logged();
                                }
                            });
                        }
                    } catch ( JSONException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginActivity.this,"Błąd logowania", Toast.LENGTH_SHORT).show();

                                // TESTING !!! 
                                logged();
                            }
                        });
                        Log.d(TAG, e.toString());
                    }
                }
                catch (java.io.IOException e )
                {
                    Log.e(TAG, e.toString());
                }

            }
        });
    }

    void loginUser(String username, String password){
        NetTask task = new NetTask();
        task.execute(username, password);
    }
}
