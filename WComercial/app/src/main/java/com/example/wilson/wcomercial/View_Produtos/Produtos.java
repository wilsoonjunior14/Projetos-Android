package com.example.wilson.wcomercial.View_Produtos;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;

import com.example.wilson.wcomercial.Menu;
import com.example.wilson.wcomercial.R;

public class Produtos extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void novoproduto(View v){
        startActivity(new Intent(this,Novo_Produto.class));
    }

    public void listar(View v){
        startActivity(new Intent(this,Lista_Produtos.class));
    }

    public void venderprodutos(View v){
        startActivity(new Intent(this,Venda_Produto.class));
    }

    public void configprodutos(View v){
        startActivity(new Intent(this,Configuracao_Produto.class));
    }

    public void alterar_produto(View v){
        startActivity(new Intent(this,Altera_Produtos.class));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this, Menu.class));
    }
}
