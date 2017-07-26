package com.example.wilson.pedidoson;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView massas,petiscos,melhores_pedidos;
    static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("PEDIDOSON APP");
        toolbar.setSubtitle("Total do Pedido: R$0");
        setSupportActionBar(toolbar);

        Dados.load_data();
        TabHost tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("tab1");
        tab1.setIndicator("Comidas");
        tab1.setContent(R.id.tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("tab2");
        tab2.setIndicator("Bebidas");
        tab2.setContent(R.id.tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("tab3");
        tab3.setIndicator("Pedido");
        tab3.setContent(R.id.tab3);

        tab.addTab(tab1);
        tab.addTab(tab2);
        tab.addTab(tab3);

        massas = (RecyclerView) findViewById(R.id.recycler_massas);
        massas.setHasFixedSize(true);
        massas.setAdapter(new RecyclerAdapter(this,Dados.getProdutos()));
        massas.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        petiscos = (RecyclerView) findViewById(R.id.recycler_petiscos);
        petiscos.setHasFixedSize(true);
        petiscos.setAdapter(new RecyclerAdapter(this,Dados.getProdutos()));
        petiscos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

}
