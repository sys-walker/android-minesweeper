package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.eps.buscamines.Constants.*;
import java.util.ArrayList;

import static com.eps.buscamines.Constants.*;

public class Minesweeper extends AppCompatActivity {
    // countdown
    public static int SECONDS =10;
    private CountDownTimer countDownTimer;
    public static long current_time=SECONDS*1000;
    public static Boolean isStarted = false;
    //common
    public static TextView crono;

    // cronometer
    public static boolean isOn;
    Thread timer;
    public static int milis,seg,minutos;
    public  static Handler h = new Handler();

    //minesweeper
    public static int SIZE_PIXELS;
    public static double entropy;
    public static ArrayList<Tile> tiles2;
    public static String[][] referenceMap;
    public static MSGeneratorMap generatedReference;
    public static int tilesDescovered ;
    public static int winState =1; // 0=Win 1=Lose 2=Timeout



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);

        tilesDescovered = PreStartActivity.SIZE * PreStartActivity.SIZE-1;
        winState=1;
        Intent intent = getIntent();
        entropy = intent.getDoubleExtra("Entropy", 0.25);

        SIZE_PIXELS = getSizeParrilla();
        setGrid();
        startDisplay(savedInstanceState);

        






    }

    private void startDisplay(Bundle savedInstanceState) {
        ButtonAdapter imageAdapter = new ButtonAdapter(this);
        GridView gridView= findViewById(R.id.gridview);

        //gridView.getLayoutParams().height = SIZE_PIXELS;
        //gridView.getLayoutParams().width = SIZE_PIXELS;
        //gridView.requestLayout();


        gridView.setBackgroundColor(Color.BLUE);
        gridView.setAdapter(imageAdapter);

        gridView.setNumColumns(PreStartActivity.SIZE);

        Log.i(getClass().getName(), " control? "+PreStartActivity.time_control);
        setTimerTextViews(savedInstanceState);
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

    public static void undescoveredTiles() {
        tilesDescovered--;
    }


    private int getSizeParrilla() {
        int height = getScreenHeight(getBaseContext());
        int width  = getScreenWidth(getBaseContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(getClass().getName(),"Its LANDSCAPE");
            //height sizes
            return height;
        } else {
            Log.i(getClass().getName(),"Its PORTRAIT");

            return width;
        }
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

    private void setTimerTextViews(Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            milis = savedInstanceState.getInt(MILIS_KEY);
            seg = savedInstanceState.getInt(SEG_KEY);
            minutos = savedInstanceState.getInt(MIN_KEY);
            isOn=savedInstanceState.getBoolean(ISON_KEY);
            String a= minutos+":"+seg+":"+milis;

            Log.i(getClass().getName()+"RECREATED ",a);
        }else {
            minutos=0;
            seg=0;
            milis=0;
            isOn=false;
        }

        crono = findViewById(R.id.crono);


        System.out.println("control de temps?"+ PreStartActivity.time_control);
        if (!PreStartActivity.time_control){
            cronometroEnvirontment();

        }
        else {
            TextView titleView= findViewById(R.id.textView0);
            titleView.setText("COUNTDOWN");
            if (!isStarted){
                current_time=SECONDS*1000;
            }


            countDownTimer = new MiContador(current_time);
            countDownTimer.start();


        }
    }

    private void cronometroEnvirontment() {

        isOn=true;
        String time="";


        String m="",s="",mi="";
        if ((minutos<10)){
            time+="0"+minutos;
        }else{
            time+=""+minutos;
        }

        time+=":";
        if ((seg<10)){
            time+="0"+seg;
        }else{
            time+=""+seg;
        }
        time+=":";
        if ((milis<10)){
            time+="00"+milis;
        }else if(milis<100){
            time+="0"+milis;
        }else{
            time+=""+milis;
        }


        crono.setText(time);


        timer = new Thread(new Cronometer());
        Cronometer.running=true;
        timer.start();
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        milis = savedInstanceState.getInt(MILIS_KEY);
        seg = savedInstanceState.getInt(SEG_KEY);
        minutos = savedInstanceState.getInt(MIN_KEY);
        isOn = savedInstanceState.getBoolean(ISON_KEY);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(MILIS_KEY,milis);
        outState.putInt(SEG_KEY,seg);
        outState.putInt(MIN_KEY,minutos);
        outState.putBoolean(ISON_KEY,isOn);


    }


    @Override
    protected void onPause() {
        if (PreStartActivity.time_control){
            countDownTimer.cancel();
        }else {
            Cronometer.running=false;
            Log.i(getClass().getName()," "+timer.isAlive());

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStarted){
            countDownTimer = new MiContador(current_time);
            countDownTimer.start();
        }
    }


    @Override
    protected void onDestroy() {

        // ensure cancellation of countdowns or cronometers
        if(PreStartActivity.time_control) {
            // countdown
            countDownTimer.cancel();
        }else {
            Cronometer.running=false;
            //cronometro
        }
        super.onDestroy();
    }

    private void gameover() {
        winState=2;

    }

    public class MiContador extends CountDownTimer {
        final int STEP = 1000;
        public MiContador(long lastCountDown_) {
            super(lastCountDown_,1000);

        }

        @Override
        public void onTick(long millisUntilFinished) {

            Minesweeper.current_time = millisUntilFinished;
            if ((millisUntilFinished / 1000)==0){
                Minesweeper.isStarted=false;
            }else {
                Minesweeper.isStarted=true;
            }
            long minutos= (millisUntilFinished / STEP) /60;
            long segundos=(millisUntilFinished / STEP)%60;
            String current_time="";

            if ((minutos<10)){
                current_time+="0"+minutos;
            }else{
                current_time+=""+minutos;
            }
            current_time+=":";
            if ((segundos<10)){
                current_time+="0"+segundos;
            }else{
                current_time+=""+segundos;
            }

            Minesweeper.crono.setText(current_time);
        }

        @Override
        public void onFinish() {
            Minesweeper.isStarted = false;
            Intent in = new Intent(getBaseContext(), MailSender.class);
            startActivity(in);
            finish();
            gameover();

        }
    }


}