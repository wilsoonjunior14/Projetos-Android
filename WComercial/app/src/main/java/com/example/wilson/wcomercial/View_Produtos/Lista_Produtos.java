package com.example.wilson.wcomercial.View_Produtos;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.wilson.wcomercial.VolleyRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_Produtos extends ListActivity{

    private ArrayList<Produto> produtos;
    private List<Map<String,Object>> lista_produtos = new ArrayList<Map<String,Object>>();
    private String url = ""+Init.url+"buscar_produtos.php?opcao=";
    private String opcao ="opcao=";
    private JSONArray array;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    final JSONObject objeto = new JSONObject(array.getString(i));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Lista_Produtos.this);
                    dialog.setMessage("Código: "+objeto.getString("codigo")+"\nNome: "+objeto.getString("nome")+"\n"+
                            "Descrição: "+objeto.getString("descricao")+"\n Tipo: "+objeto.getString("tipo")+"\n"+
                            "Preço: R$"+objeto.getString("preco")+"\n Quantidade Disponível: "+objeto.getString("quantidade"));
                    dialog.setTitle("INFORMAÇÕES DO PRODUTO");
                    dialog.setIcon(R.drawable.configuracoes);
                    dialog.setPositiveButton("Fechar",null);
                    dialog.setNegativeButton("Alterar Produto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Altera_Produtos.objeto = objeto;
                            Lista_Produtos.this.finish();
                            startActivity(new Intent(Lista_Produtos.this,Altera_Produtos.class));

                        }
                    });
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        shared = getSharedPreferences("configuracoes",0);
        boolean ordem_alfabetica = shared.getBoolean("ordem_alfabetica",false);
        if(ordem_alfabetica){
            opcao ="nome";
        }else{
            opcao ="id";
        }
        url =""+url+""+this.opcao+"";
        RequestQueue request = Volley.newRequestQueue(Lista_Produtos.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject objeto = new JSONObject(array.getString(i));
                        addLista(objeto);
                    }
                    String names[] = new String[]{"codigo","nome","preco","imagem"};
                    int id[] = new int[]{R.id.txtcodigo,R.id.txtnome,R.id.txtpreco,R.id.imagem};
                    SimpleAdapter simple = new SimpleAdapter(Lista_Produtos.this,lista_produtos, R.layout.list,names,id);
                    setListAdapter(simple);
                }catch (Exception e){
                    Toast.makeText(Lista_Produtos.this,"ERRO DE CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Lista_Produtos.this,"erro",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Lista_Produtos.this,Produtos.class));
            }
        });
        request.add(stringrequest);
    }

    public void addLista(JSONObject objeto){
        try {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("codigo","Código: "+objeto.getString("codigo"));
            item.put("nome","Nome: "+objeto.getString("nome"));
            item.put("preco","Preço: R$"+objeto.getString("preco"));
            item.put("imagem",R.drawable.seta);
            int quantidade = Integer.parseInt(objeto.getString("quantidade"));
            boolean produtos_disponiveis = shared.getBoolean("produtos_disponiveis",false);
            if(produtos_disponiveis){
                if(quantidade>0) {
                    this.lista_produtos.add(item);
                }
            }else{
                this.lista_produtos.add(item);
            }
        }catch (Exception e){

        }
    }


}
