package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle("Web View");

        webView = findViewById(R.id.webView);

        String url = getIntent().getExtras().getString(NewsActivity.WEB_KEY);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        Log.d("demo","URL in webview: "+url);

        //webView.loadUrl();
    }
}
