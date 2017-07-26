package com.example.wilson.recognitionvoice;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private FrameLayout frame_um,frame_dois,frame_tres;
    private TextToSpeech tts;
    private com.melnykov.fab.FloatingActionButton main_button;
    private String voz_reproduzida="";
    private SQLiteDatabase sqlite;
    private EditText nome,data_started,data_finished;
    public static RecyclerView recycler_notes;
    public static ArrayList<Nota> lista_notas = new ArrayList<Nota>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WNotes");
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        main_button = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.main_button);
        nome = (EditText) findViewById(R.id.nome_nota);
        data_finished = (EditText) findViewById(R.id.data_lembrete);
        data_started = (EditText) findViewById(R.id.data_criacao);

        tts = new TextToSpeech(this,this);

        recycler_notes = (RecyclerView) findViewById(R.id.recycler_notes);
        recycler_notes.setHasFixedSize(true);
        init_notas(this);
        MainActivity.recycler_notes.setAdapter(new RecyclerAdapter(this,MainActivity.lista_notas));
        recycler_notes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Date data = new Date();
        data.setDate(data.getDate()+1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        data_started.setText(sdf.format(data));


        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Wave)
                        .duration(1000)
                        .repeat(1)
                        .playOn(view);
                frame_dois.setVisibility(View.VISIBLE);
                frame_um.setVisibility(View.GONE);
                frame_tres.setVisibility(View.GONE);
            }
        });

    }

    public static void init_notas(Context context){
        SQLiteDatabase sqlite = new DataBase(context).getReadableDatabase();
        MainActivity.lista_notas = new ArrayList<Nota>();
        Cursor cursor = sqlite.rawQuery("SELECT * FROM notas", null);
        cursor.moveToFirst();
        while(!cursor.isLast()){
            MainActivity.lista_notas.add(new Nota(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(0))));
            cursor.moveToNext();
        }
    }

    public static void excluir_nota(Context context,int id,int position){
        SQLiteDatabase sqlite = new DataBase(context).getReadableDatabase();
        String sql = "DELETE FROM notas WHERE notas.id = '"+id+"'";
        sqlite.execSQL(sql);
        Toast.makeText(context,"FOI REMOVIDO",Toast.LENGTH_SHORT).show();
    }

    public void gravar_voz(View v){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"FALE E ESPERE UM POUCO");
        startActivityForResult(intent,0);
    }

    public void reproduzir_voz(View v){
        AudioManager audiomanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int max = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC,max,0);
        tts.speak(voz_reproduzida,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void salvar_nota(View v){
        try {
            sqlite = new DataBase(this).getWritableDatabase();
            String nome_nota = nome.getText().toString();
            String data_start = data_started.getText().toString();
            String data_finish = data_finished.getText().toString();
            if(!nome_nota.equals("") && data_start.length()==10 && data_finish.length()==10) {
                String sql = "INSERT INTO notas(nome,data_criacao,data_lembrete,texto) VALUES ('" + nome_nota + "','" + data_start + "','" + data_finish + "','" + voz_reproduzida + "')";
                sqlite.execSQL(sql);
                Toast.makeText(this, "NOTA SALVO COM SUCESSO!", Toast.LENGTH_LONG).show();
                nome.setText("");
                data_started.setText("");
                data_finished.setText("");
                voz_reproduzida = "";
                init_notas(this);
            }else{
                Toast.makeText(this,"INFORMAÇÕES INVÁLIDAS",Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Toast.makeText(this,"INFORMAÇÕES INVÁLIDAS",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestcode,int resultcode,Intent intent){
        if(requestcode == 0 && resultcode == RESULT_OK){
            voz_reproduzida = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Toast.makeText(MainActivity.this,"ÁUDIO OBTIDO COM SUCESSO!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_visualizar){
            frame_um.setVisibility(View.GONE);
            frame_dois.setVisibility(View.GONE);
            frame_tres.setVisibility(View.VISIBLE);
        }else if(id == R.id.menu_home){
            frame_um.setVisibility(View.VISIBLE);
            frame_dois.setVisibility(View.GONE);
            frame_tres.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int i) {

    }
}
