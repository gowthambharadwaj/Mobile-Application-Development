package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewEmail extends AppCompatActivity {
    int selId;
    String errmsg;
    Spinner spinner;
    EditText subject;
    EditText message;
    Button send;
    Button cancel;
    ArrayList<String> myUsers = new ArrayList<>();
    ArrayList<String> myUserIds = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_email);
        setTitle("Compose email");
        spinner = findViewById(R.id.spinner);
        subject = findViewById(R.id.subject_mail);
        message = findViewById(R.id.emailBody);
        send = findViewById(R.id.send);
        cancel = findViewById(R.id.emailCancel);
        new getAllUsers().execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/users");
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,myUsers);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selId = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewEmail.this);
                String token = sharedPreferences.getString("token",null);

                OkHttpClient okHttpClient = new OkHttpClient();
                MediaType json = MediaType.parse("application/json;charset=utf-8");
                JSONObject sendData = new JSONObject();
                try{
                    sendData.put("receiver_id",myUserIds.get(selId));
                    sendData.put("subject",subject.getText().toString().trim());
                    sendData.put("message",message.getText().toString().trim());
                }catch(JSONException e){
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(json,sendData.toString());
                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/add")
                        .post(body)
                        .addHeader("Authorization","BEARER " + "" +token)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewEmail.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class getAllUsers extends AsyncTask<String,Void,ArrayList<String>>{



        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewEmail.this);
            String token = sharedPreferences.getString("token",null);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/users")
                    .addHeader("Authorization","BEARER " + "" +token)
                    .build();

            try{

                Response response = okHttpClient.newCall(request).execute();
                String json = response.body().string();
                JSONObject root = new JSONObject(json);
                String status = root.getString("status");
                if (status.equals("error")) {
                    errmsg = root.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewEmail.this, errmsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    JSONArray users = root.getJSONArray("users");
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject jsonarticle = users.getJSONObject(i);
                        name = jsonarticle.getString("fname") + " " + jsonarticle.getString("lname");
                        myUserIds.add(jsonarticle.getString("id"));
                        myUsers.add(name);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return myUsers;


        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            adapter.notifyDataSetChanged();
        }
        }
}
