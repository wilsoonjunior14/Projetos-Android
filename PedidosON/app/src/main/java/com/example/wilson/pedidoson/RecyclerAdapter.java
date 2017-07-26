package com.example.wilson.pedidoson;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.format;
import static android.R.attr.itemBackground;

/**
 * Created by Wilson on 13/02/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter{
    Context context;
    List<Produto> lista;

    public RecyclerAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.lista = produtos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list,parent,false);
        RecycleHolder r = new RecycleHolder(v);
        return  r;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecycleHolder my = (RecycleHolder) holder;
        my.id.setText(""+lista.get(position).getId()+"");
        my.imagem.setImageResource(lista.get(position).getImagem());
        my.nome.setText(lista.get(position).getNome());
        my.descricao.setText(lista.get(position).getDescricao());
        double preco = lista.get(position).getPreco();
        DecimalFormat df = new DecimalFormat("0.00");
        my.preco.setText("R$"+df.format(preco));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder{

        final CardView card;
        final TextView id;
        final CircleImageView imagem;
        final TextView nome;
        final TextView descricao;
        final TextView preco;
        final CircleImageView button;
        boolean status_produto = true;

        public RecycleHolder(final View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            nome = (TextView) itemView.findViewById(R.id.nome);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            preco = (TextView) itemView.findViewById(R.id.preco);
            imagem = (CircleImageView) itemView.findViewById(R.id.imagem);
            button = (CircleImageView) itemView.findViewById(R.id.button);
            card = (CardView) itemView.findViewById(R.id.cardview);
            setButton();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(itemView.getContext());
                    dialogo.setIcon(R.mipmap.ic_launcher);
                    if(status_produto){
                        dialogo.setMessage("Remover do Pedido?");
                        dialogo.setTitle("CONFIRMAÇÃO");
                        dialogo.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int identificador = Integer.parseInt(id.getText().toString());
                                preco.setText("R$"+new DecimalFormat("0.00").format(Dados.getPrecoProduto(identificador)));
                                boolean b = Pedido.setPedido(identificador);
                                MainActivity.toolbar.setSubtitle("Total do Pedido: R$"+new DecimalFormat("0.00").format(Pedido.getValorPedido()));
                                Toast.makeText(itemView.getContext(),"Produto Removido do Pedido",Toast.LENGTH_LONG).show();
                                setButton();
                            }
                        });
                        dialogo.setNegativeButton("NÃO",null);
                        dialogo.show();
                    }else{
                        dialogo.setMessage("Adicionar ao Pedido?\n");
                        dialogo.setTitle("CONFIRMAÇÃO");
                        final EditText edit = new EditText(itemView.getContext());
                        edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit.setHint("*****QUANTIDADE*****");
                        dialogo.setView(edit);
                        dialogo.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String text = edit.getText().toString();
                                if(text.equals("") || Integer.parseInt(text) <= 0){
                                    Toast.makeText(itemView.getContext(),"QUANTIDADE INVÁLIDA",Toast.LENGTH_LONG).show();
                                }else{
                                    int identificador = Integer.parseInt(id.getText().toString());
                                    String nome_produto = nome.getText().toString();
                                    String descricao_produto = descricao.getText().toString();
                                    int imagem = Dados.getImagemProduto(identificador);
                                    int quantidade = Integer.parseInt(text);
                                    String categoria_produto = Dados.getCategoriaProduto(identificador);
                                    double new_preco = Dados.getPrecoProduto(identificador) * quantidade;

                                    Produto p = new Produto(identificador,nome_produto,descricao_produto,new_preco,categoria_produto,imagem,quantidade);
                                    Pedido.addPedido(p);

                                    preco.setText(""+quantidade+" Unidade(s) - R$"+new DecimalFormat("0.00").format(new_preco));
                                    setButton();
                                    MainActivity.toolbar.setSubtitle("Total do Pedido: R$"+new DecimalFormat("0.00").format(Pedido.getValorPedido()));
                                    Toast.makeText(itemView.getContext(),"PRODUTO ADICIONADO AO PEDIDO !!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        dialogo.setNegativeButton("NÃO",null);
                        dialogo.show();
                        Toast.makeText(itemView.getContext(),""+Pedido.getCountPedido()+"",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        public void setButton(){
            status_produto = !status_produto;
            if(status_produto){
                button.setImageResource(R.drawable.not);
                id.setBackground(itemView.getResources().getDrawable(R.drawable.estilos3));
                card.setCardBackgroundColor(itemView.getResources().getColor(R.color.cinza));
            }else{
                button.setImageResource(R.drawable.ok);
                id.setBackground(itemView.getResources().getDrawable(R.drawable.estilos));
                card.setCardBackgroundColor(itemView.getResources().getColor(R.color.card_back));
            }
        }
    }
}
