package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    public static final String WEB_KEY = "web";

    ArrayList<NewsData> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        Bundle dataFromMain = getIntent().getExtras().getBundle(MainActivity.DATA_KEY);

        NewsData[] nwd = (NewsData[]) dataFromMain.getSerializable("send");

        for(int i=0;i<nwd.length;i++){
            news.add(nwd[i]);
        }
        String name = getIntent().getStringExtra("name_key");
        Log.d("demo","Name: "+name);
        setTitle(news.get(0).name);

        ListView listView = findViewById(R.id.lisView);
        NewsAdapter adapter = new NewsAdapter(this,R.layout.use_list_view,news);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent webView = new Intent(NewsActivity.this,WebViewActivity.class);
                String url = news.get(position).urlWebView;
                webView.putExtra(WEB_KEY,url);

                startActivity(webView);

            }
        });

    }
}
