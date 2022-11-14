package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;

public class ItemPedidoInfoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNomeItem,textViewValor,textViewDescricao,textViewQuantidade,textViewPosicao;
    CardView layout;


    public ItemPedidoInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewQuantidade = itemView.findViewById(R.id.textViewQuantidade);
        textViewNomeItem = itemView.findViewById(R.id.textViewItem);
        textViewValor = itemView.findViewById(R.id.textViewValor);
        textViewPosicao = itemView.findViewById(R.id.textVieworderm);

    }
    public void bind(ItemDePedido item, OnListenerAcao<ItemDePedido> onListenerAcao, int posicao){
        textViewPosicao.setText(""+(posicao+1));
        textViewQuantidade.setText(item.getQuantidade()+" x "+String.format("%.2f", item.getPreco()));
        textViewNomeItem.setText(item.getTitulo());
        textViewValor.setText("R$ "+String.format("%.2f",(item.getQuantidade() *  item.getPreco())));



    }
}
