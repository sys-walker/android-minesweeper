package com.eps.buscamines2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.eps.buscamines2.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.eps.buscamines2.util.Constants.*;

public class PreStartActivity extends AppCompatActivity {
    private static final String TAG = "PreStartActivity:";
    public static String username;
    public  int size;
    public static boolean time_control; //NO static
    public double ENTROPY ;


    public TextInputLayout user_textInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start);

        user_textInputLayout = findViewById(R.id.date_TextField);
    }
    public void go_to_start_game(View view) {
        EditText editable = user_textInputLayout.getEditText();
        if (editable == null) {
            username = "Player";
        }else{
            username = user_textInputLayout.getEditText().getText().toString();
        }



        if (username.length()==0){
            Toast.makeText(getBaseContext(),getString(R.string.error_username),Toast.LENGTH_SHORT).show();
            return;
        }

        size = getRadioButtonSizeVal();
        time_control= getTimeControlToggleButton();
        ENTROPY = getRadioButtonEntropyVal();

        Intent in = new Intent(getBaseContext(), MineSweeperHost.class);
        in.putExtra(PRESTART_USERNAME,username);
        in.putExtra(PRESTART_SIZE,size);
        in.putExtra(PRESTART_COUNTDOWN,time_control);
        in.putExtra(PRESTART_ENTROPY,ENTROPY);

        startActivity(in);
        finish();
    }

    private boolean getTimeControlToggleButton() {
        ToggleButton toggleButton= findViewById(R.id.time_control_button);

        return toggleButton.isChecked();



    }


    public int getRadioButtonSizeVal() {
        RadioGroup radioGroup = findViewById(R.id.sizeRadioGroup);
        if (radioGroup != null) {
            int radioButtonSelected = radioGroup.getCheckedRadioButtonId();
            switch (radioButtonSelected){
                case R.id.radio_button_val7:

                    return 7;
                case R.id.radio_button_val6:

                    return 6;
                case R.id.radio_button_val5:

                    return 5;
            }
        }
        return 7;


    }

    public double getRadioButtonEntropyVal() {
        RadioGroup radioGroup = findViewById(R.id.entopyRadioGroup);
        if (radioGroup != null) {
            int radioButtonSelected = radioGroup.getCheckedRadioButtonId();
            switch (radioButtonSelected){
                case R.id.radio_button_entropy15:

                    return 0.15;
                case R.id.radio_button_entropy25:

                    return 0.25;
                case R.id.radio_button_entropy35:

                    return 0.35;
            }
        }
        return 25;

    }
}