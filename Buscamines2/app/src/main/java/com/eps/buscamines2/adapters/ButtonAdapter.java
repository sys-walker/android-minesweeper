package com.eps.buscamines2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.eps.buscamines2.R;
import com.eps.buscamines2.fragments.MinesweeperFragment;
import com.eps.buscamines2.fragments.MinesweeperFragment.*;
import com.eps.buscamines2.util.MSGeneratorMap;
import com.eps.buscamines2.MyOnClickListener;

public class ButtonAdapter extends BaseAdapter {


    private final MinesweeperEvents listener;
    private final MinesweeperFragment test_parameter;
    private Context mContext;
    private int size;
    MSGeneratorMap generated;

    public ButtonAdapter(Context context, int size, MinesweeperEvents listener, MinesweeperFragment test_parameter) {
        this.mContext = context;
        this.size = size;
       // this.generated = MinesweeperFragment.generator;
        this.listener = listener;
        this.test_parameter=test_parameter;
    }

    @Override
    public int getCount() {
        return size * size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //
        //Given  Matrix[N][N]   --- acces  ------------> Matrix[x][y]
        //                                                     ^
        //                                                     | equivalents
        //                                                     V
        //Give Array[N*N]       --> access as Matrix --> Array [(N*x)+y]
        //coordinates from given position of Array
        //x = position / N :Integer;
        //y = position % N :Integer;

        Button button;

        if (convertView == null) {
            button = new Button(this.mContext);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            button = (Button) convertView;
        }
        if (test_parameter.getGenerator().getNonCovered()[position]) {


            button.setText(test_parameter.getGenerator().getBoard()[position]);

            if (test_parameter.getGenerator().getBoard()[position].equals("B")) {
                button.setBackgroundColor(Color.RED);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
            }
            button.setClickable(false);
        } else {
            button.setText("*");
            button.setBackgroundResource(R.drawable.shape);
            button.setOnClickListener(new MyOnClickListener(position, mContext, button, size, listener,test_parameter));
        }

        return button;
    }
}