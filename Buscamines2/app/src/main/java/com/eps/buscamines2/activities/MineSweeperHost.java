package com.eps.buscamines2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.eps.buscamines2.R;
import com.eps.buscamines2.fragments.MinesweeperFragment;
import com.eps.buscamines2.fragments.MinesweeperLogFragment;

public class MineSweeperHost extends AppCompatActivity implements MinesweeperFragment.MinesweeperEvents {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper_host);
        MinesweeperFragment minesweeperFragment = (MinesweeperFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentGameHost);
        if (minesweeperFragment != null){
            minesweeperFragment.setOnEventListener(this);
        }
    }

    @Override
    public void onEventIsDetected(String event_text) {
        MinesweeperLogFragment minesweeperLogFragment = (MinesweeperLogFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentGameLog);


        if (minesweeperLogFragment != null && minesweeperLogFragment.isInLayout() ) {
            Log.i(MinesweeperFragment.TAG_EVENTS,"Event detected! sending message: " + event_text);
            minesweeperLogFragment.addBasicLog(event_text);

        }else{
            Log.i(MinesweeperFragment.TAG_EVENTS,": Event detected! But it was ignored Reason: No Log Fragment was loaded");
        }
    }

}