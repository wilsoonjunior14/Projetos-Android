package com.example.wilson.wcomercial;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Wilson on 25/01/2017.
 */

public class VolleyRequest {


    public static String getResponse(final Context context, int METHOD, String url){
        final String[] resposta = {""};
        RequestQueue volleyrequest = Volley.newRequestQueue(context);
        StringRequest stringrequest = new StringRequest(METHOD, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                resposta[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"VERIFIQUE SUA CONEXÃO COM A INTERNET E TENTE NOVAMENTE!",Toast.LENGTH_LONG).show();
            }
        });
        volleyrequest.add(stringrequest);
        return resposta[0];
    }


}
