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

    private Activity activity;
    private ArrayList<String> listDatos;
    private BasicLogAdapter adapter;
    private Bundle bundle2;

    static final public String BUNDLE_LOGGER="BUNDLE_LOGGER";




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
        outState.putBundle(BUNDLE_LOGGER,bundle2);
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
            bundle2 = savedInstanceState.getBundle(BUNDLE_LOGGER);
        }
        super.onViewStateRestored(savedInstanceState);
    }



    private String createHeader(Bundle bundle) {
        String header=getString(R.string.username_log)+bundle.getString(PRESTART_USERNAME)+" | " +
                getString(R.string.cells_log)+ (bundle.getInt(PRESTART_SIZE) *bundle.getInt(PRESTART_SIZE)  )+" | " +
                getString(R.string.mines_entropy)+(int)((MSGeneratorMap)bundle.getParcelable(MINESWEEPER_MAP)).get_percentage_mines()+" | " +
                getString(R.string.mines_num)+((MSGeneratorMap)bundle.getParcelable(MINESWEEPER_MAP)).get_num_bombs()+" | " +
                getString(R.string.time_boolean)+((bundle.getBoolean(PRESTART_COUNTDOWN))? getString(R.string.on):getString(R.string.off));
        Log.i("LOG...", "createHeader: "+header);
        return header;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView= view.findViewById(R.id.initial_info);






        if (savedInstanceState != null) {
           listDatos = savedInstanceState.getStringArrayList(BASICLOG_ADAPTER_KEY);
           bundle2 = savedInstanceState.getBundle(BUNDLE_LOGGER);


        }else {
           listDatos = new ArrayList<>();
           bundle2 = MinesweeperFragment.Extras;
        }
        String log_header=createHeader(bundle2); //MODIFIED
        textView.setText(log_header);


        LinearLayoutManager linearLayout = new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recyclerId);
        recycler.setLayoutManager(linearLayout);

        adapter = new BasicLogAdapter(activity.getBaseContext(),listDatos);
        recycler.setAdapter(adapter);


    }




    public void addBasicLog(String s) {
        //receiver of Events manager
        listDatos.add(0,s);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}