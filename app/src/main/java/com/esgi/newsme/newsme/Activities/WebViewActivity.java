package com.esgi.newsme.newsme.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.esgi.newsme.newsme.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        assignViews();

        setTitle(getIntent().getStringExtra("src"));

        String url = getIntent().getStringExtra("url");

        startWebView(url);
    }

    public void assignViews() {
        webView = (WebView) findViewById(R.id.webView_article);
    }

    public void show() {

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

    }



    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient());

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);


        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */


    }


/*
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    */
}
