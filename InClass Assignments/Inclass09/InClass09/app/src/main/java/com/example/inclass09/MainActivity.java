package com.example.inclass09;

// Assignment by Gowtham Bharadwaj and Rajath Anand Group 18

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button login;
    Button signup;
    public static int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String passw = pass.getText().toString().trim();
                OkHttpClient client = new OkHttpClient();
                MediaType json = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();

                try {
                    data.put("email", mail);
                    data.put("password", passw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(json, data.toString());
                Log.d("demo", requestBody.toString());
                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        if (!response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                }
                            });
                            throw new IOException("Unexpected code: " + response);
                        }

                        try {
                            ResponseBody responseBody = response.body();
                            Log.d("demo", "response code: " + response.code());
                            String res = responseBody.string();
                            Log.d("demo", "data: " + res);
                            JSONObject jobj = new JSONObject(res);
                            String status = jobj.getString("status");
                            String token = jobj.getString("token");
                            String fname = jobj.getString("user_fname");
                            String lname = jobj.getString("user_lname");
                            Log.d("demo", "Data Json: " + status + "  " + token + "   " + fname + "   " + lname);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, Inbox.class);
                            intent.putExtra("DataSent", fname + " " + lname);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("token") == null) {
                } else {
                    String token = data.getStringExtra("token");
                    String fname = data.getStringExtra("firstName");
                    String lastName = data.getStringExtra("lastName");
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                    Toast.makeText(this, "USER SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Inbox.class);
                    intent.putExtra("DataSent", fname + " " + lastName);
                    startActivity(intent);
                    finish();

                }
            }
        }
    }

}
