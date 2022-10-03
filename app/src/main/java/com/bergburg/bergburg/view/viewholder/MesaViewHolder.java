package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Produto;

public class ItemCardapioViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNomeItem,textViewValor;
    LinearLayout layout;

    public ItemCardapioViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNomeItem = itemView.findViewById(R.id.textViewNomeItem);
        textViewValor = itemView.findViewById(R.id.textViewValorItem);
        layout = itemView.findViewById(R.id.layoutITem);
    }
    public void bind(Produto produto, OnListenerAcao<Produto> onListenerAcao){
        textViewNomeItem.setText(produto.getTitulo());
        textViewValor.setText("R$ "+produto.getPreco());
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(produto);
        });

    }
}
