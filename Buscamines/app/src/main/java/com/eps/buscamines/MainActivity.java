package com.eps.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void go_to_help(View view) {
        Intent in = new Intent(getBaseContext(),HelpActivity.class);
        startActivity(in);
        finish();
    }

    public void go_to_prestart(View view) {
        Intent in = new Intent(getBaseContext(),PreStartActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
        finish();
    }

    public void go_to_eng_game(View view) {
        finish();
    }
}