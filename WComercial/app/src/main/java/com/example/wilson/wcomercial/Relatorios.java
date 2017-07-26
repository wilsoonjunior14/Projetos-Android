package com.example.wilson.wcomercial;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wilson.wcomercial.Model.Produto;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Relatorios extends AppCompatActivity {

    private GraphView graphView;
    private String url = ""+Init.url+"buscar_produtos_vendidos.php?";
    private String url_vendas = ""+Init.url+"vendas.php?";
    private String url_produtos = ""+Init.url+"buscar_produtos.php?opcao=quantidade";
    private List<Produto> lista_produtos = new ArrayList<Produto>();
    private JSONArray array;
    private int contador_grafico = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_relatorios);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        graphView = (GraphView) findViewById(R.id.grafico);
        graphView.removeAllSeries();
        geragraficoCentro();

    }

    public void graficoesquerda(View v){
        graphView.removeAllSeries();
        contador_grafico--;
        geraanimacao();
        if(contador_grafico<1){
            geragraficoEsquerda();
            contador_grafico = 0;
        }else if(contador_grafico == 1){
            geragraficoCentro();
        }else if(contador_grafico > 1){
            geragraficoDireita();
            contador_grafico = 2;
        }
    }

    public void graficodireita(View v){
        graphView.removeAllSeries();
        contador_grafico++;
        geraanimacao();
        if(contador_grafico<1){
            geragraficoEsquerda();
            contador_grafico = 0;
        }else if(contador_grafico == 1){
            geragraficoCentro();
        }else if(contador_grafico > 1){
            geragraficoDireita();
            contador_grafico = 2;
        }
    }

    public void geraanimacao(){
        TranslateAnimation animation = new TranslateAnimation(0,-1000,0,0);
        animation.setDuration(500);
        graphView.startAnimation(animation);
        animation = new TranslateAnimation(-1000,0,0,0);
        animation.setDuration(1000);
        graphView.startAnimation(animation);

    }

    public void addLista(JSONObject objeto){
        Produto p = new Produto();
        try {
            p.setNome(objeto.getString("nome").toString().toUpperCase());
            p.setCodigo(objeto.getString("codigo").toString().toUpperCase());
            p.setQuantidade(Integer.parseInt(objeto.getString("quantidade")));
            lista_produtos.add(p);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void geragraficoDireita(){
        graphView.removeAllSeries();
        graphView.getGridLabelRenderer().resetStyles();
        final RequestQueue request = Volley.newRequestQueue(Relatorios.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_produtos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    if(array.length()<=5){
                        String nomes[] = new String[array.length()];
                        DataPoint pontos[] = new DataPoint[array.length()];
                        for(int i=0;i<array.length();i++){
                            JSONObject objeto = new JSONObject(array.getString(i));
                            nomes[i] = objeto.getString("nome");
                            pontos[i] = new DataPoint(i,Integer.parseInt(objeto.getString("quantidade")));
                        }
                        BarGraphSeries<DataPoint> graph = new BarGraphSeries<DataPoint>(pontos);
                        graph.setColor(Color.rgb(0,100,100));
                        graph.setDrawValuesOnTop(true);
                        graph.setValuesOnTopColor(Color.RED);
                        graph.setSpacing(20);
                        graphView.addSeries(graph);

                        graphView.setTitle("PRODUTOS COM MENOR QUANTIDADE EM ESTOQUE");

                        StaticLabelsFormatter labels = new StaticLabelsFormatter(graphView);
                        labels.setHorizontalLabels(nomes);
                        graphView.getGridLabelRenderer().setLabelFormatter(labels);
                        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
                        graphView.getLegendRenderer().setVisible(false);

                    }else{
                        String nomes[] = new String[5];
                        DataPoint pontos[] = new DataPoint[5];
                        for(int i=0;i<5;i++){
                            JSONObject objeto = new JSONObject(array.getString(i));
                            nomes[i] = objeto.getString("nome");
                            pontos[i] = new DataPoint(i,Integer.parseInt(objeto.getString("quantidade")));
                        }
                        BarGraphSeries<DataPoint> graph = new BarGraphSeries<DataPoint>(pontos);
                        graph.setColor(Color.rgb(0,100,100));
                        graph.setDrawValuesOnTop(true);
                        graph.setValuesOnTopColor(Color.RED);
                        graph.setSpacing(80);
                        graph.setTitle("Quantidades Restantes");
                        graphView.addSeries(graph);

                        graphView.setTitle("PRODUTOS COM MENOR QUANTIDADE EM ESTOQUE");

                        StaticLabelsFormatter labels = new StaticLabelsFormatter(graphView);
                        labels.setHorizontalLabels(nomes);

                        graphView.getGridLabelRenderer().setLabelFormatter(labels);
                        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
                        graphView.getLegendRenderer().setVisible(false);
                    }

                }catch(Exception e){
                    Toast.makeText(Relatorios.this,"DADOS INVÁLIDOS",Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Relatorios.this,"ERRO NA CONEXÃO COM O SERVIDOR",Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

    public void geragraficoEsquerda(){
        graphView.getGridLabelRenderer().resetStyles();
        graphView.removeAllSeries();
        RequestQueue request = Volley.newRequestQueue(Relatorios.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url_vendas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if(array.length()>0){
                        ArrayList<String> nomes = new ArrayList<String>();
                        ArrayList<DataPoint> pontos = new ArrayList<DataPoint>();
                        for(int i=0;i<array.length();i++) {
                            JSONObject objeto = new JSONObject(array.getString(i));
                                nomes.add(objeto.getString("data"));
                                pontos.add(new DataPoint(i, Integer.parseInt(objeto.getString("quantidade"))));
                        }

                        DataPoint points[] = new DataPoint[pontos.size()];
                        String names[] = new String[pontos.size()];
                        for(int i=0;i<nomes.size();i++){
                            if(i == 1 || i+1 == nomes.size() || i == (int)nomes.size()/2) {
                                points[i] = pontos.get(i);
                                names[i] = nomes.get(i);
                            }else{
                                points[i] = pontos.get(i);
                                names[i] = "";
                            }
                        }

                        LineGraphSeries<DataPoint> graph = new LineGraphSeries<DataPoint>(points);
                        graph.setThickness(2);
                        graph.setTitle("Taxa de Crescimento");
                        graph.setColor(Color.RED);
                        graphView.addSeries(graph);
                        graphView.setTitle("Estatísticas das Vendas");
                        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        graphView.getLegendRenderer().setTextColor(Color.WHITE);
                        graphView.getLegendRenderer().setVisible(true);
                        graphView.getGridLabelRenderer().setNumHorizontalLabels(pontos.size());

                        PointsGraphSeries<DataPoint> point = new PointsGraphSeries<DataPoint>(points);
                        point.setShape(PointsGraphSeries.Shape.POINT);
                        point.setColor(Color.BLUE);
                        point.setTitle("Quantidades");
                        point.setSize(5);
                        graphView.addSeries(point);

                        StaticLabelsFormatter labels = new StaticLabelsFormatter(graphView);
                        labels.setHorizontalLabels(names);
                        graphView.getGridLabelRenderer().setLabelFormatter(labels);
                    }else{
                        Toast.makeText(Relatorios.this,"RELATÓRIOS AINDA NÃO ESTÃO PRONTOS",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(Relatorios.this,"DADOS INVÁLIDOS", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Relatorios.this,"SEM CONEXÃO COM O SERVIDOR", Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

    public void geragraficoCentro(){
        graphView.getGridLabelRenderer().resetStyles();
        graphView.removeAllSeries();
        RequestQueue request = Volley.newRequestQueue(Relatorios.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    array = new JSONArray(response);
                    String nomes[];
                    DataPoint pontos[];

                    if(array.length()<=5) {
                        nomes = new String[array.length()];
                        pontos = new DataPoint[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            addLista(new JSONObject(array.getString(i)));
                            nomes[i] = lista_produtos.get(i).getNome().toLowerCase();
                            pontos[i] = new DataPoint(i + 2, lista_produtos.get(i).getQuantidade());
                        }
                    }else{
                        nomes = new String[5];
                        pontos = new DataPoint[5];
                        for (int i = 0; i < 5; i++) {
                            addLista(new JSONObject(array.getString(i)));
                            nomes[i] = lista_produtos.get(i).getNome().toLowerCase();
                            pontos[i] = new DataPoint(i, lista_produtos.get(i).getQuantidade());
                        }
                    }

                    BarGraphSeries<DataPoint> graph = new BarGraphSeries<DataPoint>(pontos);
                    graph.setSpacing(50);
                    graph.setDrawValuesOnTop(true);
                    graph.setValuesOnTopColor(Color.BLUE);
                    graph.setTitle("QUANTIDADE");
                    graph.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb(0,100,100);
                        }
                    });
                    graphView.addSeries(graph);

                    graphView.setTitle("PRODUTOS MAIS VENDIDOS");
                    StaticLabelsFormatter labels = new StaticLabelsFormatter(graphView);
                    labels.setHorizontalLabels(nomes);
                    graphView.getGridLabelRenderer().setLabelFormatter(labels);
                    graphView.setTitleTextSize(20);
                    graphView.setTitleColor(Color.BLACK);

                } catch (JSONException e) {
                    Toast.makeText(Relatorios.this,"ERRO", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Relatorios.this,"DADOS INVÁLIDOS", Toast.LENGTH_LONG).show();
            }
        });
        request.add(stringrequest);
    }

}
