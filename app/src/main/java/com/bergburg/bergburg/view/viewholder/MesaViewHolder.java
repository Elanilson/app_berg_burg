package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Produto;

public class MesaViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNomeItem,textViewValor, textViewQuantidade, textViewTotal,textViewInfoTotal,textViewDescricao;
    CardView layout;


    public MesaViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNomeItem = itemView.findViewById(R.id.textViewNomeItem);
        textViewValor = itemView.findViewById(R.id.textViewValorItem);
        textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
        textViewTotal = itemView.findViewById(R.id.textViewValorItemTotal);
        textViewInfoTotal = itemView.findViewById(R.id.textViewinfoTotal);
        textViewQuantidade = itemView.findViewById(R.id.textViewQuantidadeItem);
        layout = itemView.findViewById(R.id.layoutITem);
    }
    public void bind(ItemDePedido itensDoPedido, OnListenerAcao<ItemDePedido> onListenerAcao){
        textViewNomeItem.setText(itensDoPedido.getTitulo());
        textViewValor.setText("R$ "+String.format("%.2f", itensDoPedido.getPreco()));
        textViewTotal.setText(calcular(itensDoPedido));
        textViewQuantidade.setText(itensDoPedido.getQuantidade()+"x");
        textViewDescricao.setText(itensDoPedido.getDescricao());

        textViewTotal.setVisibility(View.VISIBLE);
        textViewQuantidade.setVisibility(View.VISIBLE);
        textViewInfoTotal.setVisibility(View.VISIBLE);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onListenerAcao.onLongClick(itensDoPedido);
                return false;
            }
        });
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(itensDoPedido);
        });

    }

    private String calcular(ItemDePedido itensDoPedido){
        Float total = 0f;
        total = (itensDoPedido.getQuantidade() * itensDoPedido.getPreco());
        return  "R$ "+String.format("%.2f", total);
    }
}
