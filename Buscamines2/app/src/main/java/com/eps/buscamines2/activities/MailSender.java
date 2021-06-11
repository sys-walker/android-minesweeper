package com.eps.buscamines2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eps.buscamines2.R;
import com.eps.buscamines2.activities.PreStartActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MailSender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);
    }

    public void go_to_eng_game(View view) {
        finishAffinity();
    }

    public void send_mail(View view) {
    }

    public void go_to_prestart(View view) {
        Intent in = new Intent(getBaseContext(), PreStartActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
        finishAffinity();
    }
}