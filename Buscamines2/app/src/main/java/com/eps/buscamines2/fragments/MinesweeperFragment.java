package com.eps.buscamines2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.eps.buscamines2.R;
import com.eps.buscamines2.activities.PreStartActivity;
import com.eps.buscamines2.adapters.ButtonAdapter;
import com.eps.buscamines2.util.MSGeneratorMap;

import static com.eps.buscamines2.util.Constants.*;

public class MinesweeperFragment extends Fragment {


    public static final String TAG = "MinesweeperFragment";
    public static final String TAG_EVENTS = "MinesweeperEvents";

    private MinesweeperEvents listener;
    private View screen;
    private int MS_size;
    private double MS_entropy;
    private String MS_username;
    private boolean MS_countdown;
    public static MSGeneratorMap generator;

    public MinesweeperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MinesweeperEvents) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +" Must implement MockListener");
        }
        Log.d(TAG+":OnAttach","Attached listener");

        if (context instanceof Activity){

            Intent in = ((Activity) context).getIntent();
            if (in!=null && in.getExtras()!=null){
                Bundle Extras = in.getExtras();
                MS_username = Extras.getString(PRESTART_USERNAME);
                MS_size = Extras.getInt(PRESTART_SIZE);
                MS_entropy = Extras.getDouble(PRESTART_ENTROPY);
                MS_countdown = Extras.getBoolean(PRESTART_COUNTDOWN);

                Log.d(TAG+":OnAttach","Extras { Username = "+MS_username+" | "+
                        "Size = "+MS_size+" | "+
                        "Countdown = "+((MS_countdown)?"ON":"OFF")+" | "+
                        "Entropy = "+MS_entropy+"%}");
            }else{
                Log.e(TAG+":OnAttach","No extras, intent null");
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minsweeper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        screen = view;
        super.onViewCreated(view, savedInstanceState);
        setGrid(savedInstanceState);
        setTimerTextViews();
    }



    private void setTimerTextViews() {
        TextView titleView= screen.findViewById(R.id.textView0);
        if (MS_countdown){
            titleView.setText(getString(R.string.countdown_text));
        }else{
            titleView.setText(getString(R.string.crono_text));

        }
    }

    private void setGrid(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG+":onViewCreated","Generator Restored{Size="+generator.getSize()+"|"+
                    "Num bombs = "+generator.get_num_bombs()+"|"+
                    "% Bombs= "+generator.get_percentage_mines()+"}");
            generator= savedInstanceState.getParcelable(MINESWEEPER_MAP);
        }else {

            generator = new MSGeneratorMap(MS_size,MS_entropy);
            generator.generate();
            Log.d(TAG+":onViewCreated","Generator Not exists, generating new Map{Size="+generator.getSize()+"|"+
                    "Num bombs = "+generator.get_num_bombs()+"|"+
                    "% Bombs= "+generator.get_percentage_mines()+"}");
        }

        GridView gridView= screen.findViewById(R.id.minesweeperGridview);
        
        gridView.setBackgroundColor(Color.DKGRAY);
        gridView.setAdapter(new ButtonAdapter(getContext(),generator.getSize(),listener));
        gridView.setNumColumns(generator.getSize());
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            generator= savedInstanceState.getParcelable(MINESWEEPER_MAP);
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull  Bundle outState) {
        outState.putParcelable(MINESWEEPER_MAP,generator);
        super.onSaveInstanceState(outState);
    }

    public interface MinesweeperEvents{
        public void onEventIsDetected(String mockparam);
    }
    public  void setOnEventListener(MinesweeperEvents listener){
        this.listener=listener;
    }
}