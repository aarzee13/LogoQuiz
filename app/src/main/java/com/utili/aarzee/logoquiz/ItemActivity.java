package com.utili.aarzee.logoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    Context detailContext;
    RecyclerView detailRecyclerView;
    RecyclerView.LayoutManager detailRecyclerViewLayoutManager;
    ItemAdapter detailAdapter;
    ArrayList<Item_Model> item_list;
    SqliteController sqlt;
    String grpFilter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_item);

        //MobileAds.initialize(this,"ca-app-pub-5858135794717325~9434563129");
        mAdView = (AdView) findViewById(R.id.itemAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();

        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
        mAdView.loadAd(adRequest);

        Intent i = getIntent();
        grpFilter = i.getStringExtra("grpFilter");

        sqlt = new SqliteController(getApplicationContext());
        item_list = sqlt.getItem(grpFilter);

        detailContext = getApplicationContext();
        detailRecyclerView = (RecyclerView) findViewById(R.id.detail_recyclerview);
        detailRecyclerViewLayoutManager = new GridLayoutManager(detailContext,3);
        detailRecyclerView.setLayoutManager(detailRecyclerViewLayoutManager);
        detailAdapter = new ItemAdapter(detailContext,item_list);
        detailRecyclerView.setAdapter(detailAdapter);

        detailRecyclerView.addOnItemTouchListener( new RecyclerItemClickListener(detailContext,detailRecyclerView, new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        String itemFilter = item_list.get(position).getItem_id().toString();
                        Intent detailI = new Intent(ItemActivity.this,QuizActivity.class);
                        detailI.putExtra("itemFilter",itemFilter);
                        startActivity(detailI);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,GroupActivity.class);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Intent mBack = new Intent(ItemActivity.this,GroupActivity.class);
                startActivity(mBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
