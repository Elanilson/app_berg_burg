package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Produto;

public class MesaViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNomeItem,textViewValor, textViewQuantidade;
    LinearLayout layout;


    public MesaViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNomeItem = itemView.findViewById(R.id.textViewNomeItem);
        textViewValor = itemView.findViewById(R.id.textViewValorItem);
        textViewQuantidade = itemView.findViewById(R.id.textViewQuantidadeItem);
        layout = itemView.findViewById(R.id.layoutITem);
    }
    public void bind(Produto produto, OnListenerAcao<Produto> onListenerAcao){
        textViewNomeItem.setText(produto.getTitulo());
        textViewValor.setText(calcular(produto));
        textViewQuantidade.setText(produto.getQuantidade()+"x");
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onListenerAcao.onLongClick(produto);
                return false;
            }
        });
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(produto);
        });

    }

    private String calcular(Produto produto){
        return  "R$ "+(produto.getQuantidade() * produto.getPreco());
    }
}
