package com.eps.buscamines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.Toast;

public class Minesweeper extends AppCompatActivity {
    Button curView = null;
    //BoardParcelable boardParcelable;
    public static int h;
    public static int w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_minesweeper);

        
        ConstraintLayout constraintLayout= findViewById(R.id.parent_layout);

        constraintLayout.post(new Runnable() {

            @Override
            public void run() {
                Minesweeper.h = constraintLayout.getWidth();
                Minesweeper.w = constraintLayout.getHeight();
                //do something cool with width and height
            }

        });
        System.out.println("on create A:"+h+" B:"+w);


        if (savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }else{
           // boardParcelable = new BoardParcelable(PreStartActivity.SIZE);
            int i;
        }
        Display display = getWindowManager().getDefaultDisplay();



        startDisplay();

    }

    private void startDisplay() {
        ButtonAdapter imageAdapter = new ButtonAdapter(this);
        GridView gridView= findViewById(R.id.gridview);


        gridView.setBackgroundColor(Color.BLUE);
        gridView.setAdapter(imageAdapter);

        gridView.setNumColumns(PreStartActivity.SIZE);





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
}