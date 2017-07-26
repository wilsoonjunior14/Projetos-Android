package com.example.wilson.pedidoson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson on 14/02/2017.
 */

public class Dados {

    public static List<Produto> produtos = new ArrayList<Produto>();

    public static void load_data(){
        produtos.add(new Produto(1,"Calzone de Frango","Frango Catupiry, Mussarela, Presunto, Temperos",12.00,"massa",R.drawable.calzone_frango,1));
        produtos.add(new Produto(2,"Escondidinho de Frango","Frango, Mussarela, Presunto, Batata, Temperos",15.00,"massa",R.drawable.escondidinho_frango,1));
        produtos.add(new Produto(3,"Lasanha de Carne","Carne Moída, Mussarela, Presunto, Temperos",5.50,"massa",R.drawable.lasanha_carne,1));
        produtos.add(new Produto(4,"Pizza Calabresa Tam G","Calabresa, Presunto, Mussarela, Azeitonas, Temperos",23.00,"massa",R.drawable.pizza_calabresa,1));
    }

    public static List<Produto> getProdutos(){
        return produtos;
    }

    public static int getImagemProduto(int id){
        for (int i=0;i<produtos.size();i++){
            if (produtos.get(i).getId() == id){
                return produtos.get(i).getImagem();
            }
        }
        return 0;
    }

    public static String getCategoriaProduto(int id){
        for (int i=0;i<produtos.size();i++){
            if (produtos.get(i).getId() == id){
                return produtos.get(i).getCategoria();
            }
        }
        return "";
    }

    public static double getPrecoProduto(int id){
        for (int i=0;i<produtos.size();i++){
            if (produtos.get(i).getId() == id){
                return produtos.get(i).getPreco();
            }
        }
        return 0;
    }
}
