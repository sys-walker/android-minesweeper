package com.eps.buscamines;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MyOnClickListener implements View.OnClickListener {

    int position;

    public MyOnClickListener(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {

        Button b = (Button) view;

        ArrayList<Tile> game = Minesweeper.tiles;
        Tile actualTile = game.get(position);
        if(checkBomb(actualTile)){
            b.setText("B");
        }else{
            //int surrounding = checkSurrounding(game);
            //en comptes de NB haura de tornar el numero volgut
            b.setText("NB");
        }
        b.setClickable(false);




    }

    private int checkSurrounding(ArrayList<Tile> game) {

        int possibleBomb = 0;

        if(game.get(position - PreStartActivity.SIZE - 1).bomb) {
            possibleBomb++;
        }

        if (game.get(position - PreStartActivity.SIZE).bomb) {
            possibleBomb++;

        }
        if (game.get(position - PreStartActivity.SIZE + 1).bomb) {
            possibleBomb++;
        }

        if(game.get(position - PreStartActivity.SIZE).bomb){

        }



        return possibleBomb;
    }

    private boolean checkBomb(Tile actualTile) {
        if(actualTile.bomb){
            return true;
        }else {
            return false;
        }
    }

}
