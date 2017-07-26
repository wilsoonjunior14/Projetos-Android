package com.example.wilson.wcomercial;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Configuracao extends AppCompatActivity {

    private SharedPreferences shared;
    private Button btelevacao,bttamanhofonte,btcorfonte;
    private ToggleButton prodmais,prodmenos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btelevacao = (Button) findViewById(R.id.bt_elevacao);
        bttamanhofonte = (Button) findViewById(R.id.bt_tamanhofonte);
        btcorfonte = (Button) findViewById(R.id.bt_corfonte);
        prodmais = (ToggleButton) findViewById(R.id.bt_produtos_mais_vendidos);
        prodmenos = (ToggleButton) findViewById(R.id.bt_produtos_menos_vendidos);
        shared = getSharedPreferences("configuracoes",0);
            init_configuracoes();
    }

    public void tamanhofonte(View v){
        final String tamanhos[] = {"12pt","14pt","16pt","18pt"};
        AlertDialog.Builder ad = new AlertDialog.Builder(Configuracao.this);
        ad.setItems(tamanhos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shared.edit().putFloat("FONT_TAMANHO",Float.parseFloat(tamanhos[i].substring(0,2))).commit();
                init_configuracoes();
            }
        });
        ad.setTitle("Escolha o tamanho da fonte ...");
        ad.show();
    }

    public void elevacao(View v){
        final String tamanhos[] = {"2dp","3dp","4dp","5dp"};
        AlertDialog.Builder ad = new AlertDialog.Builder(Configuracao.this);
        ad.setItems(tamanhos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shared.edit().putFloat("BT_ELEVACAO",Float.parseFloat(tamanhos[i].substring(0,1))).commit();
                init_configuracoes();
            }
        });
        ad.setTitle("Selecione a Cor ...");
        ad.show();
    }

    public void corfonte(View v){
        final String cores[] = {"VERMELHO","AZUL","VERDE","PRETO"};
        final int cor[] = {Color.RED,Color.BLUE,Color.GREEN,Color.BLACK};
        AlertDialog.Builder ad = new AlertDialog.Builder(Configuracao.this);
        ad.setItems(cores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shared.edit().putInt("FONT_COR",cor[i]).commit();
                init_configuracoes();
            }
        });
        ad.setTitle("Selecione a Cor ...");
        ad.show();
    }

    public void mais_vendido(View v){
        if(prodmais.isChecked()){
            prodmais.setBackgroundColor(Color.GREEN);
            prodmenos.setChecked(false);
            prodmenos.setBackgroundColor(Color.RED);
            shared.edit().putBoolean("PRODUTOS_MAIS_VENDIDOS",true).commit();
            shared.edit().putBoolean("PRODUTOS_MENOS_VENDIDOS",false).commit();
        }else{
            prodmais.setBackgroundColor(Color.RED);
            prodmenos.setChecked(true);
            prodmenos.setBackgroundColor(Color.GREEN);
            shared.edit().putBoolean("PRODUTOS_MAIS_VENDIDOS",false).commit();
            shared.edit().putBoolean("PRODUTOS_MENOS_VENDIDOS",true).commit();
        }
        init_configuracoes();
    }

    public void menos_vendido(View v){
        if(prodmenos.isChecked()){
            prodmais.setBackgroundColor(Color.RED);
            prodmais.setChecked(false);
            prodmenos.setBackgroundColor(Color.GREEN);
            shared.edit().putBoolean("PRODUTOS_MAIS_VENDIDOS",false).commit();
            shared.edit().putBoolean("PRODUTOS_MENOS_VENDIDOS",true).commit();
        }else{
            prodmais.setBackgroundColor(Color.GREEN);
            prodmais.setChecked(true);
            prodmenos.setBackgroundColor(Color.RED);
            shared.edit().putBoolean("PRODUTOS_MAIS_VENDIDOS",true).commit();
            shared.edit().putBoolean("PRODUTOS_MENOS_VENDIDOS",false).commit();
        }
        init_configuracoes();
    }

    public void init_configuracoes(){
        btcorfonte.setTextColor(shared.getInt("FONT_COR",Color.BLACK));
        btcorfonte.setElevation(shared.getFloat("BT_ELEVACAO",0));
        btcorfonte.setTextSize(shared.getFloat("FONT_TAMANHO",12));

        bttamanhofonte.setTextColor(shared.getInt("FONT_COR",Color.BLACK));
        bttamanhofonte.setElevation(shared.getFloat("BT_ELEVACAO",0));
        bttamanhofonte.setTextSize(shared.getFloat("FONT_TAMANHO",12));

        btelevacao.setTextColor(shared.getInt("FONT_COR",Color.BLACK));
        btelevacao.setElevation(shared.getFloat("BT_ELEVACAO",0));
        btelevacao.setTextSize(shared.getFloat("FONT_TAMANHO",12));

        prodmais.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        prodmais.setElevation(shared.getFloat("BT_ELEVACAO",0));
        prodmais.setTextSize(shared.getFloat("FONT_TAMANHO",12));

        prodmenos.setTextColor(shared.getInt("FONT_COR", Color.BLACK));
        prodmenos.setElevation(shared.getFloat("BT_ELEVACAO",0));
        prodmenos.setTextSize(shared.getFloat("FONT_TAMANHO",12));

        if(shared.getBoolean("PRODUTOS_MAIS_VENDIDOS",false)){
            prodmais.setBackgroundColor(Color.GREEN);
            prodmais.setChecked(true);
            prodmenos.setChecked(false);
            prodmenos.setBackgroundColor(Color.RED);
        }else{
            prodmais.setBackgroundColor(Color.RED);
            prodmais.setChecked(false);
            prodmenos.setChecked(true);
            prodmenos.setBackgroundColor(Color.GREEN);
        }

        if(shared.getBoolean("PRODUTOS_MENOS_VENDIDOS",false)){
            prodmais.setBackgroundColor(Color.RED);
            prodmais.setChecked(false);
            prodmenos.setChecked(true);
            prodmenos.setBackgroundColor(Color.GREEN);
        }else{
            prodmais.setBackgroundColor(Color.GREEN);
            prodmais.setChecked(true);
            prodmenos.setChecked(false);
            prodmenos.setBackgroundColor(Color.RED);
        }

    }

}
