package com.eps.buscamines2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.eps.buscamines2.activities.MailSender;
import com.eps.buscamines2.fragments.MinesweeperFragment;

import com.eps.buscamines2.fragments.MinesweeperFragment.*;
import com.eps.buscamines2.util.MSGeneratorMap.*;
import static com.eps.buscamines2.util.Constants.*;
public class MyOnClickListener implements View.OnClickListener  {
    private final int size;
    private final MinesweeperFragment test_parameter;
    private MinesweeperEvents listener;

    private Context context;
    private  int pos;
    Button button;
    public MyOnClickListener(int position, Context mContext, Button button, int size, MinesweeperEvents listener, MinesweeperFragment test_parameter) {
        this.pos=position;
        this.context=mContext;
        this.button=button;
        this.size=size;
        this.listener=listener;
        this.test_parameter=test_parameter;
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

        button.setText(test_parameter.getGenerator().getBoard()[pos]);

        if (test_parameter.getGenerator().getBoard()[pos].equals("B")) {
            button.setBackgroundColor(Color.RED);
            Toast.makeText(context, R.string.lost, Toast.LENGTH_LONG).show();
            start_Mail_sender();
        }else {
            button.setBackgroundColor(Color.LTGRAY);
        }




        listener.onEventIsDetected(

                context.getString(R.string.chunk_selectedCell)+
                new Point<>((pos/test_parameter.getGenerator().getSize()),(pos%test_parameter.getGenerator().getSize())).toString() +"\n"+
                        "Xx:xx:xx\n" +
                        "yy:yy:yy\n"+
                        ( (test_parameter.getMS_countdown())? context.getString(R.string.chunk_remaining_time)+ "xx\n" : "" )


        );
        button.setClickable(false);

        test_parameter.getGenerator().getNonCovered()[pos]=true;
        test_parameter.undescoveredTiles();

    }

    private void start_Mail_sender() {
        Intent in = new Intent(context, MailSender.class);
        context.startActivity(in);
        ((Activity) context).finish();
    }
}
