package com.eps.buscamines;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
public class CronometroTest extends AppCompatActivity {

    private CountDownTimer timer;
    public static long lastCountDown=10*1000;; //Milliseconds for view ad
    public static Boolean isCountDown = false;
    public static Context context;

    TextView countdownText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_test);
        countdownText= findViewById(R.id.textView2);

        if (isCountDown==false){
            lastCountDown=10*1000;
        }


        timer = MiContador(lastCountDown,1000);
        timer.start();


    }

    private void gameover() {
        if (isCountDown == false){
            Intent in = new Intent(getBaseContext(), MailSender.class);
            startActivity(in);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCountDown==true){
            timer  = MiContador(lastCountDown,1000);
            timer.start();
        }

        //lastCountDown =timer.lastCountDown;
        Log.i("CRONOTEST", "onResume: "+lastCountDown/1000);



    }

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

    @Override
    protected void onPause() {
        Log.i("CRONOTEST", "onPause CRONO: "+lastCountDown/1000);

        timer.cancel();
        super.onPause();

    }

}