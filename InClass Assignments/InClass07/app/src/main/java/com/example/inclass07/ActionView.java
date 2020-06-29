package com.example.inclass07;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ActionView extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_view);

        webView = findViewById(R.id.webView);

        String url = getIntent().getExtras().getString(MainActivity.WEB_KEY);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);


    }
}
