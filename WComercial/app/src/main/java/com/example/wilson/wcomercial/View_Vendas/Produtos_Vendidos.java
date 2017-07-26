package com.example.wilson.wcomercial.View_Vendas;

import android.app.ListActivity;
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
import com.example.wilson.wcomercial.Model.Produto;
import com.example.wilson.wcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Produtos_Vendidos extends AppCompatActivity{

    private List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
    private String url_listar_vendas=""+ Init.url+"buscar_produtos_vendidos.php?";
    private RecyclerView recycler;

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_produtos__vendidos);
        recycler = (RecyclerView) findViewById(R.id.recycler_produtos_vendidos);
        recycler.setHasFixedSize(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RequestQueue request = Volley.newRequestQueue(Produtos_Vendidos.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_listar_vendas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++) {
                        addLista(new JSONObject(array.getString(i)));
                    }
                    RecyclerAdapter adapter = new RecyclerAdapter(Produtos_Vendidos.this,lista);
                    RecyclerView.LayoutManager manager = new GridLayoutManager(Produtos_Vendidos.this,5, LinearLayoutManager.HORIZONTAL,false);
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(manager);
                } catch (JSONException e) {
                    Toast.makeText(Produtos_Vendidos.this,"DADOS INVÁLIDOS!",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Produtos_Vendidos.this,"ERRO NA CONEXÃO COM O SERVIDOR!",Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addLista(JSONObject objeto){
        try{
            Map<String,String> item = new HashMap<String,String>();
            item.put("data","Código: "+objeto.getString("codigo"));
            item.put("nome_codigo_descricao","Produto: "+objeto.getString("nome")+"");
            item.put("quantidade",""+objeto.getString("quantidade")+" unidade(s) já vendida(s)");
            item.put("total","Preço: R$"+objeto.getString("preco"));
            lista.add(item);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
