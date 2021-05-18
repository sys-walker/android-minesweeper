package com.eps.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

    }

    public void go_to_main(View view) {
        Intent in = new Intent(getBaseContext(),MainActivity.class);
        startActivity(in);
        finish();
    }
}