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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);

        setTimerTextViews(savedInstanceState);






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