package com.example.wilson.wcomercial;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Init extends Activity {

    public static String url = "http://192.168.10.111:80/AppComercial/";
    ImageView imagem,quemsomos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imagem = (ImageView) findViewById(R.id.imagem_entrada);
        quemsomos = (ImageView) findViewById(R.id.imagem_quemsomos);

        Animation animation = new TranslateAnimation(0,-1000,0,-1000);
        animation.setDuration(500);
        imagem.startAnimation(animation);

        animation = new TranslateAnimation(-1000,0,-1000,0);
        animation.setDuration(2000);
        imagem.startAnimation(animation);

        animation = new TranslateAnimation(0,0,0,1000);
        animation.setDuration(500);
        quemsomos.startAnimation(animation);

        animation = new TranslateAnimation(0,0,1000,0);
        animation.setDuration(3000);
        quemsomos.startAnimation(animation);



        Runnable run = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Init.this,Login.class));
                Init.this.finish();
            }
        };

        new Handler().postDelayed(run,4000);

    }

}
