package com.eps.buscamines;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MyOnClickListener implements View.OnClickListener {
    int position;

    public MyOnClickListener(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        String[][] reference=Minesweeper.referenceMap;
        //
        //Given  Matrix[N][N]   --- acces  ------------> Matrix[x][y]
        //                                                     ^
        //                                                     | equivalents
        //                                                     V
        //Give Array[N*N]       --> access as Matrix --> Array [(N*x)+y]


        //coordinates from given position of Array
        //x = position / N :Integer;
        //y = position % N :Integer;
        int x = position / reference.length;
        int y = position % reference.length;
        String newText = reference[x][y];

        Button b = (Button) v;

        ArrayList<Tile> game = Minesweeper.tiles2;
        Tile actualTile = game.get(position);

        b.setText(newText);
        if (newText.equals("B")){
            b.setBackgroundColor(Color.RED);
        }else{
            b.setBackgroundColor(Color.LTGRAY);
        }

        b.setClickable(false);

    }
}
