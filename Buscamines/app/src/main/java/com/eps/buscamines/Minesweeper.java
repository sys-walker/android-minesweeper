package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.CountDownTimer;
=======
import android.os.Parcel;
import android.os.Parcelable;
>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
<<<<<<< HEAD
=======
import android.widget.LinearLayout;
>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1
import android.widget.TextView;

import java.util.ArrayList;

public class Minesweeper extends AppCompatActivity {
    // countdown
    public static int SECONDS=10;
    private CountDownTimer timer;
    public static long lastCountDown=SECONDS*1000;;
    public static Boolean isTimedOut = false;
    public static Context context;
    TextView countdownText;
    public static int time_elapsed;


    //BoardParcelable boardParcelable;

    public static double entropy;
    public static boolean time;
    public static ArrayList<Tile> tiles2;
    public static String[][] referenceMap;
    public static MSGeneratorMap generatedReference;
<<<<<<< HEAD
    public static int tilesDescovered ;
    public static int winState =1; // 0=Win 1=Lose 2=Timeout
=======
    public static int height, width;
    private static LinearLayout layoutTemps;
    private static int temps = 60;
    static TextView textView;

>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        tilesDescovered = PreStartActivity.SIZE * PreStartActivity.SIZE-1;
        winState=1;



=======
>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1
        setContentView(R.layout.activity_minesweeper);

        Intent intent = getIntent();
        entropy = intent.getDoubleExtra("Entropy", 0.25);
        time = intent.getBooleanExtra("Time", false);

       layoutTemps = findViewById(R.id.linearLayoutTime);
       textView = findViewById(R.id.time);

        if (savedInstanceState != null) {

            ArrayList<String> saved;
            int z = 0;
            tiles2 = savedInstanceState.getParcelable("GAME");
            saved = savedInstanceState.getParcelable("MAP");
            for (int i = 0; i < referenceMap.length; i++){
                for (int j = 0; j < referenceMap.length; j++){
                    referenceMap[i][j] = saved.get(z);
                    z++;
                }
            }
        }else {
            setGrid();
        }


        startDisplay();

    }



    private void getSizeParrilla() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height =  getScreenWidth(getBaseContext());
            width = 0;
            Log.i(getClass().getName(),"Its LANDSCAPE width:" +  width +"and heigh: "+ height);
        } else {
            Log.i(getClass().getName(),"Its PORTRAIT");
            height = 0;
            width =  getScreenWidth(getBaseContext());
        }
    }

    private void startDisplay() {
        ButtonAdapter imageAdapter = new ButtonAdapter(this);
        GridView gridView= findViewById(R.id.gridview);

<<<<<<< HEAD
        //gridView.getLayoutParams().height = SIZE_PIXELS;
        //gridView.getLayoutParams().width = SIZE_PIXELS;
        //gridView.requestLayout();
=======
        getSizeParrilla();
        gridView.getLayoutParams().height = height;
        gridView.getLayoutParams().width = width;
        gridView.requestLayout();
>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1


        gridView.setBackgroundColor(Color.BLUE);
        gridView.setAdapter(imageAdapter);

        gridView.setNumColumns(PreStartActivity.SIZE);

        Log.i(getClass().getName(), " control? "+PreStartActivity.time_control);
        if (PreStartActivity.time_control){
            setCountdowm();
        }


    }

    private void setGrid() {

        generatedReference=new MSGeneratorMap(PreStartActivity.SIZE,entropy);
        generatedReference.generate();

        referenceMap = generatedReference.getBoard();

        tiles2 = new ArrayList<>();


        for (int i = 0; i <referenceMap.length ; i++) {
            for (int j = 0; j < referenceMap[0].length; j++) {
                tiles2.add(new Tile(referenceMap[i][j]));
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(PreStartActivity.time_control){
            if (isTimedOut){
                timer  = MiContador(lastCountDown,1000);
                timer.start();
            }


            Log.i("CRONOTEST", "onResume: "+lastCountDown/1000);
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore instance

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putParcelable("GAME", (Parcelable) tiles2);

        ArrayList<String> saved = new ArrayList<>();

        for (int i = 0; i < referenceMap.length; i++){
            for (int j = 0; j < referenceMap.length; j++){
                saved.add(referenceMap[i][j]);
            }
        }
        outState.putParcelable("MAP", (Parcelable) saved);
        //outstate
    }

    public int getScreenWidth(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }
    public int getScreenHeight(@NonNull Context context) {
        // Status bar+actionbar
        int status_bar = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            status_bar = getResources().getDimensionPixelSize(resourceId);
        }



        int action_bar = 0;
        int actionBarHeight = getSupportActionBar().getHeight();
        if (actionBarHeight != 0){
            action_bar= actionBarHeight;
        }

        final TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        action_bar= actionBarHeight;



        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }


        return displayMetrics.heightPixels - action_bar -status_bar;
    }


<<<<<<< HEAD

    public static void undescoveredTiles() {
        tilesDescovered--;
    }

    //------------------------------------------------------------------------------
    private CountDownTimer MiContador(long lastCountDown, int step) {
        CountDownTimer countDownTimer = new CountDownTimer(lastCountDown, step) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Countdown", "onTick: " + String.valueOf(millisUntilFinished / 1000));
                CronometroTest.lastCountDown = millisUntilFinished;
                if ((millisUntilFinished / 1000)==0){
                    CronometroTest.isCountDown=false;
                }else {
                    CronometroTest.isCountDown=true;
                }
                Minesweeper.time_elapsed = (int) millisUntilFinished/1000;

                countdownText.setText((millisUntilFinished / 1000 + ""));
            }

            @Override
            public void onFinish() {

                Log.i("Countdown", "onFinish: ");
                CronometroTest.isCountDown = false;
                gameover();
            }
        };
        return countDownTimer;
    }
    private void gameover() {

        if (isTimedOut == false){
            winState=2;
            Intent in = new Intent(getBaseContext(), MailSender.class);
            startActivity(in);
            finish();
        }

    }


    private void setCountdowm() {
        if (PreStartActivity.time_control){
            countdownText= findViewById(R.id.textView2);

            if (isTimedOut==false){
                lastCountDown=SECONDS*1000;
            }

            timer = MiContador(lastCountDown,1000);
            timer.start();
        }

    }
    @Override
    protected void onPause() {
        Log.i("CRONOTEST", "onPause CRONO: "+lastCountDown/1000);
        if (PreStartActivity.time_control){
            timer.cancel();
        }

        super.onPause();

    }

=======
    public static void time() {
        if (time) {
            layoutTemps.setVisibility(View.VISIBLE);
            textView.setText("Temps: " + temps);
        }
    }
>>>>>>> 3359014f91e5b3a6c47959239ff1a190b34f9de1
}