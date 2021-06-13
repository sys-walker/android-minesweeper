package com.eps.buscamines2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eps.buscamines2.R;

import java.util.ArrayList;

public class GameLogAdapter extends RecyclerView.Adapter<GameLogAdapter.ViewHolderLog> {
    private final Context context;
    private ArrayList<String> listOfLogs;

    public GameLogAdapter(Context context, ArrayList<String> listDatos) {
        this.context = context;
        this.listOfLogs = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderLog onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        //enlaza adaptaor con itemlist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolderLog(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameLogAdapter.ViewHolderLog holder, int position) {
        // comunica entre adaptador i ViewHolderDatos
        holder.putStringToView(listOfLogs.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfLogs.size();
    }

    public static class ViewHolderLog extends RecyclerView.ViewHolder {
        TextView cardviewText;
        public ViewHolderLog(@NonNull  View itemView) {
            super(itemView);
            //links ViewHolder with CardView
            cardviewText= itemView.findViewById(R.id.idDato);
        }
        public void putStringToView(String game_log_string) {
            cardviewText.setText(game_log_string);
        }
    }
}
