package com.eps.buscamines2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eps.buscamines2.R;
import com.eps.buscamines2.fragments.MinesweeperFragment;
import com.eps.buscamines2.util.MSGeneratorMap;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import static com.eps.buscamines2.util.Constants.*;

public class MailSender extends AppCompatActivity {
    public Bundle results_game;

    public TextInputLayout dateTextInputSubject;  //-> EXTRA_SUBJECT (asunto)
    public TextInputLayout logsTextInputText; //->  EXTRA_TEXT (cuerpo mail)
    public TextInputLayout emailToTextInput; //-> EXTRA_EMAIL, TO 


    String today_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            results_game = savedInstanceState.getBundle(MAILSENDER_RESULTS);
            today_str = savedInstanceState.getString(MAILSENDER_TODAY);

        }else {
            results_game  = MinesweeperFragment.Extras;
            today_str = new Date().toString();
        }
        setContentView(R.layout.activity_mail_sender);
        System.out.println(today_str);
        dateTextInputSubject = findViewById(R.id.date_TextField);
        logsTextInputText = findViewById(R.id.log_values_TextField);
        emailToTextInput =findViewById(R.id.receiver_mail_TextField);

        set_today_date();
        set_logs();

    }

    private void set_logs() {
        EditText editText = logsTextInputText.getEditText();
        if (editText != null && results_game !=null) {


            StringBuilder textMail = new StringBuilder();
            textMail.append(getString(R.string.username_log)+results_game.get(PRESTART_USERNAME)+"\n");
            textMail.append(getString(R.string.cells_log)   +((MSGeneratorMap)results_game.getParcelable(MINESWEEPER_MAP)).getFullSize()   +"\n");
            textMail.append(getString(R.string.mines_entropy)   +((MSGeneratorMap)results_game.getParcelable(MINESWEEPER_MAP)).get_percentage_mines()   +"\n");
            textMail.append(getString(R.string.mines_num)   +((MSGeneratorMap)results_game.getParcelable(MINESWEEPER_MAP)).get_num_bombs()   +"\n");
            textMail.append( ((results_game.getBoolean(PRESTART_COUNTDOWN))? "\n":getString(R.string.log_time_elapsed)+"XXX"+"s")  +"\n");



            switch (results_game.getInt(GAME_RESULT_KEY,-1)){
                case GAME_WIN:
                    textMail.append(getString(R.string.log_win_statement));
                    // crono     -> elapsed time
                    // countdown -> total - transcorregut
                    break;
                case GAME_LOSE:
                    textMail.append(getString(R.string.log_lose_statement));
                    textMail.append(getString(R.string.log_losepoint_statement) + results_game.getString(LOSE_POINT)+getString(R.string.log_remaining_chunk1) + results_game.getInt(TILES_LEFT) + getString(R.string.log_remaining_chunk2));
                    break;
                case GAME_TIMEOUT:
                    textMail.append(getString(R.string.log_lose_statement)+"\n");
                    break;
                default:
                    Log.wtf(getClass().getName(), "Bro Tio WTF");
            }

           editText.setText(textMail.toString());
        }
    }

    private void set_today_date() {
        EditText editText= dateTextInputSubject.getEditText();
        if (editText != null) {
            editText.setText(today_str);
        }
    }


    public void go_to_eng_game(View view) {
        finishAffinity();
    }

    public void send_mail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{get_mail_to()});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "LOG - "+today_str);
        emailIntent.putExtra(Intent.EXTRA_TEXT, get_mail_body());
        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.email_chooser)));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.email_chooser_error), Toast.LENGTH_SHORT).show();
        }

    }

    private String get_mail_to() {
        String extra_mailTo = "";
        EditText emailToEditText = emailToTextInput.getEditText();
        if (emailToEditText != null) {
            extra_mailTo= emailToEditText.getText().toString();
            if (extra_mailTo.isEmpty()){
                extra_mailTo="msendin@gmail.com";
            }
        }else{
            extra_mailTo="msendin@gmail.com";
        }
        return extra_mailTo;
    }

    private String get_mail_body() {
        String extra_text="";
        EditText body_mail = logsTextInputText.getEditText();
        if (body_mail != null) {
            extra_text = body_mail.getText().toString();
        }
        return extra_text;

    }

    public void go_to_prestart(View view) {
        Intent in = new Intent(getBaseContext(), PreStartActivity.class);
        startActivity(in);
        finishAffinity();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        results_game = savedInstanceState.getBundle(MAILSENDER_RESULTS);
        today_str = savedInstanceState.getString(MAILSENDER_TODAY);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBundle(MAILSENDER_RESULTS,results_game);
        outState.putString(MAILSENDER_TODAY,today_str);
        super.onSaveInstanceState(outState);
    }
}