package com.utili.aarzee.logoquiz;

import android.app.AlertDialog;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    String itemFilter;
    SqliteController sqlt;
    ArrayList<Item_Model> item_detail;
    ArrayList<Item_Model> item_details;
    ArrayList<Item_Model> item_hangNo;
    ImageView quizImage1;
    ImageView quizImage2;
    ImageView quizImage3;
    ImageView quizImage4;
//    ImageView hangImage;
//    Button quizHint1;
//    Button quizHint2;
//    Button quizLearn;
//    Button quizRefresh;
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
    AlertDialog alertDialog;
    String reference_try;
    int chooseNo= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent i = getIntent();
        itemFilter = i.getStringExtra("itemFilter");

        quizContext = getApplicationContext();
        sqlt = new SqliteController(getApplicationContext());
        item_detail = sqlt.getQuiz(itemFilter);

        quizImage1 = (ImageView) findViewById(R.id.quiz_image1);
        quizImage2 = (ImageView) findViewById(R.id.quiz_image2);
        quizImage3 = (ImageView) findViewById(R.id.quiz_image3);
        quizImage4 = (ImageView) findViewById(R.id.quiz_image4);
        //hangImage = (ImageView) findViewById(R.id.hang_image);
        //quizHint1 = (Button) findViewById(R.id.quiz_hint1);
        //quizHint2 = (Button) findViewById(R.id.quiz_hint2);
        //quizLearn = (Button) findViewById(R.id.quiz_learn);
        //quizRefresh = (Button) findViewById(R.id.quiz_refresh);

        Integer hangNo = item_detail.get(0).getHang_no();
        //setHangImage(hangNo);

        String imageName = item_detail.get(0).getItem_name().replace(' ','_');
        String imageName1 = item_detail.get(0).getItem_name().replace(' ','_')+"1";
        String imageName2 = item_detail.get(0).getItem_name().replace(' ','_')+"2";
        String imageName3 = item_detail.get(0).getItem_name().replace(' ','_')+"3";
        String imageName4 = item_detail.get(0).getItem_name().replace(' ','_')+"4";
        String randomOption = item_detail.get(0).getOption();
        String correctTry = item_detail.get(0).getCorrect_try();
        reference_try = item_detail.get(0).getReference_try();

        String[] imageNameArray = imageName.replace('_',' ').split("");
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


//        int d = quizContext.getResources().getIdentifier(imageName,"drawable",quizContext.getPackageName());
//        quizImage1.setImageResource(d);
        int d1 = quizContext.getResources().getIdentifier(imageName1,"drawable",quizContext.getPackageName());
        quizImage1.setImageResource(d1);
        int d2 = quizContext.getResources().getIdentifier(imageName2,"drawable",quizContext.getPackageName());
        quizImage2.setImageResource(d2);
        int d3 = quizContext.getResources().getIdentifier(imageName3,"drawable",quizContext.getPackageName());
        quizImage3.setImageResource(d3);
        int d4 = quizContext.getResources().getIdentifier(imageName4,"drawable",quizContext.getPackageName());
        quizImage4.setImageResource(d4);

        optionRecyclerView = (RecyclerView) findViewById(R.id.option_recyclerview);


        optionRecyclerViewLayoutManager = new GridLayoutManager(quizContext,6);

        optionRecyclerView.setLayoutManager(optionRecyclerViewLayoutManager);
        optionAdapter = new QuizOptionAdapter(quizContext,optionList);
        optionRecyclerView.setAdapter(optionAdapter);


        answerRecyclerView = (RecyclerView) findViewById(R.id.answer_recyclerview);
        answerRecyclerViewLayoutManager = new GridLayoutManager(quizContext,6);
        answerRecyclerView.setLayoutManager(answerRecyclerViewLayoutManager);
        answerAdapter = new QuizAnswerAdapter(quizContext,emptyAnswerList);
        answerRecyclerView.setAdapter(answerAdapter);

        answerRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(quizContext, answerRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String itemFilt = optionList.get(position);
                        //if(!"".equals(itemFilt)) {
                            //if (emptyAnswerList.size() > chooseNo) {

                            optionList.set(chooseNo, itemFilt);
                            emptyAnswerList.set(position, "");
                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
                                optionRecyclerView.setAdapter(optionAdapter);
                                answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
                                answerRecyclerView.setAdapter(answerAdapter);
                                //chooseNo += 1;
                            //}

                       // }
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
                            if(emptyAnswerList.size() > chooseNo) {

                                emptyAnswerList.set(chooseNo, itemFilt);
                                optionList.set(position, "");
                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
                                optionRecyclerView.setAdapter(optionAdapter);
                                answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
                                answerRecyclerView.setAdapter(answerAdapter);
                                chooseNo += 1;
                            }


//                            if (answerList.indexOf(itemFilt) < 0) {
//                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                                v.vibrate(500);
//                                Integer hNo = getCurrentHangNo();
//                                Integer hNos = hNo + 1;
//                                warningMessage(hNos);
//                                optionList.set(position, "");
//                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
//                                optionRecyclerView.setAdapter(optionAdapter);
//                                String remOption = "";
//                                for (String s : optionList) {
//                                    remOption += s;
//                                }
//                                sqlt.updateHang(itemFilter, hNos);
//                                sqlt.updateOption(itemFilter, remOption);
//                                item_detail = sqlt.getQuiz(itemFilter);
//                                //setHangImage(hNos);
//
//
//                            } else {
//                                for (String chara : answerList) {
//                                    int choosedNo = answerList.indexOf(itemFilt);
//                                    if (choosedNo >= 0) {
//                                        emptyAnswerList.set(choosedNo, itemFilt);
//                                        answerList.set(choosedNo, "");
//                                    }
//                                }
//                                optionList.set(position, "");
//                                String remOption = "";
//                                for (String s : optionList) {
//                                    remOption += s;
//                                }
//                                sqlt.updateOption(itemFilter, remOption);
//                                String ansList = "";
//                                for (String s : emptyAnswerList) {
//                                    ansList += s;
//                                }
//                                sqlt.updateCorrectTry(itemFilter, ansList);
//                                optionAdapter = new QuizOptionAdapter(quizContext, optionList);
//                                optionRecyclerView.setAdapter(optionAdapter);
//                                answerAdapter = new QuizAnswerAdapter(quizContext, emptyAnswerList);
//                                answerRecyclerView.setAdapter(answerAdapter);
//
//                                // insert success in database if emptyAnswerList = answerList
//                                if (answerCheck.containsAll(emptyAnswerList) && emptyAnswerList.containsAll(answerCheck)) {
//                                    //here update database
//                                    Toast.makeText(quizContext, "success", Toast.LENGTH_SHORT).show();
//                                    sqlt.updateResult(itemFilter, "success");
//                                    sqlt.updateOption(itemFilter, "");
//                                    Intent intent = getIntent();
//                                    finish();
//                                    startActivity(intent);
//                                }
//
//                            }
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

//        quizHint1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showHint1();
//            }
//        });
//
//        quizHint2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showHint2();
//            }
//        });
//
//
//        quizLearn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLearn();
//            }
//        });
//
//        quizRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                refreshData();
//            }
//        });

//        quizImage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zoomImage();
//            }
//        });

//        showSuccessFailImage();
//        showSuccessImage();
    }

//    public void zoomImage(){
//        String imageName = item_detail.get(0).getItem_name().replace(' ','_');
//        String imageResult = item_detail.get(0).getResult();
//        final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View promptsView = li.inflate(R.layout.activity_zoom, null);
//
//
//        int zd = quizContext.getResources().getIdentifier(imageName,"drawable",quizContext.getPackageName());
//
//        ImageView iv = (ImageView)promptsView.findViewById(R.id.zoom_image);
//        iv.setImageResource(zd);
//        if("success".equals(imageResult)) {
//            TextView tv = (TextView) promptsView.findViewById(R.id.zoom_text);
//
//            tv.setText(item_detail.get(0).getProper_name().toString());
//        }
//
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                promptsView.getContext());
//
//        // set prompts.xml to alertdialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        alertDialogBuilder.setPositiveButton("Close",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        alertDialog.dismiss();
//                    }
//                });
//
//        // create alert dialog
//        alertDialog = alertDialogBuilder.create();
//        int width = (int)(getResources().getDisplayMetrics().widthPixels*1.1f);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7f);
//
//        // show it
//        alertDialog.show();
//        alertDialog.getWindow().setLayout(width,height);
//    }

    public Integer getCurrentHangNo(){
        item_hangNo = sqlt.getQuiz(itemFilter);
        return item_hangNo.get(0).getHang_no();

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

//    public void showLearn(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        if (activeNetworkInfo == null) {
//            Intent noInternet = new Intent(this, NoInternetActivity.class);
//            startActivity(noInternet);
//        }
//        else{
//            item_details = sqlt.getQuiz(itemFilter);
//            if(item_details.get(0).getResult().equals("success")) {
//
//
////load interstitial ads
//                loadInterstitialHintAd();
//
//
//                String wiki_link = "https://en.wikipedia.org/wiki/"+item_detail.get(0).getWiki_link();
//
//                final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View promptsView = li.inflate(R.layout.activity_wiki, null);
//                mAdView = (AdView) promptsView.findViewById(R.id.wikiAdView);
//                AdRequest adRequest = new AdRequest.Builder()
//                        .build();
//                //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//                //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//                mAdView.loadAd(adRequest);
//                if(!"NULL".equals(wiki_link)) {
//                    WebView wikiPage = (WebView) promptsView.findViewById(R.id.quiz_wiki_web);
//                    wikiPage.setWebViewClient(new CustomWebViewClient());
//                    WebSettings webSetting = wikiPage.getSettings();
//                    webSetting.setJavaScriptEnabled(true);
//                    webSetting.setDisplayZoomControls(true);
//                    wikiPage.loadUrl(wiki_link);
//                }
//                else{
//                    Toast.makeText(quizContext, "sorry", Toast.LENGTH_SHORT).show();
//                }
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        promptsView.getContext());
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                alertDialogBuilder.setPositiveButton("Close",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                alertDialog.dismiss();
//                            }
//                        });
//
//                // create alert dialog
//                alertDialog = alertDialogBuilder.create();
//                int width = (int)(getResources().getDisplayMetrics().widthPixels*1.1f);
//                int height = (int)(getResources().getDisplayMetrics().heightPixels*1.1f);
//
//                // show it
//                alertDialog.show();
//                alertDialog.getWindow().setLayout(width,height);
//            }
//            else{
//                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                v.vibrate(500);
//                Toast.makeText(quizContext, "First Solve !", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

//    public void refreshData(){
////load interstitial ads
//        loadInterstitialHintAd();
//        sqlt.resetData(itemFilter,reference_try);
//        reloadActivity();
//
//    }

//    public void showSuccessFailImage(){
//        item_detail = sqlt.getQuiz(itemFilter);
//        if("hanged".equals(item_detail.get(0).getResult())){
//            final LayoutInflater li = (LayoutInflater)QuizActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View promptsView = li.inflate(R.layout.activity_success_fail, null);
//
//            mAdView = (AdView) promptsView.findViewById(R.id.successFailAdView);
//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D9B4B2278852FCB4969314FB997BCD1").build();
//            //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//            mAdView.loadAd(adRequest);
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    promptsView.getContext());
//
//            // set prompts.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//
//            alertDialogBuilder.setPositiveButton("Try Again",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            refreshData();
//                            alertDialog.dismiss();
//
//                        }
//                    });
//
//            alertDialogBuilder.setOnKeyListener(new Dialog.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface arg0, int keyCode,
//                                     KeyEvent event) {
//                    // TODO Auto-generated method stub
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        Intent a = new Intent(QuizActivity.this,ItemActivity.class);
//                        a.putExtra("grpFilter",item_detail.get(0).getGrp_id());
//                        startActivity(a);
//                    }
//                    return true;
//                }
//            });
//
//            // create alert dialog
//            alertDialog = alertDialogBuilder.create();
//            int width = (int)(getResources().getDisplayMetrics().widthPixels*1.1f);
//            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.9f);
//
//            // show it
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(width,height);
//        }
//    }

//    public void showSuccessImage(){
//        item_detail = sqlt.getQuiz(itemFilter);
//        if("success".equals(item_detail.get(0).getResult())){
//            zoomImage();
//        }
//    }

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

//    public void loadInterstitialHintAd(){
//        //AdRequest admobHint = new AdRequest.Builder().addTestDevice("E498786B6424DB4D655F2D365A363A66").build();
//        AdRequest admobHint = new AdRequest.Builder().build();
//        //for admob interstitial
//        interstitial = new InterstitialAd(QuizActivity.this);
//        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
//        interstitial.loadAd(admobHint);
//        interstitial.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                if(interstitial.isLoaded()){
//                    interstitial.show();
//                }
//            }
//        });
//    }

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
                Intent mBack = new Intent(QuizActivity.this,ItemActivity.class);
                mBack.putExtra("grpFilter",item_detail.get(0).getGrp_id());
                startActivity(mBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void warningMessage(int count){
        switch (count){
            case 1:
                Toast.makeText(quizContext, "Wrong Guess! One Chance gone", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(quizContext, "Wrong Guess! Second Chance gone", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(quizContext, "Wrong Guess! Third Chance gone", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(quizContext, "Be Careful! Half way to be hanged", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(quizContext, "Wrong Guess! You are close to be hanged", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(quizContext, "Wrong Guess! Last Chance.", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(quizContext, "You are hanged !!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
