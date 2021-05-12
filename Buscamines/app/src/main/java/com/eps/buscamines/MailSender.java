package com.eps.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class MailSender extends AppCompatActivity {
    
    public TextInputLayout date;  //-> EXTRA_SUBJECT
    public TextInputLayout log; //->  EXTRA_TEXT
    public TextInputLayout email_to; //-> EXTRA_EMAIL, TO

    Date today;
    String date_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);

         date = findViewById(R.id.outlinedTextField);
         log = findViewById(R.id.outlinedTextField2);
         email_to =findViewById(R.id.outlinedTextField3);
         set_today_date();
         set_logs();


        
    }

    private void set_logs() {
        EditText editText= log.getEditText();
        if (editText != null) {
            editText.setText("LOG EXEMPLE");
        }
    }

    private void set_today_date() {

        EditText editText= date.getEditText();
        if (editText != null) {
            editText.setText(get_today_date());
        }

    }

    private String get_today_date() {
        today = new Date();
        date_str = today.toString();
        return date_str;
    }

    public void go_to_eng_game(View view) {
        finish();
    }

    public void go_to_prestart(View view) {
        Intent in = new Intent(getBaseContext(),PreStartActivity.class);
        startActivity(in);
        finish();
    }

    public void send_mail(View view) {

        String subject=get_mail_subject();
        String body_msg = get_mail_body();
        String to =get_mail_to();

        String[] TO = {to};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body_msg);

        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            startActivity(Intent.createChooser(emailIntent, getString(R.string.email_chooser)));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.email_chooser_error), Toast.LENGTH_SHORT).show();
        }

    }

    private String get_mail_to() {
        String to = "";
        EditText aoe = email_to.getEditText();//msendin -> EXTRA_EMAIL, TO
        if (aoe != null) {
            to= aoe.getText().toString();
            if (to.isEmpty()){
                to="msendin@gmail.com";
            }
        }else{
            to="msendin@gmail.com";
        }
        return to;
    }

    private String get_mail_body() {
        String body_msg = "";
        EditText oe = log.getEditText();//log ->  EXTRA_TEXT
        if (oe != null) {
            body_msg= oe.getText().toString();
            if (body_msg.isEmpty()){
                body_msg="No Logs";
            }
        }else{
            body_msg="No Logs";
        }
        return body_msg;
    }

    private String get_mail_subject() {
        EditText ae= date.getEditText(); //dia y hora  -> EXTRA_SUBJECT
        String subject="";
        if (ae != null) {
            subject= ae.getText().toString();
            if (subject.isEmpty()){
                subject=get_today_date();
            }
        }else{
            subject=get_today_date();
        }
        return subject;
    }
}