package com.example.wilson.wcomercial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wilson.wcomercial.View_Produtos.Produtos;
import com.example.wilson.wcomercial.View_Vendas.Vendas;

public class Menu extends AppCompatActivity{
    private Toolbar toolbar;
    private TextView usuario;
    private LinearLayout main;
    private SharedPreferences shared;
    private Button bt_menu_produtos, bt_menu_vendas, bt_menu_relatorios, bt_menu_estoque, bt_menu_configuracoes, bt_menu_sobre;
    private String url_produto= ""+Init.url+"buscar_produtos.php?opcao=nome";
    private boolean notifica;
    private Ringtone music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle("APP COMERCIAL");
        setSupportActionBar(toolbar);

        usuario = (TextView) findViewById(R.id.usuario);
        bt_menu_produtos = (Button) findViewById(R.id.bt_menu_produtos);
        bt_menu_vendas = (Button) findViewById(R.id.bt_menu_vendas);
        bt_menu_relatorios = (Button) findViewById(R.id.bt_menu_relatorio);
        bt_menu_estoque = (Button) findViewById(R.id.bt_menu_estoque);
        bt_menu_configuracoes = (Button) findViewById(R.id.bt_menu_configuracoes);
        bt_menu_sobre = (Button) findViewById(R.id.bt_menu_sobre);
        main = (LinearLayout) findViewById(R.id.main);
        notifica = false;
        shared = getSharedPreferences("configuracoes", 0);
        usuario.setText("Usuário: " + shared.getString("USER_LOGIN", "") + "\nLogado em " + shared.getString("data_login", ""));

        TranslateAnimation animacao = new TranslateAnimation(0,0,0,3000);
        animacao.setDuration(100);
        main.startAnimation(animacao);

        animacao = new TranslateAnimation(0,0,3000,0);
        animacao.setDuration(1500);
        main.startAnimation(animacao);

    }


    @Override
    public void onResume() {
        super.onResume();
        init_configuracoes();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void produtos(View v) {
        startActivity(new Intent(this, Produtos.class));
    }

    public void sobre(View v) {
        Snackbar.make(v, "Wilson Junior - Engenheiro de Computação - Ibiapina Ceará 2017", Snackbar.LENGTH_SHORT).show();
    }

    public void estoque(View v){
        startActivity(new Intent(this,Estoque.class));
    }

    public void vendas(View v){
        startActivity(new Intent(this,Vendas.class));
    }

    public void relatorios(View v){
        startActivity(new Intent(this,Relatorios.class));
    }

    public void sair(View v) {
        shared.edit().putString("USER_LOGIN", "").putString("PASS_LOGIN", "").putBoolean("LOGADO", false).commit();
        Menu.this.finish();
        startActivity(new Intent(this, Login.class));
    }

    public void configuracoes(View v) {
        startActivity(new Intent(this, Configuracao.class));
    }

    public void init_configuracoes() {
        bt_menu_produtos.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_produtos.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_produtos.setElevation(shared.getFloat("ELEVACAO", 0));

        bt_menu_vendas.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_vendas.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_vendas.setElevation(shared.getFloat("ELEVACAO", 0));

        bt_menu_relatorios.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_relatorios.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_relatorios.setElevation(shared.getFloat("ELEVACAO", 0));

        bt_menu_estoque.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_estoque.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_estoque.setElevation(shared.getFloat("ELEVACAO", 0));

        bt_menu_configuracoes.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_configuracoes.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_configuracoes.setElevation(shared.getFloat("ELEVACAO", 0));

        bt_menu_sobre.setTextSize(shared.getFloat("FONT_TAMANHO", 12));
        bt_menu_sobre.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        bt_menu_sobre.setElevation(shared.getFloat("ELEVACAO", 0));
    }


}
