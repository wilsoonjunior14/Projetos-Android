package com.example.wilson.wcomercial.View_Vendas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wilson.wcomercial.Init;
import com.example.wilson.wcomercial.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wilson on 08/02/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<Map<String,String>> lista = new ArrayList<Map<String,String>>();

    public RecyclerAdapter(Context context, List<Map<String,String>> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lista_vendas,parent,false);
        RecyclerHolder myholder = new RecyclerHolder(v);
        return myholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder my = (RecyclerHolder) holder;
        my.data.setText(lista.get(position).get("data"));
        my.codigo_nome.setText(lista.get(position).get("nome_codigo_descricao"));
        my.quantidade.setText(lista.get(position).get("quantidade"));
        my.preco.setText(lista.get(position).get("total"));
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder{

        final TextView data;
        final TextView codigo_nome;
        final TextView quantidade;
        final TextView preco;

        public RecyclerHolder(View itemView) {
            super(itemView);
            data = (TextView) itemView.findViewById(R.id.vendas_data);
            codigo_nome = (TextView) itemView.findViewById(R.id.vendas_codigo_nome);
            quantidade = (TextView) itemView.findViewById(R.id.vendas_quantidade);
            preco = (TextView) itemView.findViewById(R.id.vendas_preco);
        }
    }

}
