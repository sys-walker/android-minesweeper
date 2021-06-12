package com.eps.buscamines2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eps.buscamines2.R;

import java.util.ArrayList;

public class BasicLogAdapter extends RecyclerView.Adapter<BasicLogAdapter.ViewHolderBasicLog> {
    private Context context;
    private ArrayList<String> listDatos;

    public BasicLogAdapter(Context context, ArrayList<String> listDatos) {
        this.context = context;
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderBasicLog onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        //enlaza adaptaor con itemlist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolderBasicLog(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicLogAdapter.ViewHolderBasicLog holder, int position) {
        // comunica entre adaptador i ViewHolderDatos
        holder.assignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public static class ViewHolderBasicLog extends RecyclerView.ViewHolder {
        TextView dato;
        public ViewHolderBasicLog(@NonNull  View itemView) {
            super(itemView);
            //enlaza con el textvieww
            dato= itemView.findViewById(R.id.idDato);
        }
        public void assignarDatos(String dato_) {
            Log.wtf("???", "recreated");
            dato.setText(dato_);
        }
    }
}
