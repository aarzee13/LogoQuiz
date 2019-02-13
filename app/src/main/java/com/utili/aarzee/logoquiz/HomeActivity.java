package com.utili.aarzee.logoquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button start;
    Button options;
    ConstraintLayout constraint;
    TextView title;
    SqliteController sqlt;
    boolean set = false;
    ConstraintSet constraintSet =  new ConstraintSet();
    final Handler handler = new Handler();
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    Integer backButtonCount = 0;
    ImageView fbShare;
    ImageView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        pref = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this.getApplication().getApplicationContext());
        prefEditor = pref.edit();
        if(pref.getInt("hint_value",-1) < 0) {
            prefEditor.remove("hint_value");
            prefEditor.putInt("hint_value", 50);
            prefEditor.apply();
        }

        sqlt = new SqliteController(getApplicationContext());
        //to check database existence
        if (!sqlt.checkDataBase()) {
            try {
                sqlt.createDataBase();
                // sqlt.importDBFromSdCard();
                sqlt.importDBFromAsset();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        constraint = findViewById(R.id.constraint);
        start = findViewById(R.id.home_start);
        options = findViewById(R.id.home_options);

        fbShare = findViewById(R.id.home_fb_share);
        twitter = findViewById(R.id.home_twitter_share);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideComponents();
            }
        },500);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();

            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
            }
        });

        fbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookShare();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterShare();
            }
        });


//        title = findViewById(R.id.home_title);
//        title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(set)
//                    hideComponents();
//                else
//                    showComponents();
//            }
//        });
    }

    private void showList() {
        Intent listI = new Intent(HomeActivity.this,GroupActivity.class);
        startActivity(listI);
    }

    private void showOptions() {
        Intent optionsI = new Intent(HomeActivity.this,OptionsActivity.class);
        startActivity(optionsI);
    }



//    private void showComponents(){
//        set = true;
//
//        constraintSet.clone(this, R.layout.activity_home);
//
//        Transition transition = new ChangeBounds();
//        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
//        transition.setDuration(1000);
//
//        TransitionManager.beginDelayedTransition(constraint, transition);
//        constraintSet.applyTo(constraint);
//    }

    private void hideComponents(){
        //set = false;

        constraintSet.clone(this, R.layout.activity_home_zoom);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(1000);


        TransitionManager.beginDelayedTransition(constraint, transition);
        constraintSet.applyTo(constraint);
    }

    public void facebookShare(){
        String urlToShare = "https://play.google.com/store/apps/details?id=com.aarzee.game.hangman";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

// See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

// As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    public void twitterShare(){
        String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
                + "https://play.google.com/store/apps/details?id=com.aarzee.game.hangman";
        Uri uri = Uri.parse(tweetUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(backButtonCount >= 1)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                backButtonCount++;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
