package com.utili.aarzee.logoquiz;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    String itemFilter;
    SqliteController sqlt;
    ArrayList<Item_Model> item_detail;
    ArrayList<Item_Model> item_details;
    ArrayList<Item_Model> item_detail_for_message;
    ArrayList<Item_Model> item_next;
    ImageView quizImage;
    ImageView quizImage1;
    ImageView quizImage2;
    ImageView quizImage3;
    ImageView quizImage4;
    ImageView helpHint;
    ImageView helpAddOne;
    ImageView helpErase;
    ImageView helpSolve;
    ImageView learn;
    ImageView restart;
    ImageView quizPrevious;
    ImageView quizNext;
    Context quizContext;
    RecyclerView optionRecyclerView;
    RecyclerView answerRecyclerView;
    RecyclerView.LayoutManager optionRecyclerViewLayoutManager;
    RecyclerView.LayoutManager answerRecyclerViewLayoutManager;
    QuizOptionAdapter optionAdapter;
    QuizAnswerAdapter answerAdapter;
    List<String> optionList;
    List<String> answerList;
    List<String> answerCheck;
    List<String> emptyAnswerList;
    List<String> answerCheckFinal;
    List<String> emptyAnswerListFinal;
    AlertDialog alertDialog;
    String reference_try;
    String reference_options;
    int chooseNo= 0;
    int clickedPosition;
    int clickedPositions = 0;
    int image2_status;
    int image3_status;
    int image4_status;
    String imageResult;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    private AdView mAdView;
    InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);

        //MobileAds.initialize(this,"ca-app-pub-5858135794717325~9434563129");
        mAdView = (AdView) findViewById(R.id.quizAdView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();

        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
        mAdView.loadAd(adRequest);


        pref = PreferenceManager.getDefaultSharedPreferences(QuizActivity.this.getApplication().getApplicationContext());
        prefEditor = pref.edit();
        setToolbarTitle();
        //int k = pref.getInt("hint_value",-1);

        Intent i = getIntent();
        itemFilter = i.getStringExtra("itemFilter");

        quizContext = getApplicationContext();
        sqlt = new SqliteController(getApplicationContext());
        item_detail = sqlt.getQuiz(itemFilter);



        quizImage = findViewById(R.id.main_image);
        quizImage1 = (ImageView) findViewById(R.id.quiz_image1);
        quizImage2 = (ImageView) findViewById(R.id.quiz_image2);
        quizImage3 = (ImageView) findViewById(R.id.quiz_image3);
        quizImage4 = (ImageView) findViewById(R.id.quiz_image4);
        helpHint = findViewById(R.id.help_hint);
        helpAddOne = findViewById(R.id.help_add_one);
        helpErase = findViewById(R.id.help_erase);
        helpSolve = findViewById(R.id.help_solve);
        learn = findViewById(R.id.learn);
        restart = findViewById(R.id.restart);
        quizPrevious = findViewById(R.id.quiz_previous);
        quizNext = findViewById(R.id.quiz_next);


        if("success".equals(item_detail.get(0).getResult())){
            helpHint.setVisibility(View.INVISIBLE);
            helpAddOne.setVisibility(View.INVISIBLE);
            helpErase.setVisibility(View.INVISIBLE);
            helpSolve.setVisibility(View.INVISIBLE);

        }

        Integer hangNo = item_detail.get(0).getHang_no();
        //setHangImage(hangNo);
        final String imageResult = item_detail.get(0).getResult();
        image2_status = item_detail.get(0).getImage2_status();
        image3_status = item_detail.get(0).getImage3_status();
        image4_status = item_detail.get(0).getImage4_status();
        final String imageName = item_detail.get(0).getItem_name().replace(' ','_').replace("&","aanndd");
        final String imageNameFiltered = item_detail.get(0).getItem_name_filtered().replace(' ','_').replace("&","aanndd");
//        final String imageName1 = item_detail.get(0).getItem_name().replace(' ','_')+"1";
//        final String imageName2 = item_detail.get(0).getItem_name().replace(' ','_')+"2";
//        final String imageName3 = item_detail.get(0).getItem_name().replace(' ','_')+"3";
//        final String imageName4 = item_detail.get(0).getItem_name().replace(' ','_')+"4";
        String randomOption = item_detail.get(0).getOption();
        String correctTry = item_detail.get(0).getCorrect_try();
        reference_try = item_detail.get(0).getReference_try();
        reference_options = item_detail.get(0).getReference_options();


        String[] imageNameArray = imageNameFiltered.replace('_',' ').replace("aanndd","&").split("");
        String[] imageOptionArray = randomOption.split("");
        String[] correctTryArray = correctTry.split("");

        optionList = new ArrayList<String>(Arrays.asList(imageOptionArray));
        //Collections.shuffle(optionList);
        optionList.removeAll(Arrays.asList(""));

        answerList = new ArrayList<String>(Arrays.asList(imageNameArray));
        answerList.removeAll(Arrays.asList(""));
        answerCheck = new ArrayList<String>(Arrays.asList(imageNameArray));
        answerCheck.removeAll(Arrays.asList(""));
        emptyAnswerList = new ArrayList<String>(Arrays.asList(correctTryArray));
        emptyAnswerList.removeAll(Arrays.asList(""));

        for(String s : emptyAnswerList){
            if(s.matches("^[a-zA-Z&]$")){
                chooseNo += 1;
            }
        }


//        int d = quizContext.getResources().getIdentifier(imageName,"drawable",quizContext.getPackageName());
//        quizImage1.setImageResource(d);
        int dm = quizContext.getResources().getIdentifier(imageName,"drawable",quizContext.getPackageName());
        quizImage.setImageResource(dm);
        quizImage1.setVisibility(View.INVISIBLE);
//        int d1 = quizContext.getResources().getIdentifier(imageName1,"drawable",quizContext.getPackageName());
//        quizImage1.setImageResource(d1);
        //final int d2 = quizContext.getResources().getIdentifier(imageName2,"drawable",quizContext.getPackageName());
        if(image2_status == 1) {
            quizImage2.setVisibility(View.INVISIBLE);
            //quizImage2.setImageResource(d2);
        }
        //final int d3 = quizContext.getResources().getIdentifier(imageName3,"drawable",quizContext.getPackageName());
        if(image3_status == 1) {
            quizImage3.setVisibility(View.INVISIBLE);
            //quizImage3.setImageResource(d3);
        }
        //final int d4 = quizContext.getResources().getIdentifier(imageName4,"drawable",quizContext.getPackageName());
            if(image4_status == 1) {
                quizImage4.setVisibility(View.INVISIBLE);
                //quizImage4.setImageResource(d4);
            }

        optionRecyclerView = (RecyclerView) findViewById(R.id.option_recyclerview);


        optionRecyclerViewLayoutManager = new GridLayoutManager(quizContext,7);

        optionRecyclerView.setLayoutManager(optionRecyclerViewLayoutManager);
        optionAdapter = new QuizOptionAdapter(quizContext,optionList);
        optionRecyclerView.setAdapter(optionAdapter);


        answerRecyclerView = (RecyclerView) findViewById(R.id.answer_recyclerview);
        answerRecyclerViewLayoutManager = new GridLayoutManager(quizContext,7);
        answerRecyclerView.setLayoutManager(answerRecyclerViewLayoutManager);
        answerAdapter = new QuizAnswerAdapter(quizContext,emptyAnswerList);
        answerRecyclerView.setAdapter(answerAdapter);

        answerRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(quizContext, answerRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(!"success".equals(imageResult)){
                        String itemFilt = emptyAnswerList.get(position);
                        if(itemFilt.matches("^[a-zA-Z&]*$")) {
                        clickedPositions = 0;
                        for(String s : optionList) {
                            if ("1".equals(s)) {
                                clickedPosition = clickedPositions;
                                break;
                            }
                            clickedPositions++;
                        }
                        //if(!"".equals(itemFilt)) {
                            //if (emptyAnswerList.size() > chooseNo) {

                            optionList.set(clickedPositions, itemFilt);
                        String remOption = "";
                        for (String s : optionList) {
                            remOption += s;
                        }
                            if(position+1 >=10){
                                emptyAnswerList.set(position, Integer.toString((position+1)%10));
                            }
                            else{
                                emptyAnswerList.set(position, Integer.toString(position + 1));
                            }
                        String ansList = "";
                        for (String s : emptyAnswerList) {
                            ansList += s;
                        }
                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
                                optionRecyclerView.setAdapter(optionAdapter);
                                answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
                                answerRecyclerView.setAdapter(answerAdapter);
                        sqlt.updateCorrectTry(itemFilter, ansList);
                        sqlt.updateOption(itemFilter, remOption);
                        chooseNo -=1;
                                //chooseNo += 1;
                            }

                        }
                        }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        optionRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(quizContext, optionRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        String itemFilt = optionList.get(position);
                        if(!"".equals(itemFilt)) {
                            emptyAnswerListFinal = new ArrayList<String>(emptyAnswerList);
                            emptyAnswerListFinal.removeAll(Arrays.asList(" ",null));
                            if(emptyAnswerListFinal.size() > chooseNo) {
                                clickedPositions = 0;
                                for(String s : emptyAnswerList){
                                    if(s.matches("^[0-9]$")){
                                        clickedPosition = clickedPositions;
                                        break;
                                    }
                                    clickedPositions++;
                                }

                                emptyAnswerList.set(clickedPosition, itemFilt);
                                String ansList = "";
                                for (String s : emptyAnswerList) {
                                    ansList += s;
                                }
                                optionList.set(position, "1");
                                String remOption = "";
                                for (String s : optionList) {
                                    remOption += s;
                                }
                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
                                optionRecyclerView.setAdapter(optionAdapter);
                                answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
                                answerRecyclerView.setAdapter(answerAdapter);
                                chooseNo += 1;
                                sqlt.updateCorrectTry(itemFilter, ansList);
                                sqlt.updateOption(itemFilter, remOption);
                                // insert success in database if emptyAnswerList = answerList
                                    if (answerCheck.equals(emptyAnswerList)) {
                                        item_detail_for_message = sqlt.getQuiz(itemFilter);
                                        int bonus_hint=0;
                                        //if (answerCheck.containsAll(emptyAnswerList) && emptyAnswerList.containsAll(answerCheck)) {
                                        //here update database
                                        if(item_detail_for_message.get(0).getImage2_status() == 0 && item_detail_for_message.get(0).getImage3_status() == 0 && item_detail_for_message.get(0).getImage4_status() == 0){
                                            bonus_hint = 4;
                                            Toast.makeText(quizContext, "Genuis !", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(item_detail_for_message.get(0).getImage3_status() == 0 && item_detail_for_message.get(0).getImage4_status() == 0){
                                            bonus_hint = 3;
                                            Toast.makeText(quizContext, "Excellent !", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(item_detail_for_message.get(0).getImage4_status() == 0){
                                            bonus_hint = 2;
                                            Toast.makeText(quizContext, "Good !", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            bonus_hint = 1;
                                            Toast.makeText(quizContext, "Ok !", Toast.LENGTH_SHORT).show();
                                        }
                                        prefEditor.remove("hint_value");
                                        prefEditor.putInt("hint_value", pref.getInt("hint_value",-1)+bonus_hint);
                                        prefEditor.apply();

                                        sqlt.updateResult(itemFilter, "success");
                                        sqlt.updateOption(itemFilter, "");
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                    answerCheckFinal = new ArrayList<String>(answerCheck);
                                    answerCheckFinal.removeAll(Arrays.asList(" ",null));
                                    if(chooseNo == answerCheckFinal.size() && !answerCheck.equals(emptyAnswerList)) {
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        v.vibrate(500);
                                        Toast.makeText(quizContext, "Sorry Wrong Answer !", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        quizImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage(imageName,imageResult);
            }
        });

//        quizImage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showImage1(imageName1,imageResult);
//            }
//        });

        quizImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showImage2(imageName,d2,imageResult);
                showImage2(imageName,imageResult);
            }
        });

        quizImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showImage3(imageName,d3,imageResult);
                showImage3(imageName,imageResult);
            }
        });

        quizImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showImage4(imageName,d4,imageResult);
                showImage4(imageName,imageResult);
            }
        });

        helpHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHintMessage("hint");
            }
        });

        helpAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHintMessage("add_one");
            }
        });

        helpErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHintMessage("erase");
            }
        });

        helpSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHintMessage("solve");
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartData();
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLearn();
            }
        });

        quizPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrevious();
            }
        });

        quizNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNext();
            }
        });

//        showSuccessFailImage();
        showSuccessImage();
    }



    private void showPrevious() {
        item_detail = sqlt.getQuiz(itemFilter);
        item_next = sqlt.getPreviousQuiz(item_detail.get(0).grp_id,item_detail.get(0).id);
        if(item_next.size() <= 0){
            Toast.makeText(quizContext,"No logo to solve previous to it. Check next !",Toast.LENGTH_SHORT).show();
        }
        else {
            String itemFilter = item_next.get(0).item_id;
            Intent detailI = new Intent(QuizActivity.this, QuizActivity.class);
            detailI.putExtra("itemFilter", itemFilter);
            startActivity(detailI);
        }

    }

    private void showNext() {
        item_detail = sqlt.getQuiz(itemFilter);
        item_next = sqlt.getNextQuiz(item_detail.get(0).grp_id,item_detail.get(0).id);
        if(item_next.size() <= 0){
            Toast.makeText(quizContext,"No logo to solve next to it. Check previous !",Toast.LENGTH_SHORT).show();
        }
        else {
            String itemFilter = item_next.get(0).item_id;
            Intent detailI = new Intent(QuizActivity.this, QuizActivity.class);
            detailI.putExtra("itemFilter", itemFilter);
            startActivity(detailI);
        }
    }


    private void showHelpHint() {
        int k = pref.getInt("hint_value",-1);
        k = k-1;
        if(k>=0) {
            prefEditor.remove("hint_value");
            prefEditor.putInt("hint_value", k);
            prefEditor.apply();
        showHintMessage("hint_answer");
            setToolbarTitle();
        }
        else{
            Toast.makeText(quizContext,"not enough hint",Toast.LENGTH_SHORT).show();
        }
    }

    private void showHelpAddOne() {
        int k = pref.getInt("hint_value",-1);
        k = k-1;
        if(k>=0) {

            List<Integer> remPosition = new ArrayList<>();
            int pos1 = 0;
            for(String s : emptyAnswerList){
                if(s.matches("^[0-9]$")){
                    remPosition.add(pos1);
                }
                pos1++;
            }
            Random rand = new Random();
            int ranNumber = rand.nextInt(remPosition.size());
            int ranElement = remPosition.get(ranNumber);
            int remPosSize = remPosition.size();

//            if(remPosition.size()>1){
//                if(!optionList.contains(answerList.get(ranElement))){
//                    remPosition.remove(ranNumber);
//                    ranNumber = rand.nextInt(remPosition.size());
//                    ranElement = remPosition.get(ranNumber);
//                }
//            }
            if(remPosition.size()>1) {
                do {
                    if (!optionList.contains(answerList.get(ranElement))) {
                        remPosition.remove(ranNumber);
                        ranNumber = rand.nextInt(remPosition.size());
                        ranElement = remPosition.get(ranNumber);

                    }
                    remPosSize--;
                } while (remPosSize > 1);
            }


            if(optionList.contains(answerList.get(ranElement))){




            emptyAnswerList.set(ranElement, answerList.get(ranElement));
            String ansList = "";
            for (String s : emptyAnswerList) {
                ansList += s;
            }

            int matchedPostition = 0;
            for (String s : optionList) {
                if(s.matches(answerList.get(ranElement))){
                    break;
                }
                matchedPostition = matchedPostition +1;
            }


            optionList.set(matchedPostition, "1");
            String remOption = "";
            for (String s : optionList) {
                remOption += s;
            }
            optionAdapter = new QuizOptionAdapter(quizContext, optionList);
            optionRecyclerView.setAdapter(optionAdapter);
            answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
            answerRecyclerView.setAdapter(answerAdapter);
            chooseNo += 1;
            sqlt.updateCorrectTry(itemFilter, ansList);
            sqlt.updateOption(itemFilter, remOption);
            // insert success in database if emptyAnswerList = answerList
            if (answerCheck.equals(emptyAnswerList)) {
                //if (answerCheck.containsAll(emptyAnswerList) && emptyAnswerList.containsAll(answerCheck)) {
                //here update database
                Toast.makeText(quizContext, "Excellent !", Toast.LENGTH_SHORT).show();
                sqlt.updateResult(itemFilter, "success");
                sqlt.updateOption(itemFilter, "");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            if(remPosition.size() == 1 && !answerCheck.equals(emptyAnswerList)) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                Toast.makeText(quizContext, "Sorry Wrong Answer !", Toast.LENGTH_SHORT).show();
            }
                prefEditor.remove("hint_value");
                prefEditor.putInt("hint_value", k);
                prefEditor.apply();
            }
            else
            {
                Toast.makeText(quizContext,"no matching found",Toast.LENGTH_SHORT).show();
            }
            setToolbarTitle();


            //showHintMessage("hint_answer");
        }
        else{
            Toast.makeText(quizContext,"not enough hint",Toast.LENGTH_SHORT).show();
        }

    }

    private void showHelpErase() {
        int k = pref.getInt("hint_value",-1);
        k = k-1;
        if(k>=0) {
            prefEditor.remove("hint_value");
            prefEditor.putInt("hint_value", k);
            prefEditor.apply();
            item_detail = sqlt.getQuiz(itemFilter);
        String opt = item_detail.get(0).getItem_name().replace(" ","");
        String triedOption = item_detail.get(0).getReference_try();
        List<Character> characters = new ArrayList<Character>();
        for(char c:opt.toCharArray()){
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder sb = new StringBuilder(); //now rebuild the word
        for(char c : characters)
            sb.append(c);

        sqlt.updateOption(itemFilter,sb.toString());
        sqlt.updateCorrectTry(itemFilter,triedOption);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            setToolbarTitle();
        }
        else{
            Toast.makeText(quizContext,"not enough hint",Toast.LENGTH_SHORT).show();
        }

    }

    private void showHelpSolve() {
        int k = pref.getInt("hint_value",-1);
        k = k-5;
        if(k>=0) {
            prefEditor.remove("hint_value");
            prefEditor.putInt("hint_value", k);
            prefEditor.apply();


            Toast.makeText(quizContext, "Solved !", Toast.LENGTH_SHORT).show();
            sqlt.updateResult(itemFilter, "success");
            sqlt.updateOption(itemFilter, "");
            sqlt.updateCorrectTry(itemFilter,item_detail.get(0).getItem_name());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            setToolbarTitle();

            //showHintMessage("hint_answer");
        }
        else{
            Toast.makeText(quizContext,"not enough hint",Toast.LENGTH_SHORT).show();
        }

    }

    private void showHintMessage(String hintType){
        loadInterstitialHintAd();
        final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View promptsView = li.inflate(R.layout.activity_hint_message, null);
        final String hType = hintType;
        TextView hTitle = promptsView.findViewById(R.id.hintTitle);
        TextView hMessage = promptsView.findViewById(R.id.hintMessage);
        TextView hCost = promptsView.findViewById(R.id.hintCost);
        if("hint".equals(hType)){
            hTitle.setText("Use Hint");
            hMessage.setText("Show logo category.");
            hCost.setText("Cost : 1 hint");
        }
        else if("add_one".equals(hType)){
            hTitle.setText("Use Hint");
            hMessage.setText("Show one letter of answer.");
            hCost.setText("Cost : 1 hint");
        }
        else if("erase".equals(hType)){
            hTitle.setText("Use Hint");
            hMessage.setText("Remove letters which are not part of the answer.");
            hCost.setText("Cost : 1 hint");
        }
        else if("solve".equals(hType)){
            hTitle.setText("Use Hint");
            hMessage.setText("Solve Puzzle.");
            hCost.setText("Cost : 5 hints");
        }
        else if("solve".equals(hType)){
            hTitle.setText("Use Hint");
            hMessage.setText("Solve Puzzle.");
            hCost.setText("Cost : 5 hints");
        }
        else if("hint_answer".equals(hType)){
            hTitle.setText("Category");
            hMessage.setText("The brand belongs to category.");
            hCost.setText(item_detail.get(0).hint1);
        }


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
//        alertDialogBuilder.setTitle("dddd");
//        alertDialogBuilder.setIcon(R.drawable.i);

        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if("hint".equals(hType)){
                            showHelpHint();
                        }
                        else if("add_one".equals(hType)){
                            showHelpAddOne();
                        }
                        else if("erase".equals(hType)){
                            showHelpErase();
                        }
                        else if("solve".equals(hType)){
                            showHelpSolve();
                        }
                    }
                });
        if(!"hint_answer".equals(hType)) {
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
        }
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9f);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.5f);

        // show it
        alertDialog.show();
        alertDialog.getWindow().setLayout(width,height);
    }

    private void showImage(String imageName, String imageResult) {
        item_detail = sqlt.getQuiz(itemFilter);
        if("success".equals(imageResult) ||(item_detail.get(0).image2_status == 1 && item_detail.get(0).image3_status == 1 && item_detail.get(0).image4_status == 1)) {
            zoomImage(imageName+"_ans");
        }
        else{
            Toast.makeText(quizContext,"First open all hidden part of image.",Toast.LENGTH_SHORT).show();
        }
    }

    private void showImage1(String image1,String imageResult) {
        zoomImage(image1);
    }

    private void showImage2(String image2,String imageResult) {
        item_detail = sqlt.getQuiz(itemFilter);
        image2_status = item_detail.get(0).getImage2_status();
        if(image2_status == 0 && "fail".equals(imageResult)) {
                quizImage2.setRotationY(0f);
                quizImage2.animate().rotationY(90f).setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //iv.setImageDrawable(drawable);
                        //quizImage2.setImageResource(d2);
                        quizImage2.getResources().getDrawable(R.drawable.blank);
                        quizImage2.setRotationY(270f);
                        quizImage2.animate().rotationY(360f).setListener(null);
                        quizImage2.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });

                sqlt.updateImageOption(itemFilter, "image2_status");

        }
        else{
            zoomImage(image2+"_ans");
        }
    }

    private void showImage3(String image3,String imageResult) {
        item_detail = sqlt.getQuiz(itemFilter);
        image3_status = item_detail.get(0).getImage3_status();
        if(image3_status == 0 && "fail".equals(imageResult)) {
            quizImage3.setRotationY(0f);
            quizImage3.animate().rotationY(90f).setListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //iv.setImageDrawable(drawable);
                    //quizImage3.setImageResource(d3);
                    quizImage3.getResources().getDrawable(R.drawable.blank);
                    quizImage3.setRotationY(270f);
                    quizImage3.animate().rotationY(360f).setListener(null);
                    quizImage3.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
            });
            sqlt.updateImageOption(itemFilter,"image3_status");
        }
        else{
            zoomImage(image3+"_ans");
        }
    }

    private void showImage4(String image4,String imageResult) {
//        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.anim.card_flip);
        //Animation anim = AnimationUtils.loadAnimation(this,R.anim.swing_up_right);
//        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.card_flip);
//        anim.setTarget(quizImage4);
//        anim.setDuration(1500);
//        anim.start();
//        quizImage4.setImageResource(d3);
        //final Drawable drawable=getResources().getDrawable(R.drawable.a);
        //final ImageView iv = ((ImageView)findViewById(R.id.quiz_image4));
        item_detail = sqlt.getQuiz(itemFilter);
        image4_status = item_detail.get(0).getImage4_status();
        if(image4_status == 0 && "fail".equals(imageResult)) {
            quizImage4.setRotationY(0f);
            quizImage4.animate().rotationY(90f).setListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //iv.setImageDrawable(drawable);
//                    quizImage4.setImageResource(d4);
                    quizImage4.getResources().getDrawable(R.drawable.blank);
                    quizImage4.setRotationY(270f);
                    quizImage4.animate().rotationY(360f).setListener(null);
                    quizImage4.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
            });
            sqlt.updateImageOption(itemFilter,"image4_status");
        }
        else{
            zoomImage(image4+"_ans");
        }
    }



    public void zoomImage(String iName){
        //String imageName = item_detail.get(0).getItem_name().replace(' ','_');
        String imageName = iName;
        String imageResult = item_detail.get(0).getResult();
        final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View promptsView = li.inflate(R.layout.activity_zoom, null);


        int zd = quizContext.getResources().getIdentifier(imageName,"drawable",quizContext.getPackageName());

        ImageView iv = (ImageView)promptsView.findViewById(R.id.zoom_image);
        iv.setImageResource(zd);
        if("success".equals(imageResult)) {
            TextView tv = (TextView) promptsView.findViewById(R.id.zoom_text);
            TextView zoom_mesage = promptsView.findViewById(R.id.zoom_message);
            Button continue_button = promptsView.findViewById(R.id.continue_quiz);

            tv.setText(item_detail.get(0).getProper_name().toString());
            zoom_mesage.setText("Excellent");
            continue_button.setVisibility(View.VISIBLE);
            continue_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item_next = sqlt.getNextQuiz(item_detail.get(0).grp_id,item_detail.get(0).id);
                    if(item_next.size() <= 0){
                        Toast.makeText(quizContext,"All logos of this group is solved. Go to another group !",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String itemFilter = item_next.get(0).item_id;
                        Intent detailI = new Intent(QuizActivity.this, QuizActivity.class);
                        detailI.putExtra("itemFilter", itemFilter);
                        startActivity(detailI);
                    }
                }
            });
        }


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                promptsView.getContext());

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
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7f);

        // show it
        alertDialog.show();
        alertDialog.getWindow().setLayout(width,height);
    }


//    public void setHangImage(Integer hangNos){
//        switch(hangNos){
//            case 0:
//                hangImage.setImageResource(R.drawable.hang1);
//                break;
//            case 1:
//                hangImage.setImageResource(R.drawable.hang2);
//                break;
//            case 2:
//                hangImage.setImageResource(R.drawable.hang3);
//                break;
//            case 3:
//                hangImage.setImageResource(R.drawable.hang4);
//                break;
//            case 4:
//                hangImage.setImageResource(R.drawable.hang5);
//                break;
//            case 5:
//                hangImage.setImageResource(R.drawable.hang6);
//                break;
//            case 6:
//                hangImage.setImageResource(R.drawable.hang7);
//                break;
//            case 7:
//                hangImage.setImageResource(R.drawable.hang8);
//                sqlt.updateResult(itemFilter,"hanged");
//                showSuccessFailImage();
//                break;
//        }
//    }

//    public void showHint1(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        if (activeNetworkInfo == null) {
//            Intent noInternet = new Intent(this, NoInternetActivity.class);
//            startActivity(noInternet);
//        }
//        else{
////load interstitial ads
//            loadInterstitialHintAd();
//
//
//            final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View promptsView = li.inflate(R.layout.activity_hint1, null);
//
//            mAdView = (AdView) promptsView.findViewById(R.id.hint1AdView);
//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//            mAdView.loadAd(adRequest);
//            //int zd = getResources().getIdentifier(imageFilter,"drawable",getPackageName());
//
//            //ImageView iv = (ImageView)promptsView.findViewById(R.id.gallery_zoom);
//            TextView tv = (TextView)promptsView.findViewById(R.id.hint1_text);
//            //iv.setImageResource(zd);
//            String hint1 = item_detail.get(0).getHint1();
//            tv.setText(hint1);
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    promptsView.getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//
//            // set prompts.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//
//            alertDialogBuilder.setPositiveButton("Close",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            alertDialog.dismiss();
//                        }
//                    });
//
//            alertDialog = alertDialogBuilder.create();
//            int width = (int)(getResources().getDisplayMetrics().widthPixels*1.1f);
//            int height = (int)(getResources().getDisplayMetrics().heightPixels*1.1f);
//
//            // create alert dialog
//            alertDialog = alertDialogBuilder.create();
//
//            // show it
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(width,height);
//        }
//    }

//    public void showHint2(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        if (activeNetworkInfo == null) {
//            Intent noInternet = new Intent(this, NoInternetActivity.class);
//            startActivity(noInternet);
//        }
//        else{
////load interstitial ads
//            loadInterstitialHintAd();
//
//            final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View promptsView = li.inflate(R.layout.activity_hint2, null);
//
//            mAdView = (AdView) promptsView.findViewById(R.id.hint2AdView);
//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//            mAdView.loadAd(adRequest);
//            //int zd = getResources().getIdentifier(imageFilter,"drawable",getPackageName());
//
//            //ImageView iv = (ImageView)promptsView.findViewById(R.id.gallery_zoom);
//            TextView tv = (TextView)promptsView.findViewById(R.id.hint2_text);
//            //iv.setImageResource(zd);
//            String hint2 = item_detail.get(0).getHint2();
//            tv.setText(hint2);
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    promptsView.getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//
//            // set prompts.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//
//            alertDialogBuilder.setPositiveButton("Close",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            alertDialog.dismiss();
//                        }
//                    });
//
//            // create alert dialog
//            alertDialog = alertDialogBuilder.create();
//            int width = (int)(getResources().getDisplayMetrics().widthPixels*1.1f);
//            int height = (int)(getResources().getDisplayMetrics().heightPixels*1.1f);
//
//            // show it
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(width,height);
//        }
//    }

    public void showLearn(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Intent noInternet = new Intent(this, NoInternetActivity.class);
            startActivity(noInternet);
        }
        else{
            item_details = sqlt.getQuiz(itemFilter);
            if(item_details.get(0).getResult().equals("success")) {


//load interstitial ads
                loadInterstitialHintAd();


                String wiki_link = "https://en.wikipedia.org/wiki/"+item_detail.get(0).getWiki_link();

                final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View promptsView = li.inflate(R.layout.activity_wiki, null);
//                mAdView = (AdView) promptsView.findViewById(R.id.wikiAdView);
//                AdRequest adRequest = new AdRequest.Builder()
//                        .build();
//                //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//                //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//                mAdView.loadAd(adRequest);
                if(!"NULL".equals(wiki_link)) {
                    WebView wikiPage = (WebView) promptsView.findViewById(R.id.quiz_wiki_web);
                    wikiPage.setWebViewClient(new CustomWebViewClient());
                    WebSettings webSetting = wikiPage.getSettings();
                    webSetting.setJavaScriptEnabled(true);
                    webSetting.setDisplayZoomControls(true);
                    wikiPage.loadUrl(wiki_link);
                }
                else{
                    Toast.makeText(quizContext, "sorry", Toast.LENGTH_SHORT).show();
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        promptsView.getContext());

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
            else{
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                Toast.makeText(quizContext, "First Solve !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void restartData(){
//load interstitial ads
        loadInterstitialHintAd();
        sqlt.resetData(itemFilter,reference_try,reference_options);
        reloadActivity();

    }

    public void showSuccessImage(){
        item_detail = sqlt.getQuiz(itemFilter);
        if("success".equals(item_detail.get(0).getResult())){
            String imaName_ = item_detail.get(0).getItem_name().replace(' ','_');
            final String imaName = imaName_.replace("&","aanndd");
            zoomImage(imaName+"_ans");
        }
    }

    public void reloadActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,ItemActivity.class);
            a.putExtra("grpFilter",item_detail.get(0).getGrp_id());
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void loadInterstitialHintAd(){
        AdRequest admobHint = new AdRequest.Builder().addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
        //AdRequest admobHint = new AdRequest.Builder().build();
        //for admob interstitial
        interstitial = new InterstitialAd(QuizActivity.this);
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
        interstitial.loadAd(admobHint);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(interstitial.isLoaded()){
                    interstitial.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu,menu);
        return true;
    }

    private void setToolbarTitle() {
        TextView txtviewtitle;
        //toolbar =findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtviewtitle = findViewById(R.id.custom_title);
        txtviewtitle.setTextSize(18);
        txtviewtitle.setText("hints : " + pref.getInt("hint_value",-1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                item_detail = sqlt.getQuiz(itemFilter);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Intent mBack = new Intent(QuizActivity.this,ItemActivity.class);
                mBack.putExtra("grpFilter",item_detail.get(0).getGrp_id());
                startActivity(mBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
