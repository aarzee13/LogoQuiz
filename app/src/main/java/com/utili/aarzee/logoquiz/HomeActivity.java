package com.utili.aarzee.logoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    Button start;
    Button options;
    ConstraintLayout constraint;
    TextView title;
    SqliteController sqlt;
    boolean set = false;
    ConstraintSet constraintSet =  new ConstraintSet();
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideComponents();
            }
        },100);

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
        Intent listI = new Intent(this,GroupActivity.class);
        startActivity(listI);
    }

    private void showOptions() {
        Intent optionsI = new Intent(this,OptionsActivity.class);
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
        set = false;

        constraintSet.clone(this, R.layout.activity_home_zoom);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(1000);


        TransitionManager.beginDelayedTransition(constraint, transition);
        constraintSet.applyTo(constraint);
    }
}
