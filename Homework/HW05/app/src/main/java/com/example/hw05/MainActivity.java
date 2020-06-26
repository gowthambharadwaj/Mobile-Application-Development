package com.example.hw05;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
Assignment #HW5
File name: Group#18_HW05
Submitted by Rajath Anand and Gowtham Bharadwaj
*/

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ProgressBar pb;
    ArrayList<Source> data = new ArrayList<Source>();
    ArrayList<NewsData> dataFromNews = new ArrayList<NewsData>();
    String name;

    public static final String DATA_KEY = "data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main Activity");
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lisView);
        pb = findViewById(R.id.progressBar);
        //pb.setVisibility(View.INVISIBLE);
        if(isConnected()){
            new getDataAsync().execute("https://newsapi.org/v2/sources?apiKey=5da9e082486647b2a8bd8a16dcdd1f11");

        }
        else{
            Log.d("demo","No Internet Connection");
        }


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

    private class getDataAsync extends AsyncTask<String, Void, ArrayList<Source>> {

        AlertDialog.Builder builder;
        AlertDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater inflater = MainActivity.this.getLayoutInflater();

            builder.setTitle("Loading Sources..").setView(inflater.inflate(R.layout.progress_bar,null));

            dialog = builder.create();

            dialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<Source> sources) {
            super.onPostExecute(sources);

            final ArrayAdapter<Source> adapter = new ArrayAdapter<Source>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,sources);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String key = sources.get(i).id;
                    name = sources.get(i).name;
                    new DisplayDataAsync().execute("https://newsapi.org/v2/top-headlines?sources="+key+"&apiKey=5da9e082486647b2a8bd8a16dcdd1f11");

                }
            });
        }

        @Override
        protected ArrayList<Source> doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray newsData = jsonObject.getJSONArray("sources");
                    for(int i=0;i<newsData.length();i++){
                        Source source = new Source();
                        JSONObject jobj = newsData.getJSONObject(i);
                        source.name = jobj.getString("name");
                        source.id = jobj.getString("id");
                        data.add(source);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }
    }

    private class DisplayDataAsync extends AsyncTask<String,Void,ArrayList<NewsData>>{

        AlertDialog.Builder builder;
        AlertDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater inflater = MainActivity.this.getLayoutInflater();

            builder.setTitle("Loading Stories..").setView(inflater.inflate(R.layout.progress_bar,null));

            dialog = builder.create();

            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<NewsData> incomingData) {
            super.onPostExecute(incomingData);
            dialog.dismiss();
            NewsData nw[] = new NewsData[incomingData.size()];
            for(int i=0;i<incomingData.size();i++){
                nw[i] = new NewsData();
                nw[i].setUrlToImage(incomingData.get(i).urlToImage);
                nw[i].setTitle(incomingData.get(i).title);
                nw[i].setPublishedAt(incomingData.get(i).publishedAt);
                nw[i].setAuthor(incomingData.get(i).author);
                nw[i].setUrlWebView(incomingData.get(i).urlWebView);
                nw[i].setName(name);
            }
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),NewsActivity.class);
            Bundle dataToBeSent = new Bundle();
            dataToBeSent.putSerializable("send",nw);
            dataToBeSent.putString("name_key",name);
            intent.putExtra(DATA_KEY,dataToBeSent);
            incomingData.clear();
            startActivity(intent);
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray dataArray = jsonObject.getJSONArray("articles");
                    for(int i=0;i<dataArray.length();i++){
                        NewsData nd = new NewsData();
                        JSONObject jobj = dataArray.getJSONObject(i);
                        nd.author = jobj.getString("author");
                        nd.publishedAt = jobj.getString("publishedAt");
                        nd.title = jobj.getString("title");
                        nd.urlToImage = jobj.getString("urlToImage");
                        nd.urlWebView = jobj.getString("url");
                        dataFromNews.add(nd);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return dataFromNews;
        }
    }
}
