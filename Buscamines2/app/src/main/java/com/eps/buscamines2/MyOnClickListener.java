package com.eps.buscamines2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.eps.buscamines2.activities.MailSender;
import com.eps.buscamines2.fragments.MinesweeperFragment;
import com.eps.buscamines2.util.MSGeneratorMap;
import com.eps.buscamines2.fragments.MinesweeperFragment.*;
import com.eps.buscamines2.util.MSGeneratorMap.*;

public class MyOnClickListener implements View.OnClickListener  {
    private final int size;
    private MinesweeperEvents listener;

    private Context context;
    private  int pos;
    Button button;
    MSGeneratorMap generated;
    public MyOnClickListener(int position, Context mContext, Button button, int size, MSGeneratorMap generated, MinesweeperEvents listener) {
        this.pos=position;
        this.context=mContext;
        this.button=button;
        this.size=size;
        this.generated = generated;
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        //
        //Given  Matrix[N][N]   --- acces  ------------> Matrix[x][y]
        //                                                     ^
        //                                                     | equivalents
        //                                                     V
        //Give Array[N*N]       --> access as Matrix --> Array [(N*x)+y]
        //coordinates from given position of Array
        //x = position / N :Integer;
        //y = position % N :Integer;
        button.setText(generated.getBoard()[pos]);
        button.setText(generated.getBoard()[pos]);
        if (generated.getBoard()[pos].equals("B")) {
            button.setBackgroundColor(Color.RED);
            //start_Mail_sender();
        }else {
            button.setBackgroundColor(Color.LTGRAY);
        }


        listener.onEventIsDetected("LOG "+new Point<Integer,Integer>((pos/generated.getSize()),(pos%generated.getSize())).toString() +"");
        button.setClickable(false);

        generated.getNonCovered()[pos]=true;
    }

    private void start_Mail_sender() {
        Intent in = new Intent(context, MailSender.class);
        context.startActivity(in);
        ((Activity) context).finish();
    }
}
