package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    EditText et_fname;
    EditText et_lname;
    EditText et_email;
    EditText et_pass;
    EditText et_repeat;
    Button signUp;
    Button cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign up");

        et_fname = findViewById(R.id.firstName);
        et_lname = findViewById(R.id.lastName);
        et_email = findViewById(R.id.email);
        et_pass = findViewById(R.id.password);
        et_repeat = findViewById(R.id.repeatPassword);
        signUp = findViewById(R.id.signupSignup);
        cancel = findViewById(R.id.cancelSignup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(et_pass.getText().toString().trim().equals(et_repeat.getText().toString().trim()))
                        {
                            doPostRequest();
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignupActivity.this, "Please provide same password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void doPostRequest(){
        Log.d("OKHTTP3","Post Button Called");
        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        JSONObject actualData = new JSONObject();
        try {
            actualData.put("email",""+et_email.getText().toString().trim());
            actualData.put("password",""+et_pass.getText().toString().trim());
            actualData.put("fname",""+et_fname.getText().toString().trim());
            actualData.put("lname",""+et_lname.getText().toString().trim());
        } catch (JSONException e) {
            Log.d("OKHTTP3","JSON Exception");
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(json,actualData.toString());
        Request newReq = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup")
                .post(body)
                .build();
        try {
            final Response response = client.newCall(newReq).execute();
            Log.d("OKHTTP3","Got Response");
            Log.d("OKHTTP3","**"+response.code());
            if(response.code() == 200 ||response.code() == 201 ){
                String responseObj = response.body().string();
                JSONObject jsonObject = new JSONObject(responseObj);
                final String status = jsonObject.getString("status");
                final String token = jsonObject.getString("token");
                final String fName = jsonObject.getString("user_fname");
                final String lName = jsonObject.getString("user_lname");

                if(status.equalsIgnoreCase("error")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            intent.putExtra("token",token);
                            intent.putExtra("firstName",fName);
                            intent.putExtra("lastName",lName);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                }

            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, "Enter Correct Values", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("OKHTTP3","IO Exception");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("OKHTTP3","JSON Exception");
        }
    }
}

