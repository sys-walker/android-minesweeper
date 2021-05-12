package com.eps.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class PreStartActivity extends AppCompatActivity {
    public static int SIZE=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start);

    }

    public void go_to_start_game(View view) {
        Intent in = new Intent(getBaseContext(), Minesweeper.class);
        startActivity(in);
        finish();
    }


}