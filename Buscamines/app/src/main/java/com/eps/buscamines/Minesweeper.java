package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.GridView;

import java.util.ArrayList;

public class Minesweeper extends AppCompatActivity {

    //BoardParcelable boardParcelable;

    public static int SIZE_PIXELS;
    public static double entropy;
    public static ArrayList<Tile> tiles2;
    public static String[][] referenceMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_minesweeper);

        Intent intent = getIntent();
        entropy = intent.getDoubleExtra("Entropy", 0.25);

        SIZE_PIXELS = getSizeParrilla();
        setGrid();






        startDisplay();

    }



    private int getSizeParrilla() {
        int height = getScreenHeight(getBaseContext());
        int width  = getScreenWidth(getBaseContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(getClass().getName(),"Its LANDSCAPE");
            //height sizes
            return height;
        } else {
            Log.i(getClass().getName(),"Its PORTRAIT");

            return width;
        }
    }

    private void startDisplay() {
        ButtonAdapter imageAdapter = new ButtonAdapter(this);
        GridView gridView= findViewById(R.id.gridview);

        gridView.getLayoutParams().height = SIZE_PIXELS;
        gridView.getLayoutParams().width = SIZE_PIXELS;
        gridView.requestLayout();


        gridView.setBackgroundColor(Color.BLUE);
        gridView.setAdapter(imageAdapter);

        gridView.setNumColumns(PreStartActivity.SIZE);

    }

    private void setGrid() {
        //hardcoded
        MSGeneratorMap reference=new MSGeneratorMap(PreStartActivity.SIZE,entropy);
        reference.generate();

        referenceMap = reference.getBoard();

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
}