package com.example.wilson.wcomercial.View_Produtos;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wilson.wcomercial.Init;
import com.example.wilson.wcomercial.R;
import com.example.wilson.wcomercial.VolleyRequest;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Novo_Produto extends AppCompatActivity {

    private EditText etcodigo_produto,etnome_produto,etdescricao_produto,etquantidade,etpreco;
    private Spinner sptipo;
    private String cadastrar_produto = ""+Init.url+"cadastrar_produto.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo__produto);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etcodigo_produto = (EditText) findViewById(R.id.etcodigo_produto);
        sptipo = (Spinner) findViewById(R.id.sptipo);
        etnome_produto = (EditText) findViewById(R.id.etnome_produto);
        etdescricao_produto = (EditText) findViewById(R.id.etdescricao_produto);
        etpreco = (EditText) findViewById(R.id.etpreco);
        etquantidade = (EditText) findViewById(R.id.etquantidade);

        String tipos[] = {"Maquiagens","Hidratantes","Shampoos","Bijouterias"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Novo_Produto.this,android.R.layout.simple_spinner_dropdown_item,tipos);
        sptipo.setAdapter(adapter);
        GetCodigoBarras();
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String resultado = intent.getStringExtra("SCAN_RESULT");
                etcodigo_produto.setText(resultado);
            }
        }
    }

    public void reset_fields(){
        etcodigo_produto.setText("");
        etnome_produto.setText("");
        etdescricao_produto.setText("");
        etpreco.setText("");
        etquantidade.setText("");
    }

    public void capturacodigo(View v){
        GetCodigoBarras();
    }

    public void cadastrar(View v){
        try {
            String codigo = etcodigo_produto.getText().toString();
            String nome = etnome_produto.getText().toString();
            String descricao = etdescricao_produto.getText().toString();
            String tipo = sptipo.getSelectedItem().toString();
            double preco = Double.parseDouble(etpreco.getText().toString());
            int quantidade = Integer.parseInt(etquantidade.getText().toString());
            String url = cadastrar_produto+"codigo="+codigo+"&nome="+nome+"&descricao="+descricao+"&tipo="+tipo+"&preco="+preco+"&quantidade="+quantidade+"";
            RequestQueue request = Volley.newRequestQueue(Novo_Produto.this);
            StringRequest stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int value = Integer.parseInt(response);
                    if(value==1){
                        Toast.makeText(Novo_Produto.this,"PRODUTO CADASTRADO COM SUCESSO!",Toast.LENGTH_LONG).show();
                        reset_fields();
                    }else{
                        Toast.makeText(Novo_Produto.this,"DADOS INVÁLIDOS!",Toast.LENGTH_LONG).show();
                        reset_fields();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Novo_Produto.this,"ERRO NA CONEXÃO COM O SERVIDOR!",Toast.LENGTH_LONG).show();
                }
            });
            request.add(stringrequest);
        }catch (Exception e){
            Toast.makeText(Novo_Produto.this,"VERIFIQUE AS INFORMAÇÕES OU SUA CONEXÃO COM A INTERNET E TENTE NOVAMENTE!",Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar(View v){
        startActivity(new Intent(this,Produtos.class));
        Novo_Produto.this.finish();
    }

    public void GetCodigoBarras(){
        Intent ir = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(ir,0);
    }



}
