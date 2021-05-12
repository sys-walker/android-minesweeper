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

        //ImageView imageView;
        Button button;
        //int heightt = Resources.getSystem().getDisplayMetrics().heightPixels;
        //int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int min = Math.min(heightt,width);
        //System.out.println("A:"+heightt+" B:"+width+" square:"+min);

        //int height =min/PreStartActivity.SIZE;

        if (convertView==null){
            button = new Button(this.mContext);
            //button.setHeight(height);
            //button.setWidth(height);
            //button.setLayoutParams(new ViewGroup.LayoutParams(button.getMeasuredWidth(), button.getMeasuredWidth()));
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText("XD"+i);


            button.setOnClickListener(new ActionF());

            //imageView = new ImageView(this.mContext);
            //imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //imageView.setImageResource(R.drawable.fitxa_vermell);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }else {
            button = (Button) convertView;
            //button.setHeight(height);
            //button.setWidth(height);
            button.setText("XD"+i);
            button.setOnClickListener(new ActionF());

            //imageView= (ImageView) convertView;
            //imageView.setImageResource(R.drawable.fitxa_vermell);
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
