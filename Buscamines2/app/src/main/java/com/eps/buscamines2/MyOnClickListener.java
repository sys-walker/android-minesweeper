package com.eps.buscamines2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.eps.buscamines2.activities.MailSender;
import com.eps.buscamines2.fragments.MinesweeperFragment;

import com.eps.buscamines2.fragments.MinesweeperFragment.*;
import com.eps.buscamines2.util.MSGeneratorMap.*;
import static com.eps.buscamines2.util.Constants.*;
public class MyOnClickListener implements View.OnClickListener  {
    private final int sizeRow;
    private final MinesweeperFragment instance;
    private MinesweeperEvents listener;
    private Context context;
    private  int pos;
    Button button;
    String startTimeMovement;

    public MyOnClickListener(int position, Context mContext, Button button, int sizeRow, MinesweeperEvents listener, MinesweeperFragment test_parameter) {
        this.pos=position;
        this.context=mContext;
        this.button=button;
        this.sizeRow=sizeRow;
        this.listener=listener;
        this.instance=test_parameter;
        this.startTimeMovement=(String)test_parameter.textViewCountDown.getText();
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG", "onClick: "+sizeRow+startTimeMovement);
        //
        //Given  Matrix[N][N]   --- acces  ------------> Matrix[x][y]
        //                                                     ^
        //                                                     | equivalents
        //                                                     V
        //Give Array[N*N]       --> access as Matrix --> Array [(N*x)+y]
        //coordinates from given position of Array
        //x = position / N :Integer;
        //y = position % N :Integer;

        button.setText(instance.getGenerator().getBoard()[pos]);

        if (instance.getGenerator().get_num_bombs()==instance.getTilesDescovered()-1){
            instance.endGameNoTimeout=true;
            MinesweeperFragment.Extras.putInt(GAME_RESULT_KEY,GAME_WIN);
            MinesweeperFragment.Extras.putString(TIME_WINNER,instance.textViewCountDown.getText().toString());
            start_Mail_sender();
        }

        if (instance.getGenerator().getBoard()[pos].equals("B")) {
            instance.endGameNoTimeout=true;
            button.setBackgroundColor(Color.RED);
            Toast.makeText(context, R.string.lost, Toast.LENGTH_LONG).show();

            MinesweeperFragment.Extras.putInt(TILES_LEFT,instance.getTilesDescovered());
            MinesweeperFragment.Extras.putInt(GAME_RESULT_KEY,GAME_LOSE);
            MinesweeperFragment.Extras.putString(LOSE_POINT, "("+(pos / sizeRow)+","+(pos % sizeRow)+")");
            start_Mail_sender();
        }else {
            button.setBackgroundColor(Color.LTGRAY);
        }

        listener.onEventIsDetected(

                context.getString(R.string.chunk_selectedCell)+
                new Point<>((pos/instance.getGenerator().getSize()),(pos%instance.getGenerator().getSize())).toString() +"\n"+
                        startTimeMovement+"\n" +
                        instance.textViewCountDown.getText().toString()+
                        ( (instance.getMS_countdown())?"\n"+ context.getString(R.string.chunk_remaining_time)+ "YY (hardcoded)\n" : "" )
        );
        button.setClickable(false);

        instance.getGenerator().getNonCovered()[pos]=true;
        instance.undescoveredTiles();

    }

    private void start_Mail_sender() {
        Intent in = new Intent(context, MailSender.class);
        context.startActivity(in);
        ((Activity) context).finish();
    }
}
