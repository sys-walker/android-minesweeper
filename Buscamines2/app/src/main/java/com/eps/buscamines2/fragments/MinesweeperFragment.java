package com.eps.buscamines2.fragments;

import android.app.Activity;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.eps.buscamines2.R;
import com.eps.buscamines2.activities.*;
import com.eps.buscamines2.adapters.*;
import com.eps.buscamines2.util.*;
import java.util.Locale;


import static com.eps.buscamines2.util.Constants.*;

public class MinesweeperFragment extends Fragment {
    // For Debug
    public static final String TAG = "MinesweeperFragment";
    public static final String TAG_EVENTS = "MinesweeperEvents";

    // countdown and stopwatch
    public TextView textViewCountDown;

    //countdown globals
    public static CountDownTimer mCountDownTimer;
    private boolean isCountDownRunning;
    private long timeLeftInMillis = 5*SECONDS;
    private long currentTime;
    public boolean endGameNoTimeout=false;


    //
    public static Bundle Extras=null;
    public Activity activity;


;

    private MinesweeperEvents listener;
    private MSGeneratorMap generator;

    private View screen;
    private int MS_size;
    private double MS_entropy;
    private boolean MS_countdown;
    private int tilesDescovered;

    public int getTilesDescovered() {
        return tilesDescovered;
    }

    public MSGeneratorMap getGenerator() {
        return generator;
    }

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
            activity= (Activity) context;
            Intent in = ((Activity) context).getIntent();
            if (in!=null && in.getExtras()!=null){
                Extras = in.getExtras();
                MS_size = Extras.getInt(PRESTART_SIZE);
                MS_entropy = Extras.getDouble(PRESTART_ENTROPY);
                MS_countdown = Extras.getBoolean(PRESTART_COUNTDOWN);

                Log.d(TAG+":OnAttach","Extras { Username = "+ Extras.getString(PRESTART_USERNAME) +" | "+
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
        setTextviews(savedInstanceState);
        if (savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }else{
            initializeGame();
            if(MS_countdown) startTimer();
        }
        textViewCountDown = screen.findViewById(R.id.crono);
        setGrid(savedInstanceState);
        Log.i(TAG,MS_countdown+":Countdown");

    }

    private void initializeGame() {
        generator = new MSGeneratorMap(MS_size,MS_entropy).generate();
        tilesDescovered = generator.getFullSize();
        //MS_countdown on attach initialization
        //put generator in bundle to Pass to fragment
        Extras.putParcelable(MINESWEEPER_MAP,generator);
    }


    private void setTextviews(Bundle savedInstanceState) {
        setTimerTextViews();
        setGameTextViews(savedInstanceState);
    }

    private void setGameTextViews(Bundle savedInstanceState) {
        //INFO
        if (savedInstanceState!=null){
            Log.d(TAG+":setGameTextViews","Recreated {Size="+tilesDescovered+"}");
        }else{
            Log.d(TAG+":setGameTextViews","Created {Size="+tilesDescovered+"}");
        }
        TextView remaining = screen.findViewById(R.id.remaining_tiles);
        remaining.setText(String.valueOf(tilesDescovered));
    }


    private void setTimerTextViews() {
        TextView titleView = screen.findViewById(R.id.textView0);

        if (MS_countdown){
            titleView.setText(getString(R.string.countdown_text));
        }else{
            titleView.setText(getString(R.string.crono_text));
        }
    }

    private void startTimer() {
        currentTime = System.currentTimeMillis() + timeLeftInMillis;
        mCountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                isCountDownRunning = false;
                gameOver();
            }
        }.start();
        isCountDownRunning = true;
    }


    private void gameOver() {
        if (!endGameNoTimeout){
            Intent in= new Intent(activity, MailSender.class);
            Extras.putInt(GAME_RESULT_KEY,GAME_TIMEOUT);
            activity.startActivity(in);
            activity.finish();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeLeftFormatted);
    }


    private void setGrid(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG+":onViewCreated","Generator Restored{Size="+generator.getSize()+"|"+
                    "Num bombs = "+generator.get_num_bombs()+"|"+
                    "% Bombs= "+generator.get_percentage_mines()+"}");
        }else {
            Log.d(TAG+":onViewCreated","Generator Not exists, generating new Map{Size="+generator.getSize()+"|"+
                    "Num bombs = "+generator.get_num_bombs()+"|"+
                    "% Bombs= "+generator.get_percentage_mines()+"}");
        }

        GridView gridView= screen.findViewById(R.id.minesweeperGridview);
        
        gridView.setBackgroundColor(Color.parseColor("#8b8589"));
        gridView.setAdapter(new ButtonAdapter(getContext(),generator.getSize(),listener,this));
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
            tilesDescovered = savedInstanceState.getInt(TILES_DISCOVERED);
            MS_countdown= savedInstanceState.getBoolean(COUNTDWN_BOOLEAN);


            timeLeftInMillis = savedInstanceState.getLong(COUNTDOWN_MILIS_LEFT);
            isCountDownRunning = savedInstanceState.getBoolean(COUNTDOWN_TIMMER_RUNNING);
            if (isCountDownRunning) {
                currentTime = savedInstanceState.getLong(COUNTDOWN_END_TIME);
                timeLeftInMillis = currentTime - System.currentTimeMillis();
            }
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull  Bundle outState) {
        outState.putParcelable(MINESWEEPER_MAP,generator);
        outState.putInt(TILES_DISCOVERED,tilesDescovered);
        outState.putBoolean(COUNTDWN_BOOLEAN,MS_countdown);

        outState.putLong(COUNTDOWN_MILIS_LEFT, timeLeftInMillis);
        outState.putBoolean(COUNTDOWN_TIMMER_RUNNING, isCountDownRunning);
        outState.putLong(COUNTDOWN_END_TIME, currentTime);
        super.onSaveInstanceState(outState);
    }


    private void onRestoreInstanceState(Bundle savedInstanceState) {
        tilesDescovered = savedInstanceState.getInt(TILES_DISCOVERED);
        generator= savedInstanceState.getParcelable(MINESWEEPER_MAP);
        MS_countdown= savedInstanceState.getBoolean(COUNTDWN_BOOLEAN);
        if(MS_countdown){
            timeLeftInMillis = savedInstanceState.getLong(COUNTDOWN_MILIS_LEFT);
            isCountDownRunning = savedInstanceState.getBoolean(COUNTDOWN_TIMMER_RUNNING);
            updateCountDownText();
            if (isCountDownRunning) {
                currentTime = savedInstanceState.getLong(COUNTDOWN_END_TIME);
                timeLeftInMillis = currentTime - System.currentTimeMillis();
                startTimer();
            }
        }
    }

    public void undescoveredTiles() {
        TextView remaining = screen.findViewById(R.id.remaining_tiles);
        Log.i(TAG,"was: "+tilesDescovered );
        tilesDescovered--;
        remaining.setText(""+tilesDescovered);
        Log.i(TAG,"Now we have: "+tilesDescovered );

    }

    public interface MinesweeperEvents{
        void onEventIsDetected(String event_text);
    }
    public  void setOnEventListener(MinesweeperEvents listener){
        this.listener=listener;
    }
    public boolean getMS_countdown(){
        return MS_countdown;
    }
}