package com.example.inclass06;
/*
Submitted by Gowtham Bharadwaj and Rjath Anand
Group 18

 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StringBuilder stringUrl = new StringBuilder();
    ArrayList<String> names = new ArrayList<String>();
    String string;
    int right = 0;
    int size = 0;
    Button buttonleft;
    Button buttonright;
    Spinner spinner;
    String link;
    TextView txt;
    ImageView image;
    Button button;
    int i = 1;
    ArrayList<setter> objects = new ArrayList<setter>();
    ArrayList<setter> objectsresults = new ArrayList<setter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("News App");

        buttonleft = (Button) findViewById(R.id.button2);
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonleft.setEnabled(false);
        buttonright = (Button) findViewById(R.id.button3);
        buttonright.setEnabled(false);
        button = (Button) findViewById(R.id.button);
        button.setEnabled(false);

        final TextView txt = findViewById(R.id.textView);
        final TextView txt1 = findViewById(R.id.textView2);
        final TextView txt2 = findViewById(R.id.textView3);
        final TextView txt3 = findViewById(R.id.textView4);
        final ImageView image = findViewById(R.id.imageView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new connectionTest().execute(link);
                i = 0;
            }
        });
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        String[] categoryTitle = {"Choose Category", "Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, categoryTitle);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    buttonleft.setEnabled(false);
                    buttonright.setEnabled(false);
                    findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Choose the option below", Toast.LENGTH_LONG).show();
                } else {
                    findViewById(R.id.imageView).setVisibility(View.VISIBLE);
                    buttonleft.setEnabled(true);
                    buttonright.setEnabled(true);
                    button.setEnabled(true);
                    String key = spin.getSelectedItem().toString();
                    link = "https://newsapi.org/v2/top-headlines?apiKey=5c1e31b8ef6640409a543bf58f985e08&country=us&category=" + key;
                    Log.d("key", link);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                String des = objectsresults.get(i).description;
                if (des == "null") {
                    Toast.makeText(MainActivity.this, "No News Avaliable", Toast.LENGTH_LONG).show();
                }
                if (i >= objectsresults.size()) {
                    i = 0;
                }
                txt.setText(objectsresults.get(i).headlines);
                txt1.setText(objectsresults.get(i).description);
                txt2.setText(objectsresults.get(i).published);
                txt3.setText(i + 1 + "/" + objectsresults.size());
                new connectionImage().execute(objectsresults.get(i).imageurl);

            }
        });
        buttonleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                String des = objectsresults.get(i).description;
                if (des == "null") {
                    Toast.makeText(MainActivity.this, "No News Avaliable", Toast.LENGTH_LONG).show();
                }
                if (i < 0) {
                    i = objectsresults.size() - 1;
                }
                txt.setText(objectsresults.get(i).headlines);
                txt1.setText(objectsresults.get(i).description);
                txt2.setText(objectsresults.get(i).published);
                txt3.setText(i + 1 + "/" + objectsresults.size());
                new connectionImage().execute(objectsresults.get(i).imageurl);

            }
        });

    }

    public class connectionTest extends AsyncTask<String, Void, ArrayList<setter>> {
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> ImageLink = new ArrayList<String>();
        ArrayList<setter> objects1 = new ArrayList<setter>();

        public boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            } else return false;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(ArrayList<setter> setters) {
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            String des = setters.get(0).description;
            if (des == "null") {
                Toast.makeText(MainActivity.this, "No News Avaliable", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(setters);
            Bitmap myBitmap = null;
            Log.d("123", setters.get(0).headlines);
            objectsresults = objects1;
            TextView text = (TextView) findViewById(R.id.textView);
            TextView textD = (TextView) findViewById(R.id.textView2);
            TextView textP = (TextView) findViewById(R.id.textView3);
            TextView textN = (TextView) findViewById(R.id.textView4);
            ImageView image = (ImageView) findViewById(R.id.imageView);
            text.setText(setters.get(0).headlines);
            textD.setText(setters.get(0).description);
            textP.setText(setters.get(0).published);
            textN.setText(1 + "/" + setters.size());
            for (int m = 0; m < 1000; m++) {
            }
            new connectionImage().execute(setters.get(0).imageurl);

        }

        @Override
        protected ArrayList<setter> doInBackground(String... string) {

            if (isNetworkAvailable()) {
                try {
                    URL url = new URL(string[0]);
                    Log.d("urlss", String.valueOf(url));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    String tags = IOUtils.toString(connection.getInputStream(), "UTF-8");

                    connection.disconnect();
                    String title1 = null;
                    Bitmap myBitmap = null;
                    String imageurl = null;
                    JSONObject urlName = new JSONObject(tags);
                    JSONArray article = urlName.getJSONArray("articles");
                    for (int i = 0; i < article.length(); i++) {
                        JSONObject headlines = article.getJSONObject(i);
                        title1 = headlines.getString("title");
                        imageurl = headlines.getString("urlToImage");
                        String description = headlines.getString("description");
                        String published = headlines.getString("publishedAt");
                        setter obj = new setter(title1, imageurl, description, published);
                        objects1.add(obj);

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return objects1;
        }

    }


    public class connectionImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView im = (ImageView) findViewById(R.id.imageView);
            im.setImageBitmap(bitmap);
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream input1 = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input1);
                myBitmap = Bitmap.createScaledBitmap(myBitmap, 800, 700, true);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}







