package com.utili.aarzee.logoquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {

    Button new_game;
    Button instructions;
    //AdView mAdView;
    SqliteController sqlt;
    ArrayList<Item_Model> all_list;
    ArrayList<Item_Model> new_game_list;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options);
        sqlt = new SqliteController(getApplicationContext());

//        mAdView = (AdView) findViewById(R.id.optionsAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//        mAdView.loadAd(adRequest);

        new_game = (Button) findViewById(R.id.options_new_game);
        instructions = (Button) findViewById(R.id.options_instructions);

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });


        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstructions();
            }
        });
    }

    public void startNewGame(){
        new AlertDialog.Builder(this)
                .setTitle("Reset Game")
                .setMessage("Do you really want to Start New Game? All your guesses and progress will be lost.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        all_list = sqlt.getAllItem();
                        sqlt.resetAllData(all_list);
                        Toast.makeText(OptionsActivity.this, "All Data Reset. Start new game.", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void showInstructions(){
        final LayoutInflater li = (LayoutInflater)OptionsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View promptsView = li.inflate(R.layout.activity_instructions, null);

//        mAdView = (AdView) promptsView.findViewById(R.id.instructionsAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//        mAdView.loadAd(adRequest);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                promptsView.getContext(),android.R.style.Theme_Black_NoTitleBar);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setPositiveButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*1.0f);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*1.0f);

        // show it
        alertDialog.show();
        alertDialog.getWindow().setLayout(width,height);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,HomeActivity.class);
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
                Intent mAdd = new Intent(OptionsActivity.this,HomeActivity.class);
                startActivity(mAdd);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
