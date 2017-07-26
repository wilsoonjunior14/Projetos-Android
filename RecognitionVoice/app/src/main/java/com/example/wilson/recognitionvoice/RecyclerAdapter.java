package com.example.wilson.recognitionvoice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 15/03/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter{

    Context context;
    List<Nota> notas;

    public RecyclerAdapter(Context context, ArrayList<Nota> lista_notas) {
        this.context = context;
        this.notas = lista_notas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_notes,parent,false);
        RecyclerHolder hold = new RecyclerHolder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder h = (RecyclerHolder) holder;
        h.update_nome_nota.setText(notas.get(position).getNome_nota());
        h.update_data_inicio.setText(notas.get(position).getData_inicio());
        h.update_data_lembrete.setText(notas.get(position).getData_lembrete());
        h.texto = notas.get(position).getTexto();
        h.id.setText(""+notas.get(position).getId()+"");
        h.position = position;
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }
}
