package com.utili.aarzee.logoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WikiActivity extends AppCompatActivity {

    String wiki_links;
    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

//        mAdView = (AdView) findViewById(R.id.wikiAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//        mAdView.loadAd(adRequest);

        Intent ine = getIntent();
        wiki_links = ine.getStringExtra("wikiLink");
        WebView wikiPage = (WebView)findViewById(R.id.quiz_wiki_web);
        wikiPage.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = wikiPage.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        wikiPage.loadUrl(wiki_links);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
