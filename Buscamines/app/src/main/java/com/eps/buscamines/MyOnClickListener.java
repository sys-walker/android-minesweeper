package com.eps.buscamines;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyOnClickListener implements View.OnClickListener {

    private final Context context;
    int position;
    int win_=Minesweeper.generatedReference.getMapSize()-Minesweeper.generatedReference.getNUMBOMBS();
    int current =0;

    int temps = 60;
    private TextView textView;

    public MyOnClickListener(int position, Context mContext) {
        this.position = position;
        this.context=mContext;
    }

    @Override
    public void onClick(View v) {

        String[][] reference= Minesweeper.referenceMap;
        //
        //Given  Matrix[N][N]   --- acces  ------------> Matrix[x][y]
        //                                                     ^
        //                                                     | equivalents
        //                                                     V
        //Give Array[N*N]       --> access as Matrix --> Array [(N*x)+y]


        //coordinates from given position of Array
        //x = position / N :Integer;
        //y = position % N :Integer;



        //Creació de TextView del Temps

        Minesweeper.time();

        int x = position / reference.length;
        int y = position % reference.length;
        String newText = reference[x][y];

        Button b = (Button) v;

        ArrayList<Tile> game = Minesweeper.tiles2;
        Tile actualTile = game.get(position);

        b.setText(newText);
        if (newText.equals("B")){
            b.setBackgroundColor(Color.RED);
            System.out.println("BOMBA xD");
            Intent in = new Intent(context,MailSender.class);
            ((Activity) context).finish();
            context.startActivity(in);
        }else{
            b.setBackgroundColor(Color.LTGRAY);
            this.current = this.current +1;
            if (this.current==this.win_){
                Toast.makeText(v.getContext(),"YOU WIN",Toast.LENGTH_SHORT).show();
            }
        }

        b.setClickable(false);

    }
}
