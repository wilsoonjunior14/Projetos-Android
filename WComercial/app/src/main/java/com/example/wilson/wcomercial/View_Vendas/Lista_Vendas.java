package com.example.wilson.wcomercial.View_Vendas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wilson.wcomercial.Init;
import com.example.wilson.wcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_Vendas extends AppCompatActivity {

    private RecyclerView recycler;
    private List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
    private String url_listar_vendas=""+ Init.url+"buscar_vendas.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__vendas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        final RequestQueue request = Volley.newRequestQueue(Lista_Vendas.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_listar_vendas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        addLista(new JSONObject(array.getString(i)));
                    }
                    recycler = (RecyclerView) findViewById(R.id.recycler);
                    recycler.setHasFixedSize(true);

                    RecyclerView.LayoutManager manager = new GridLayoutManager(Lista_Vendas.this,2, LinearLayoutManager.VERTICAL,false);
                    recycler.setLayoutManager(manager);

                    RecyclerAdapter adapter = new RecyclerAdapter(Lista_Vendas.this,lista);
                    recycler.setAdapter(adapter);

                } catch (JSONException e) {
                    Toast.makeText(Lista_Vendas.this,"ERRO DOS DADOS",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Lista_Vendas.this,"ERRO NA CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

    public void addLista(JSONObject objeto){
        Map<String,String> item = new HashMap<String,String>();
        try {
            item.put("data","Data: "+objeto.getString("data")+"");
            item.put("nome_codigo_descricao","Código: "+objeto.getString("codigo")+" - Produto: "+objeto.getString("nome")+"\nDescrição: "+objeto.getString("descricao")+" - Tipo: "+objeto.getString("tipo"));
            item.put("quantidade",""+objeto.getString("quantidade")+" Unidade(s) Vendida(s)");
            double preco = Double.parseDouble(objeto.getString("preco"));
            int quantidade = Integer.parseInt(objeto.getString("quantidade"));
            double total = preco*quantidade;
            item.put("total","Total da venda: R$"+total);
            lista.add(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
