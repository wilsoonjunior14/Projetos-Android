package com.example.wilson.wcomercial.View_Produtos;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Altera_Produtos extends AppCompatActivity {

    private Button atualizar;
    private EditText etnome,etcodigo,etdescricao,etquantidade,etpreco;
    private Spinner sptipo;
    private TextView texto_inicial;
    public static JSONObject objeto;
    private String url_update =""+Init.url+"altera_produto.php?";

    private String url_busca_produto=""+Init.url+"buscar_produto.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera__produtos);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        atualizar = (Button) findViewById(R.id.cadastrar);
        sptipo = (Spinner) findViewById(R.id.sptipo);
        etcodigo = (EditText) findViewById(R.id.etcodigo_produto);
        etnome = (EditText) findViewById(R.id.etnome_produto);
        etdescricao = (EditText) findViewById(R.id.etdescricao_produto);
        etquantidade = (EditText) findViewById(R.id.etquantidade);
        etpreco = (EditText) findViewById(R.id.etpreco);
        texto_inicial = (TextView) findViewById(R.id.texto_inicial);

        String tipos[] = {"Maquiagens","Hidratantes","Shampoos","Bijouterias"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Altera_Produtos.this,android.R.layout.simple_spinner_dropdown_item,tipos);
        sptipo.setAdapter(adapter);
        etcodigo.setEnabled(false);
        atualizar.setText("Atualizar");
        texto_inicial.setText("Menu - Produtos - Alterações");
        if(objeto == null){
            reset_fields();
        }else{
            altera_fields(objeto);
        }
    }

    public void cadastrar(View v){
        try {
            String codigo = etcodigo.getText().toString();
            String nome = etnome.getText().toString();
            String descricao = etdescricao.getText().toString();
            String tipo = sptipo.getSelectedItem().toString();
            int quantidade = Integer.parseInt(etquantidade.getText().toString());
            double preco = Double.parseDouble(etpreco.getText().toString());
            String new_url = ""+url_update+"codigo="+codigo+"&nome="+nome+""+
                    "&descricao="+descricao+"&tipo="+tipo+"&quantidade="+quantidade+"&preco="+preco+"";
            RequestQueue request = Volley.newRequestQueue(Altera_Produtos.this);
            StringRequest stringrequest = new StringRequest(Request.Method.POST, new_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        int resposta = Integer.parseInt(response);
                        if(resposta == 1) {
                            Toast.makeText(Altera_Produtos.this, "PRODUTO ALTERADO COM SUCESSO", Toast.LENGTH_LONG).show();
                            reset_fields();
                        }else{
                            Toast.makeText(Altera_Produtos.this,"ERRO NA ALTERAÇÃO DOS DADOS",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(Altera_Produtos.this,response,Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Altera_Produtos.this,"ERRO DE CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
                }
            });
            request.add(stringrequest);
        }catch (Exception e){
            Toast.makeText(Altera_Produtos.this,"DADOS INVÁLIDOS",Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar(View v){
        Altera_Produtos.this.finish();
        startActivity(new Intent(this, Produtos.class));
    }

    public void capturacodigo(View v){
        Intent ir  = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(ir,0);
    }

    public void reset_fields(){
        etcodigo.setText("");
        etnome.setText("");
        etdescricao.setText("");
        etpreco.setText("");
        etquantidade.setText("");
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        objeto = null;
        Altera_Produtos.this.finish();
        startActivity(new Intent(this,Produtos.class));
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent ir){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                try {
                    String content = ir.getStringExtra("SCAN_RESULT");
                    String new_url = url_busca_produto + "codigo=" + content + "";
                    RequestQueue request = Volley.newRequestQueue(Altera_Produtos.this);
                    StringRequest stringrequest = new StringRequest(Request.Method.GET, new_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                objeto = new JSONObject(array.getString(0));
                                altera_fields(objeto);
                            } catch (JSONException e) {
                                Toast.makeText(Altera_Produtos.this,"ERRO COM AS INFORMAÇÕES JSON",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Altera_Produtos.this,"ERRO DE CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
                        }
                    });
                    request.add(stringrequest);
                }catch (Exception e){
                    Toast.makeText(Altera_Produtos.this,"ERRO",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void altera_fields(JSONObject objeto){
        try {
            etcodigo.setText(objeto.getString("codigo"));
            etnome.setText(objeto.getString("nome"));
            etdescricao.setText(objeto.getString("descricao"));
            etquantidade.setText(objeto.getString("quantidade"));
            etpreco.setText(objeto.getString("preco"));
        }catch (Exception e){
            Toast.makeText(Altera_Produtos.this,"ERRO COM AS INFORMAÇÕES JSON",Toast.LENGTH_LONG).show();
        }
    }

}
