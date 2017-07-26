package com.example.wilson.wcomercial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Servico extends BroadcastReceiver {
    boolean notifica;
    private String url_produto= ""+Init.url+"buscar_produtos.php?opcao=nome";

    public Servico() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        notifica = false;
        RequestQueue request = Volley.newRequestQueue(context);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_produto, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject objeto = new JSONObject(array.getString(i));
                        int qtde = Integer.parseInt(objeto.getString("quantidade"));
                        if(qtde <= 10){
                            notifica = true;
                        }
                    }
                    if(notifica) {
                        CriaNotificacao notify = new CriaNotificacao(context, "Alguns de seus produtos estão acabando!", "Consulte seu estoque", new Intent(context, Relatorios.class),"Consulte seu estoque");
                        notify.executaNotificacao();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"ERRO NA CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);

    }
}
