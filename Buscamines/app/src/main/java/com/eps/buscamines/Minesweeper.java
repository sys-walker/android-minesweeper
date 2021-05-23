package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Minesweeper extends AppCompatActivity {

    //BoardParcelable boardParcelable;

    public static double entropy;
    public static boolean time;
    public static ArrayList<Tile> tiles2;
    public static String[][] referenceMap;
    public static MSGeneratorMap generatedReference;
    public static int height, width;
    private static LinearLayout layoutTemps;
    private static int temps = 60;
    static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);

        Intent intent = getIntent();
        entropy = intent.getDoubleExtra("Entropy", 0.25);
        time = intent.getBooleanExtra("Time", false);

       layoutTemps = findViewById(R.id.linearLayoutTime);
       textView = findViewById(R.id.time);

        if (savedInstanceState != null) {

            ArrayList<String> saved;
            int z = 0;
            tiles2 = savedInstanceState.getParcelable("GAME");
            saved = savedInstanceState.getParcelable("MAP");
            for (int i = 0; i < referenceMap.length; i++){
                for (int j = 0; j < referenceMap.length; j++){
                    referenceMap[i][j] = saved.get(z);
                    z++;
                }
            }
        }else {
            setGrid();
        }

        startDisplay();

    }



    private void getSizeParrilla() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height =  getScreenWidth(getBaseContext());
            width = 0;
            Log.i(getClass().getName(),"Its LANDSCAPE width:" +  width +"and heigh: "+ height);
        } else {
            Log.i(getClass().getName(),"Its PORTRAIT");
            height = 0;
            width =  getScreenWidth(getBaseContext());
        }
    }

    private void startDisplay() {
        ButtonAdapter imageAdapter = new ButtonAdapter(this);
        GridView gridView= findViewById(R.id.gridview);

        getSizeParrilla();
        gridView.getLayoutParams().height = height;
        gridView.getLayoutParams().width = width;
        gridView.requestLayout();


        gridView.setBackgroundColor(Color.BLUE);
        gridView.setAdapter(imageAdapter);

        gridView.setNumColumns(PreStartActivity.SIZE);

    }

    private void setGrid() {

        generatedReference=new MSGeneratorMap(PreStartActivity.SIZE,entropy);
        generatedReference.generate();

        referenceMap = generatedReference.getBoard();

        tiles2 = new ArrayList<>();


        for (int i = 0; i <referenceMap.length ; i++) {
            for (int j = 0; j < referenceMap[0].length; j++) {
                tiles2.add(new Tile(referenceMap[i][j]));
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore instance

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putParcelable("GAME", (Parcelable) tiles2);

        ArrayList<String> saved = new ArrayList<>();

        for (int i = 0; i < referenceMap.length; i++){
            for (int j = 0; j < referenceMap.length; j++){
                saved.add(referenceMap[i][j]);
            }
        }
        outState.putParcelable("MAP", (Parcelable) saved);
        //outstate
    }

    public int getScreenWidth(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }
    public int getScreenHeight(@NonNull Context context) {
        // Status bar+actionbar
        int status_bar = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            status_bar = getResources().getDimensionPixelSize(resourceId);
        }



        int action_bar = 0;
        int actionBarHeight = getSupportActionBar().getHeight();
        if (actionBarHeight != 0){
            action_bar= actionBarHeight;
        }

        final TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        action_bar= actionBarHeight;



        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }


        return displayMetrics.heightPixels - action_bar -status_bar;
    }


    public static void time() {
        if (time) {
            layoutTemps.setVisibility(View.VISIBLE);
            textView.setText("Temps: " + temps);
        }
    }
}