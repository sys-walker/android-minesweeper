package com.eps.buscamines;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ButtonAdapter extends BaseAdapter {


    private Context mContext;
    private boolean bomb;


    public ButtonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PreStartActivity.SIZE *PreStartActivity.SIZE;
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
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {


        Button button;


        if (convertView==null){
            button = new Button(this.mContext);

            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        }else {
            button = (Button) convertView;
        }

        button.setText("*");
        button.setBackgroundColor(Color.LTGRAY);
        button.setOnClickListener(new MyOnClickListener(position));

        //return imageView;
        return button;
    }
}