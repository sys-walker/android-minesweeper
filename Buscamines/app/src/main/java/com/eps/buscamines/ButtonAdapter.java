package com.eps.buscamines;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;


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
    public View getView( int i, View convertView, ViewGroup viewGroup)
    {


        Button button;


        if (convertView==null){
            button = new Button(this.mContext);

            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            button.setText(""+(i+1));



            button.setOnClickListener(new ActionF());

        }else {
            button = (Button) convertView;
            button.setText(""+(i+1));
            button.setOnClickListener(new ActionF());
        }

       //return imageView;
        return button;
    }

    private static class ActionF implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            b.setText("F");
            b.setClickable(false);


        }
    }
}
