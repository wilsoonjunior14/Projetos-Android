package com.example.wilson.pedidoson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 14/02/2017.
 */

public class Pedido {

    public static List<Produto> produtos_adicionados = new ArrayList<Produto>();

    public static List<Produto> getPedido(){
        return produtos_adicionados;
    }

    public static void addPedido(Produto p){
        produtos_adicionados.add(p);
    }

    public static boolean setPedido(int id){
        boolean remover = false;
        if(produtos_adicionados != null) {
            for (int i = 0; i < produtos_adicionados.size(); i++) {
                if (produtos_adicionados.get(i).getId() == id) {
                    produtos_adicionados.remove(i);
                    remover = true;
                }
            }
        }
        return remover;
    }

    public static int getCountPedido(){
        return produtos_adicionados.size();
    }

    public static double getValorPedido(){
        double total = 0;
        for (int i=0;i<getCountPedido();i++){
            total += produtos_adicionados.get(i).getPreco();
        }
        return total;
    }

}
