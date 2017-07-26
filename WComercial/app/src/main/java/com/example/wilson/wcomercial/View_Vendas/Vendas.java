package com.example.wilson.wcomercial.View_Vendas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.wilson.wcomercial.R;
import com.example.wilson.wcomercial.View_Produtos.Lista_Produtos;
import com.example.wilson.wcomercial.View_Produtos.Venda_Produto;

public class Vendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void listar_vendas(View v){
        startActivity(new Intent(this, Lista_Vendas.class));
    }

    public void vender_produtos(View v){
        startActivity(new Intent(this, Venda_Produto.class));
    }

    public void produtosvendidos(View v){
        startActivity(new Intent(this,Produtos_Vendidos.class));
    }

}
