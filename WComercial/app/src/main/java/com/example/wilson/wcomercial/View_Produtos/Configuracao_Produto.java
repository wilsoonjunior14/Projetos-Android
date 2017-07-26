package com.example.wilson.wcomercial.View_Produtos;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.wilson.wcomercial.R;

public class Configuracao_Produto extends AppCompatActivity {

    private ToggleButton bt_proddisp,bt_ordemalfa,bt_todos;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao__produto);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bt_proddisp = (ToggleButton) findViewById(R.id.bt_config_proddisp);
        bt_ordemalfa = (ToggleButton) findViewById(R.id.bt_config_ordemalfa);
        bt_todos = (ToggleButton) findViewById(R.id.bt_config_todos);
        shared = getSharedPreferences("configuracoes",0);

        bt_proddisp.setChecked(shared.getBoolean("produtos_disponiveis",false));
        bt_todos.setChecked(shared.getBoolean("todos_produtos",false));
        bt_ordemalfa.setChecked(shared.getBoolean("ordem_alfabetica",false));

        init_botoes();
    }

    public void config_produtos_disponiveis(View v){
        if(bt_proddisp.isChecked()){
            shared.edit().putBoolean("produtos_disponiveis",true).commit();
        }else{
            shared.edit().putBoolean("produtos_disponiveis",false).commit();
        }
        init_botoes();
    }

    public void config_ordem_alfa(View v){
        if(bt_ordemalfa.isChecked()){
            shared.edit().putBoolean("ordem_alfabetica",true).commit();
        }else{
            shared.edit().putBoolean("ordem_alfabetica",false).commit();
        }
        init_botoes();
    }

    public void config_todos_produtos(View v){
        if(bt_todos.isChecked()){
            shared.edit().putBoolean("todos_produtos",true).commit();
        }else{
            shared.edit().putBoolean("todos_produtos",false).commit();
        }
        init_botoes();
    }

    public void init_botoes(){
        if(bt_todos.isChecked()){
            bt_todos.setBackgroundColor(Color.GREEN);
        }else{
            bt_todos.setBackgroundColor(Color.RED);
        }
        if(bt_ordemalfa.isChecked()){
            bt_ordemalfa.setBackgroundColor(Color.GREEN);
        }else{
            bt_ordemalfa.setBackgroundColor(Color.RED);
        }
        if(bt_proddisp.isChecked()){
            bt_proddisp.setBackgroundColor(Color.GREEN);
        }else{
            bt_proddisp.setBackgroundColor(Color.RED);
        }
    }

}
