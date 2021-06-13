package com.eps.buscamines2.fragments;

import android.app.Activity;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;

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
    public String currentTimeString;

    //countdown globals
    public static CountDownTimer mCountDownTimer;
    private boolean isCountDownRunning;
    public long timeLeftInMillis = START * SECONDS;
    public long currentTime;
    public boolean endGameNoTimeout = false;
    // stopwatch
    private int milis = 0;
    public boolean running;// Is the stopwatch running?
    private boolean wasRunning;

    //Fragment varibales
    public Activity activity;
    private MinesweeperEvents listener;
    private MSGeneratorMap generator;

    private View screen;
    //Intent values of Prestart activty
    public static Bundle Extras = null;
    private int MS_size;
    private double MS_entropy;
    private boolean MS_countdown;
    private int tilesDescovered;


    public MinesweeperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MinesweeperEvents) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement MockListener");
        }

        if (context instanceof Activity) {
            activity = (Activity) context;
            Intent in = ((Activity) context).getIntent();
            if (in != null && in.getExtras() != null) {
                Extras = in.getExtras();
                MS_size = Extras.getInt(PRESTART_SIZE);
                MS_entropy = Extras.getDouble(PRESTART_ENTROPY);
                MS_countdown = Extras.getBoolean(PRESTART_COUNTDOWN);
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minsweeper, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        screen = view;
        super.onViewCreated(view, savedInstanceState);

        initializeGame();
        setTextviews();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            if (MS_countdown) {
                startCountDownTimer();
            } else {
                startTimer();
            }
        }
        setGrid();
    }

    private void startTimer() {
        running = true;
        runTimer();
    }


    private void runTimer() {
        final Handler handler  = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = milis / 3600;
                int minutes = (milis % 3600) / 60;
                int secs = milis % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours,minutes, secs);
                textViewCountDown.setText(time);
                if (running) {
                    milis++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void initializeGame() {
        generator = new MSGeneratorMap(MS_size, MS_entropy).generate();
        tilesDescovered = generator.getFullSize();
        //MS_countdown on attach initialization
        //put generator in bundle to Pass to fragment
        Extras.putParcelable(MINESWEEPER_MAP, generator);
    }


    private void setTextviews() {
        setTimerTextViews();
        setGameTextViews();
    }

    private void setGameTextViews() {
       //sets remainig tiles
        TextView remaining = screen.findViewById(R.id.remaining_tiles);
        remaining.setText(String.valueOf(tilesDescovered));
    }


    private void setTimerTextViews() {

        textViewCountDown = screen.findViewById(R.id.crono);
        textViewCountDown.setText(String.format(Locale.getDefault(),"%d:%02d:%02d", 0,0,0));

        TextView titleView = screen.findViewById(R.id.textView0);
        if (MS_countdown) {
            titleView.setText(getString(R.string.countdown_text));
        } else {

            titleView.setText(getString(R.string.crono_text));


        }
    }

    private void startCountDownTimer() {
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
        if (!endGameNoTimeout) {
            Intent in = new Intent(activity, MailSender.class);
            Extras.putInt(GAME_RESULT_KEY, GAME_TIMEOUT);
            activity.startActivity(in);
            activity.finish();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        currentTimeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(currentTimeString);
    }


    private void setGrid() {
        // sets Minesweeper Gridview
        GridView gridView = screen.findViewById(R.id.minesweeperGridview);
        gridView.setBackgroundColor(Color.parseColor("#8b8589"));
        gridView.setAdapter(new ButtonAdapter(getContext(), generator.getSize(), listener, this));
        gridView.setNumColumns(generator.getSize());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(MINESWEEPER_MAP, generator);
        outState.putInt(TILES_DISCOVERED, tilesDescovered);
        outState.putBoolean(COUNTDWN_BOOLEAN, MS_countdown);
        outState.putString(TIMER_STRING, currentTimeString);

        outState.putLong(COUNTDOWN_MILIS_LEFT, timeLeftInMillis);
        outState.putBoolean(COUNTDOWN_TIMMER_RUNNING, isCountDownRunning);
        outState.putLong(COUNTDOWN_END_TIME, currentTime);

        outState.putInt(TIMER_MILIS, milis);
        outState.putBoolean(TIMER_RUNNING, running);
        outState.putBoolean(TIMER_WAS_RUNNING, wasRunning);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            generator = savedInstanceState.getParcelable(MINESWEEPER_MAP);
            tilesDescovered = savedInstanceState.getInt(TILES_DISCOVERED);
            MS_countdown = savedInstanceState.getBoolean(COUNTDWN_BOOLEAN);
            currentTimeString = savedInstanceState.getString(TIMER_STRING);

            timeLeftInMillis = savedInstanceState.getLong(COUNTDOWN_MILIS_LEFT);
            isCountDownRunning = savedInstanceState.getBoolean(COUNTDOWN_TIMMER_RUNNING);
            if (isCountDownRunning) {
                currentTime = savedInstanceState.getLong(COUNTDOWN_END_TIME);
                timeLeftInMillis = currentTime - System.currentTimeMillis();
            }
        }

        super.onViewStateRestored(savedInstanceState);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        tilesDescovered = savedInstanceState.getInt(TILES_DISCOVERED);
        generator = savedInstanceState.getParcelable(MINESWEEPER_MAP);
        MS_countdown = savedInstanceState.getBoolean(COUNTDWN_BOOLEAN);
        currentTimeString = savedInstanceState.getString(TIMER_STRING);
        TextView remaining = screen.findViewById(R.id.remaining_tiles);
        remaining.setText(String.valueOf(tilesDescovered));
        if (MS_countdown) {
            timeLeftInMillis = savedInstanceState.getLong(COUNTDOWN_MILIS_LEFT);
            isCountDownRunning = savedInstanceState.getBoolean(COUNTDOWN_TIMMER_RUNNING);
            updateCountDownText();
            if (isCountDownRunning) {
                currentTime = savedInstanceState.getLong(COUNTDOWN_END_TIME);
                timeLeftInMillis = currentTime - System.currentTimeMillis();
                startCountDownTimer();
            }
        } else {
            milis= savedInstanceState.getInt(TIMER_MILIS);
            running= savedInstanceState.getBoolean(TIMER_RUNNING);
            wasRunning= savedInstanceState.getBoolean(TIMER_WAS_RUNNING);
            startTimer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!MS_countdown) {
            wasRunning = running;
            running = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!MS_countdown) {
            if (wasRunning) {
                running = true;
            }
        }
    }

    public void undiscoveredTiles() {
        TextView remaining = screen.findViewById(R.id.remaining_tiles);
        tilesDescovered--;
        remaining.setText(String.valueOf(tilesDescovered));

    }


    public boolean getMS_countdown() {
        return MS_countdown;
    }

    public int getTilesDescovered() {
        return tilesDescovered;
    }

    public MSGeneratorMap getGenerator() {
        return generator;
    }

    public interface MinesweeperEvents {
        void onEventIsDetected(String event_text);
    }

    public void setOnEventListener(MinesweeperEvents listener) {
        this.listener = listener;
    }

}