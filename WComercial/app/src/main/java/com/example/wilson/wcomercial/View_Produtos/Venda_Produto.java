package com.example.wilson.wcomercial.View_Produtos;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Venda_Produto extends AppCompatActivity{

    private ListView lista;
    private ArrayList<Produto> lista_produtos = new ArrayList<Produto>();
    private ArrayList<String> produtos = new ArrayList<String>();
    private ArrayList<Produto> lista_original = new ArrayList<Produto>();
    private String url = ""+Init.url+"buscar_produto.php?";
    private String url_update =""+Init.url+"altera_produto.php?";
    private String url_venda = ""+Init.url+"vender_produtos.php?";
    private TextView mensagem,total;
    private double valor_total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda__produto);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lista = (ListView)findViewById(R.id.lista);
        mensagem = (TextView) findViewById(R.id.mensagem);
        total = (TextView) findViewById(R.id.valor_total);
        mensagem.setText("Nenhum produto selecionado.");
        total.setText("Total: R$"+valor_total);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Venda_Produto.this);
                final EditText value = new EditText(Venda_Produto.this);
                value.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialog.setView(value);
                dialog.setTitle("INFORMAÇÕES DA VENDA DO PRODUTO");
                dialog.setMessage("Código:"+lista_produtos.get(i).getCodigo()+"\nNome:"+lista_produtos.get(i).getNome()+"\n"+
                        "Descrição:"+lista_produtos.get(i).getDescricao()+"\nTipo:"+lista_produtos.get(i).getTipo()+"\n"+
                        "Preço p/ Unidade:R$"+lista_produtos.get(i).getPreco()+"\nQuantidade:"+lista_produtos.get(i).getQuantidade()+"");
                dialog.setIcon(R.drawable.configuracoes);
                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int e) {
                                if(!value.getText().toString().equals("")){
                                    final int qtde = Integer.parseInt(value.getText().toString());
                                        String url_busca = url+"codigo="+lista_produtos.get(i).getCodigo()+"";
                                        RequestQueue request = Volley.newRequestQueue(Venda_Produto.this);
                                        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_busca, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                JSONArray array = null;
                                                try {
                                                    array = new JSONArray(response);
                                                    JSONObject objeto = new JSONObject(array.getString(0));
                                                    int original = Integer.parseInt(objeto.getString("quantidade"));
                                                    if(original>=qtde){
                                                        Produto p = lista_produtos.get(i);
                                                        double new_value = p.getPreco() * qtde;
                                                        produtos.set(i, "Código: " + p.getCodigo() + "\nNome: " + p.getNome() + "\nPreço: R$" + new_value + "");
                                                        int old_qtde = lista_produtos.get(i).getQuantidade();
                                                        lista_produtos.get(i).setQuantidade(qtde);
                                                        valor_total = (valor_total - (p.getPreco() * old_qtde)) + (qtde * p.getPreco());
                                                        total.setText("Total: R$" + valor_total);
                                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Venda_Produto.this, android.R.layout.simple_list_item_1, produtos);
                                                        lista.setAdapter(adapter);
                                                    }else{
                                                        Toast.makeText(Venda_Produto.this,"QUANTIDADE SOLICITADA INDISPONÍVEL",Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (JSONException e1) {
                                                    Toast.makeText(Venda_Produto.this,"ERROR NOT FOUND",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Venda_Produto.this,"ERROR NA CONEXÃO COM SERVIDOR",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    request.add(stringrequest);
                                }else{
                                    Toast.makeText(Venda_Produto.this, "INFORMAÇÕES INVÁLIDAS!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                dialog.show();
            }
        });

    }

    public void buscarproduto(View v){
        getCodigoBarras();
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent ir){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String content = ir.getStringExtra("SCAN_RESULT");
                String url_busca = url+"codigo="+content+"";
                final RequestQueue request = Volley.newRequestQueue(Venda_Produto.this);
                StringRequest stringrequest = new StringRequest(Request.Method.GET, url_busca, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject objeto = new JSONObject(array.getString(0));
                            addLista(objeto);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Venda_Produto.this,android.R.layout.simple_list_item_1,produtos);
                            lista.setAdapter(adapter);
                        }catch (Exception e){
                            Toast.makeText(Venda_Produto.this,"ERRO DAS INFORMAÇÕES!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Venda_Produto.this,"ERRO NA CONEXÃO COM O SERVIDOR!",Toast.LENGTH_LONG).show();
                    }
                });
                request.add(stringrequest);
            }
        }
    }

    public void getCodigoBarras(){
        Intent ir = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(ir,0);
    }

    public void addLista(JSONObject objeto){
        Produto p = new Produto();
        try {
            boolean insere = true;
            p.setId(Integer.parseInt(objeto.getString("id")));
            p.setCodigo(objeto.getString("codigo"));
            p.setNome(objeto.getString("nome"));
            p.setDescricao(objeto.getString("descricao"));
            p.setTipo(objeto.getString("tipo"));
            p.setQuantidade(1);
            p.setPreco(Double.parseDouble(objeto.getString("preco")));
            for(int i=0;i<lista_produtos.size();i++){
                if(lista_produtos.get(i).getCodigo().equals(p.getCodigo())){
                    insere = false;
                }
            }
            if(insere && Integer.parseInt(objeto.getString("quantidade"))>0) {
                this.lista_produtos.add(p);
                this.produtos.add("Código: " + p.getCodigo() + "\nNome: " + p.getNome() + "\nPreço: R$" + p.getPreco() + "");
                this.valor_total += p.getPreco();
                this.total.setText("Total :R$" + this.valor_total);
                this.mensagem.setText("Lista de Produtos Selecionados");
            }else{
                Toast.makeText(Venda_Produto.this,"PRODUTO JÁ ENCONTRA-SE NA LISTA\n OU NÃO ESTÁ DISPONÍVEL NO ESTOQUE!",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void vender_produtos(View v){
        if(lista_produtos.size()>0 && habilita_venda()){
            for(int i=0;i<lista_produtos.size();i++){
                String codigo = lista_produtos.get(i).getCodigo();
                int quantidade = lista_produtos.get(i).getQuantidade();
                final String nome = lista_produtos.get(i).getNome();
                final String descricao = lista_produtos.get(i).getDescricao();
                String new_url = ""+url_venda+"codigo="+codigo+"&qtde_vendido="+quantidade+"";
                RequestQueue request = Volley.newRequestQueue(Venda_Produto.this);
                StringRequest stringrequest = new StringRequest(Request.Method.GET, new_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int resposta = Integer.parseInt(response);
                        if(resposta == 1){
                            Toast.makeText(Venda_Produto.this,""+nome+"-"+descricao+" VENDIDO COM SUCESSO!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Venda_Produto.this,""+nome+"-"+descricao+" QUANTIDADE EM ESTOQUE INSUFICIENTE!",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Venda_Produto.this,"ERRO NA CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
                    }
                });
                request.add(stringrequest);
                lista_produtos = new ArrayList<Produto>();
                produtos = new ArrayList<String>();
                this.valor_total = 0;
                this.total.setText("Total :R$" + this.valor_total);
                this.mensagem.setText("Nenhum Produto Selecionado");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Venda_Produto.this,android.R.layout.simple_list_item_1,produtos);
                lista.setAdapter(adapter);
            }
        }else{
            Toast.makeText(Venda_Produto.this,"NENHUM PRODUTO SELECIONADO!",Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar(View v){
        Venda_Produto.this.finish();
        startActivity(new Intent(this,Produtos.class));
    }

    public boolean habilita_venda(){
        boolean habilita = true;
        for(int i=0;i<lista_produtos.size();i++){
            if(lista_produtos.get(i).getQuantidade() <= 0){
                habilita = false;
            }
        }
        return habilita;
    }


}
