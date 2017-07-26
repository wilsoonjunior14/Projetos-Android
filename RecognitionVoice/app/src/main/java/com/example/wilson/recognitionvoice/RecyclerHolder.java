package com.example.wilson.recognitionvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Wilson on 15/03/2017.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder implements TextToSpeech.OnInitListener{

    final EditText update_nome_nota;
    final EditText update_data_inicio;
    final EditText update_data_lembrete;
    String texto;
    final CardView cardView;
    TextToSpeech tts;
    final com.melnykov.fab.FloatingActionButton reproduzir,excluir;
    final TextView id;
    int position;


    public RecyclerHolder(final View itemView) {
        super(itemView);
        update_nome_nota = (EditText) itemView.findViewById(R.id.update_nome_nota);
        update_data_inicio = (EditText) itemView.findViewById(R.id.update_data_criada);
        update_data_lembrete = (EditText) itemView.findViewById(R.id.update_data_lembrete);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
        reproduzir = (com.melnykov.fab.FloatingActionButton) itemView.findViewById(R.id.reproduzir_voz);
        excluir = (com.melnykov.fab.FloatingActionButton) itemView.findViewById(R.id.excluir_nota);
        id = (TextView) itemView.findViewById(R.id.id);
        tts = new TextToSpeech(itemView.getContext(),this);

        reproduzir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager audiomanager = (AudioManager) itemView.getContext().getSystemService(Context.AUDIO_SERVICE);
                int max = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC,max,0);
                tts.speak(texto,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int identificador = Integer.parseInt(id.getText().toString());
                SQLiteDatabase sqlite = new DataBase(itemView.getContext()).getReadableDatabase();
                sqlite.execSQL("DELETE FROM notas WHERE notas._id = '"+identificador+"'");
                MainActivity.lista_notas.remove(position);
                MainActivity.recycler_notes.setAdapter(new RecyclerAdapter(itemView.getContext(),MainActivity.lista_notas));
            }
        });



    }

    @Override
    public void onInit(int i) {

    }
}
