package com.eps.buscamines2.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eps.buscamines2.R;
import com.eps.buscamines2.activities.PreStartActivity;
import com.eps.buscamines2.adapters.BasicLogAdapter;
import com.eps.buscamines2.util.MSGeneratorMap;

import java.util.ArrayList;

import static com.eps.buscamines2.util.Constants.*;


public class MinesweeperLogFragment extends Fragment{
    private final String username = PreStartActivity.username;
    private Activity activity;
    private ArrayList<String> listDatos;
    private BasicLogAdapter adapter;




    public MinesweeperLogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(BASICLOG_ADAPTER_KEY,listDatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minesweeper_log, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listDatos = savedInstanceState.getStringArrayList(BASICLOG_ADAPTER_KEY);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView= view.findViewById(R.id.initial_info);
        String log_header=createHeader(MinesweeperFragment.generator);
        textView.setText(log_header);

        if (savedInstanceState != null) {
           listDatos = savedInstanceState.getStringArrayList(BASICLOG_ADAPTER_KEY);

        }else {
           listDatos = new ArrayList<>();
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recyclerId);
        recycler.setLayoutManager(linearLayout);

        adapter = new BasicLogAdapter(activity.getBaseContext(),listDatos);
        recycler.setAdapter(adapter);


    }

    private String createHeader(MSGeneratorMap generator) {
        String header=getString(R.string.username_log)+PreStartActivity.username+" | " +
                getString(R.string.cells_log)+generator.getFullSize()+" | " +
                getString(R.string.mines_entropy)+(int)generator.get_percentage_mines()+" | " +
                getString(R.string.mines_num)+generator.get_num_bombs()+" | " +
                "Control de tiempo: "+((PreStartActivity.time_control)? "ON":"OFF");

                ;
        //String a="LOLASO "+instance.getFullSize();
        Log.i("LOG...", "createHeader: "+header);
        return header;
    }


    public void addBasicLog(String s) {
        //receiver of Events manager
        listDatos.add(s);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}