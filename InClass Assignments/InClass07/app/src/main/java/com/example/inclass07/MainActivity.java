package com.example.inclass07;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
 * Submitted by Gowtham Bharadwaj and Rajath Anand
 * */

public class MainActivity extends AppCompatActivity {

    EditText et_search;
    SeekBar sb;
    Button search;
    RadioGroup rg;
    RadioButton track_rating;
    RadioButton artist_rating;
    ListView lv;
    String selected_btn;
    ProgressBar pb;
    TextView textView;
    ArrayList<Music> musics = new ArrayList<>();
    public static String WEB_KEY = "web";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        et_search = findViewById(R.id.et_name);
        sb = findViewById(R.id.seekBar);
        search = findViewById(R.id.search_btn);
        rg = findViewById(R.id.radioGroup);
        track_rating = findViewById(R.id.radioButton);
        artist_rating = findViewById(R.id.radioButton2);
        lv = findViewById(R.id.listView);
        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);

        sb.setMax(25);
        sb.incrementProgressBy(1);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        selected_btn = "s_track_rating";
                        break;
                    case R.id.radioButton2:
                        selected_btn = "s_artist_rating";
                        break;
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(i + " ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_name = et_search.getText().toString();

                if (search_name.equals("") || selected_btn.equals("")) {
                    et_search.setError("Enter value");
                } else {
                    int limit = sb.getProgress();

                    if (isConnected()) {
                        Log.d("demo", "data is here: " + search_name + "button:  " + selected_btn + "limit: " + limit);

                        musics.clear();


                        new getDataAsync().execute("http://api.musixmatch.com/ws/1.1/track.search?q=" + search_name + "&page_size=" + limit + "&" + selected_btn + "=desc&apikey=877ff80c6d2ea5b74e9fc28b2ca3040b");

                    } else {
                        Toast.makeText(MainActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("demo", "Selected items: " + search_name + "  button selected: " + selected_btn + "limit: " + limit);

                }

            }
        });


    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class getDataAsync extends AsyncTask<String, Void, ArrayList<Music>> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<Music> doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                Log.d("demo", "URL: " + url);

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    Log.d("demo", "data inside");

                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject message = jsonObject.getJSONObject("message");
                    JSONObject body = message.getJSONObject("body");
                    JSONArray track_list = body.getJSONArray("track_list");
                    for (int i = 0; i < track_list.length(); i++) {
                        Music music = new Music();
                        JSONObject jobj = track_list.getJSONObject(i).getJSONObject("track");
                        music.trackname = jobj.getString("track_name");
                        music.artistname = jobj.getString("artist_name");
                        music.albumname = jobj.getString("album_name");
                        music.url = jobj.getString("track_share_url");

                        String str = jobj.getString("updated_time");

                        ArrayList<String> arr = new ArrayList<String>();
                        for (String s : str.split("-")) {
                            arr.add(s);
                            //System.out.println(s);
                        }

                        music.date = arr.get(1) + "-" + arr.get(2).substring(0, 2) + "-" + arr.get(0);

                        Log.d("demo", "date: " + music.toString());

                        musics.add(music);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return musics;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(ArrayList<Music> music) {
            super.onPostExecute(music);
            pb.setVisibility(View.INVISIBLE);

            //Log.d("demo","music: "+music.get(0).date);

            MusicAdapter musicAdapter = new MusicAdapter(MainActivity.this, R.layout.use_list, music);

            musicAdapter.notifyDataSetChanged();

            lv.setAdapter(musicAdapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String url = musics.get(i).url;
                    Intent intent = new Intent(getApplicationContext(), ActionView.class);

                    intent.putExtra(WEB_KEY, url);


                    startActivity(intent);


                }
            });

        }
    }


}
