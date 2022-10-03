package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;

public class CardapioViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNomeItem;
    LinearLayout layout;

    public CardapioViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNomeItem = itemView.findViewById(R.id.textViewNomeItem);
        layout = itemView.findViewById(R.id.layoutITem);
    }
    public void bind(Categoria categoria, OnListenerAcao<Categoria> onListenerAcao){
        textViewNomeItem.setText(categoria.getTitulo());
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(categoria);
        });

    }
}
