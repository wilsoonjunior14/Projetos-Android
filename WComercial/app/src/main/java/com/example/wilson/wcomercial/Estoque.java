package com.example.wilson.wcomercial;

import android.app.IntentService;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Estoque extends ListActivity{

    private String nomes[] = new String[]{"codigo","nome","preco","quantidade","lucroestimado","lucroesperado","imagem"};
    private int ids[] = new int[]{R.id.estoque_codigo,R.id.estoque_nome,R.id.estoque_preco,R.id.estoque_quantidade,
    R.id.estoque_lucroestimado,R.id.estoque_lucroesperado,R.id.estoque_imagem};
    private List<Map<String,Object>> lista_produtos = new ArrayList<Map<String,Object>>();
    private String url = ""+Init.url+"buscar_produtos_vendidos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RequestQueue request = Volley.newRequestQueue(Estoque.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        addLista(new JSONObject(array.getString(i)));
                    }
                    SimpleAdapter simple = new SimpleAdapter(Estoque.this,lista_produtos,R.layout.activity_estoque,nomes,ids);
                    setListAdapter(simple);
                }catch (Exception e){
                    Toast.makeText(Estoque.this,"DADOS INVÁLIDOS",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Estoque.this,"ERRO NA CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

    public void addLista(JSONObject objeto) throws JSONException {
        Map<String,Object> item = new HashMap<String,Object>();
        item.put("codigo","Código: "+objeto.getString("codigo"));
        item.put("nome","Nome: "+objeto.getString("nome"));
        item.put("preco","Preço: R$"+objeto.getString("preco"));
        item.put("quantidade","Quantidade Restante: "+objeto.getString("quantidade_restante")+" unidade(s)");
        double preco = Double.parseDouble(objeto.getString("preco"));
        int quantidade_vendido = Integer.parseInt(objeto.getString("quantidade"));
        int quantidade_restante = Integer.parseInt(objeto.getString("quantidade_restante"));
        double lucroestimado = preco * quantidade_vendido;
        double lucroesperado = preco * (quantidade_restante + quantidade_vendido);
        item.put("lucroestimado","Lucro Estimado Obtido: R$"+lucroestimado);
        item.put("lucroesperado","Lucro Esperado: R$"+lucroesperado);
        item.put("imagem",R.drawable.seta);
        lista_produtos.add(item);
    }

}
