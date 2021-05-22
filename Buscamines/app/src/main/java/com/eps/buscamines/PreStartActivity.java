package com.eps.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;

public class PreStartActivity extends AppCompatActivity {
    public static int SIZE=0;
    public TextInputLayout user_textInputLayout;
    public static double ENTROPY = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start);
        user_textInputLayout = findViewById(R.id.outlinedTextField);

    }

    public void go_to_start_game(View view) {
        String username = user_textInputLayout.getEditText().getText().toString();

        if (username.length()==0){
            Toast.makeText(getBaseContext(),getString(R.string.error_username),Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(getClass().getName(),
                "            " +
                     "\nUsername     = "+username+
                     "\nSize         = "+getRadioButtonSizeVal()+
                     "\nTime control = "+getTimeControlToggleButton()+
                     "\nEntropy      = "+getRadioButtonEntropyVal()+"%"
        );


        Intent in = new Intent(getBaseContext(), Minesweeper.class);
        in.putExtra("Entropy", getRadioButtonEntropyVal());
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
                    SIZE=7;
                    return 7;
                case R.id.radio_button_val6:
                    SIZE=6;
                    return 6;
                case R.id.radio_button_val5:
                    SIZE=5;
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
                    ENTROPY=0.15;
                    return 0.15;
                case R.id.radio_button_entropy25:
                    ENTROPY=0.25;
                    return 0.25;
                case R.id.radio_button_entropy35:
                    ENTROPY=0.35;
                    return 0.35;
            }
        }
        return 25;

    }
}