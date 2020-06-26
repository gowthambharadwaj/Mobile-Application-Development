package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import okhttp3.ResponseBody;

public class Inbox extends AppCompatActivity {

    TextView fullname;
    ArrayList<InboxData> inbox = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ImageView logout;
    ImageView composeEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Inbox");

        logout = findViewById(R.id.logout);
        composeEmail = findViewById(R.id.composeEmail);
        fullname = findViewById(R.id.username);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(Inbox.this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setHasFixedSize(true);
        String name = getIntent().getExtras().getString("DataSent");
        fullname.setText(name);

        composeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inbox.this, NewEmail.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inbox.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        OkHttpClient client = new OkHttpClient();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Inbox.this);
        String token = sharedPreferences.getString("token", null);

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox")
                .addHeader("Authorization", "BEARER " + "" + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!(response.isSuccessful())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Inbox.this, "No data to display", Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new IOException("Unexpected code: " + response);
                }


                try {
                    ResponseBody responseBody = response.body();
                    Log.d("demo", "response code: " + response.code());
                    String res = responseBody.string();
                    Log.d("demo", "res: " + res);
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("messages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        InboxData inboxData = new InboxData();
                        inboxData.subject = jObj.getString("subject");
                        inboxData.dateTime = jObj.getString("created_at");
                        inboxData.message = jObj.getString("message");
                        String fname = jObj.getString("sender_fname");
                        String lname = jObj.getString("sender_lname");
                        inboxData.senderName = fname + " " + lname;
                        inbox.add(inboxData);
                    }
                    Log.d("demo", "data: " + inbox);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Context context = getApplicationContext();
                            mAdapter = new InboxAdapter(inbox, context);
                            recyclerView.setAdapter(mAdapter);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
