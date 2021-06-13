package com.eps.buscamines2.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eps.buscamines2.R;
import com.eps.buscamines2.fragments.MinesweeperFragment;
import com.eps.buscamines2.util.MSGeneratorMap;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Objects;

import static com.eps.buscamines2.util.Constants.*;


public class MailSender extends AppCompatActivity {
    public Bundle results_game;
    String today_str;
    private TextInputLayout date;
    private TextInputLayout email;
    private TextInputLayout data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            results_game = savedInstanceState.getBundle(MAILSENDER_RESULTS);
            today_str = savedInstanceState.getString(MAILSENDER_TODAY);

        } else {
            results_game = MinesweeperFragment.Extras;
            today_str = new Date().toString();
        }
        setContentView(R.layout.activity_mail_sender);

        date = findViewById(R.id.date_TextField);
        email = findViewById(R.id.receiver_mail_TextField);
        data = findViewById(R.id.log_values_TextField);

        setDate();
        setLogs();
    }

    public void goToPreStart(View view) {
        Intent in = new Intent(getBaseContext(), PreStartActivity.class);
        startActivity(in);
        finishAffinity();
    }

    public void goToEndGame(View view) {
        finishAffinity();
    }

    public void goToSendEmail(View view) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getEmailTo()});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getEmailSubject());
        emailIntent.putExtra(Intent.EXTRA_TEXT, getEmailBody());
        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.email_chooser)));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.email_chooser_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void setLogs() {

        EditText editText = data.getEditText();

        if (editText != null && results_game != null) {
            StringBuilder textMail = new StringBuilder();
            textMail.append(getString(R.string.username_log)).append(results_game.get(PRESTART_USERNAME)).append("\n");
            textMail.append(getString(R.string.cells_log)).append(((MSGeneratorMap) results_game.getParcelable(MINESWEEPER_MAP)).getFullSize()).append("\n");
            textMail.append(getString(R.string.mines_entropy)).append(((MSGeneratorMap) results_game.getParcelable(MINESWEEPER_MAP)).get_percentage_mines()).append("\n");
            textMail.append(getString(R.string.mines_num)).append(((MSGeneratorMap) results_game.getParcelable(MINESWEEPER_MAP)).get_num_bombs()).append("\n");
            textMail.append((results_game.getBoolean(PRESTART_COUNTDOWN)) ? "\n" : getString(R.string.log_time_elapsed) + results_game.get(TIME_WINNER) + "s").append("\n");

            switch (results_game.getInt(GAME_RESULT_KEY, -1)) {
                case GAME_WIN:
                    textMail.append(getString(R.string.log_win_statement));
                    if (!results_game.getBoolean(PRESTART_COUNTDOWN)){
                        textMail.append(results_game.getString(TIME_WINNER));
                    }


                    break;
                case GAME_LOSE:
                    textMail.append(getString(R.string.log_lose_statement)).append("\n");
                    textMail.append(getString(R.string.log_losepoint_statement)).append(results_game.getString(LOSE_POINT)).append(getString(R.string.log_remaining_chunk1)).append(results_game.getInt(TILES_LEFT)).append(getString(R.string.log_remaining_chunk2));

                    break;
                case GAME_TIMEOUT:
                    textMail.append(getString(R.string.log_lose_statement)).append("\n");
                    textMail.append(getString(R.string.log_remaining_chunk1)).append(results_game.getInt(TILES_LEFT)).append(getString(R.string.log_remaining_chunk2));

                    break;
                default:

                    Log.wtf(getClass().getName(), "Bro Tio WTF ");
            }
            editText.setText(textMail.toString());
        }
    }

    private void setDate() {
        Date today = new Date();
        EditText editText = date.getEditText();
        if (editText != null) {
            editText.setText(today.toString());
        }
    }


    private String getEmailTo() {

        String emailTo;
        EditText to = email.getEditText();

        if (to != null) {
            emailTo = to.getText().toString();
        } else {
            emailTo = "example@example.com";
        }
        return emailTo;
    }

    private String getEmailBody() {

        String infoGame = "No log game";
        EditText info = data.getEditText();

        if (info != null) {
            infoGame = info.getText().toString();
        }
        return infoGame;
    }

    private String getEmailSubject() {
        String subject = Objects.requireNonNull(date.getEditText()).getText().toString();
        return "LOG-" + subject;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        results_game = savedInstanceState.getBundle(MAILSENDER_RESULTS);
        today_str = savedInstanceState.getString(MAILSENDER_TODAY);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBundle(MAILSENDER_RESULTS, results_game);
        outState.putString(MAILSENDER_TODAY, today_str);
        super.onSaveInstanceState(outState);
    }
}