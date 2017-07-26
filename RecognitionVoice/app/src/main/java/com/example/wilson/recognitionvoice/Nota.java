package com.example.wilson.recognitionvoice;

/**
 * Created by Wilson on 15/03/2017.
 */

public class Nota {

    private String nome_nota;
    private String data_inicio;
    private String data_lembrete;
    private String texto;
    private int id;


    public Nota(String nome_nota, String data_inicio, String data_lembrete, String texto, int id) {
        this.nome_nota = nome_nota;
        this.data_inicio = data_inicio;
        this.data_lembrete = data_lembrete;
        this.texto = texto;
        this.id = id;
    }

    public String getNome_nota() {
        return nome_nota;
    }

    public void setNome_nota(String nome_nota) {
        this.nome_nota = nome_nota;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_lembrete() {
        return data_lembrete;
    }

    public void setData_lembrete(String data_lembrete) {
        this.data_lembrete = data_lembrete;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
