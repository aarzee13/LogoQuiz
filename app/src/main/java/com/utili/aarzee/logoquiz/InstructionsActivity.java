package com.utili.aarzee.logoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class InstructionsActivity extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_instructions);

        //MobileAds.initialize(this,"ca-app-pub-5858135794717325~9434563129");
        mAdView = (AdView) findViewById(R.id.instructionsAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();

        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
        mAdView.loadAd(adRequest);
    }
}
