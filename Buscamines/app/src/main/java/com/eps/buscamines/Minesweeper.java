package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper extends AppCompatActivity {

    //BoardParcelable boardParcelable;

    public static int SIZE_PIXELS;
    public static int entropy;
    public static ArrayList<Tile> tiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_minesweeper);

        Intent intent = getIntent();
        entropy = intent.getIntExtra("Entropy", 0);

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

        //afegir el numero de bombes per a que randomitzi
        tiles = new ArrayList<>(PreStartActivity.SIZE*PreStartActivity.SIZE);
        int bombMax = PreStartActivity.SIZE*PreStartActivity.SIZE / entropy;
        int actualbomb = 0;

        for(int i = 0; i < PreStartActivity.SIZE; i++){
            for(int j = 0; j < PreStartActivity.SIZE; j++){
                Random rand = new Random();
                boolean r = rand.nextBoolean();
                if(r && actualbomb < bombMax){
                    actualbomb++;
                    tiles.add(new Tile(r));
                }else if(!r){
                    tiles.add(new Tile(r));
                }else {
                    j--;
                }
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
        context.getDisplay().getRealMetrics(displayMetrics);
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

        context.getDisplay().getRealMetrics(displayMetrics);


        return displayMetrics.heightPixels - action_bar -status_bar;
    }
}