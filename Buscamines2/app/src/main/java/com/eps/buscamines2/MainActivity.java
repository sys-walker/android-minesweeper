package com.eps.buscamines2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eps.buscamines2.R;
import com.eps.buscamines2.activities.HelpActivity;
import com.eps.buscamines2.activities.PreStartActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToPrestart(View view) {
        Intent in = new Intent(getBaseContext(), PreStartActivity.class);
        startActivity(in);
        finish();
    }

    public void goToEndGame(View view) {
        finishAffinity();
    }

    public void goToHelp(View view) {
        Intent in = new Intent(getBaseContext(), HelpActivity.class);
        startActivity(in);
        finish();
    }
}