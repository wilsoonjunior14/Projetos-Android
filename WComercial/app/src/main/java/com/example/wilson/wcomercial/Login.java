package com.example.wilson.wcomercial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity {

    private EditText user,pass;
    private CheckBox manter_conectado;
    private SharedPreferences shared;
    private String validar_login = ""+Init.url+"valida_login.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        user = (EditText) findViewById(R.id.etnome);
        pass = (EditText) findViewById(R.id.etsenha);
        manter_conectado = (CheckBox) findViewById(R.id.manterconectado);
        shared = getSharedPreferences("configuracoes",0);
        }

    @Override
    public void onResume(){
        super.onResume();
        try {
            String response = VolleyRequest.getResponse(Login.this, Request.Method.GET, validar_login);
            boolean logado = shared.getBoolean("LOGADO",true);
            if(logado){
                startActivity(new Intent(Login.this,Menu.class));
            }else {
                Toast.makeText(Login.this,"FAÇA LOGIN E TENHA ACESSO AOS SERVIÇOS DO APLICATIVO!",Toast.LENGTH_LONG).show();
                user.setText("");
                pass.setText("");
            }
        }catch(Exception e){
            Toast.makeText(Login.this,"ERRO NA CONEXÃO COM A INTERNET",Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View v){
        if(!user.getText().toString().equals("") && !pass.getText().toString().equals("")) {
            RequestQueue request = Volley.newRequestQueue(Login.this);
            validar_login = validar_login + "user=" + user.getText().toString() + "&pass=" + pass.getText().toString();
            StringRequest stringrequest = new StringRequest(StringRequest.Method.GET, validar_login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (isChecked()) {
                            shared.edit().putBoolean("LOGADO", true).commit();
                        } else {
                            shared.edit().putString("USER_LOGIN", user.getText().toString()).putString("PASS_LOGIN", pass.getText().toString()).commit();
                        }
                        int value_response = Integer.parseInt(response);
                        if (value_response == 1) {
                            Date data = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            shared.edit().putString("data_login",sdf.format(data)).commit();
                            startActivity(new Intent(Login.this, Menu.class));
                        } else {
                            Toast.makeText(Login.this, "LOGIN INVÁLIDO! VERIFIQUE OS DADOS E TENTE NOVAMENTE.", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(Login.this,"VERIFIQUE SEUS DADOS E CONEXÃO COM A INTERNET E TENTE NOVAMENTE!",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, "SEM CONEXÃO COM A INTERNET! VERIFIQUE SUA CONEXÃO E TENTE NOVAMENTE!", Toast.LENGTH_LONG).show();
                }
            });
            request.add(stringrequest);
        }else{
            Toast.makeText(Login.this,"VERIFIQUE SEUS DADOS E TENTE NOVAMENTE!",Toast.LENGTH_LONG).show();
        }
    }

    public boolean isChecked(){
        boolean retorno = false;
        if(manter_conectado.isChecked()){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("USER_LOGIN",user.getText().toString());
            editor.putString("PASS_LOGIN",pass.getText().toString());
            editor.commit();
            retorno = true;
        }
        return retorno;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Login.this.finish();
    }

}
