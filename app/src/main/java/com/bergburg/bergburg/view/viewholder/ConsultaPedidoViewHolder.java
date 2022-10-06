package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;

public class PedidoViewHolder extends RecyclerView.ViewHolder {
    TextView textNumeroMesa,textStatus;
    CardView layout;


    public PedidoViewHolder(@NonNull View itemView) {
        super(itemView);
        textNumeroMesa = itemView.findViewById(R.id.textViewNumeroDaMesa);
        textStatus = itemView.findViewById(R.id.textViewStatusDoPedido);
        layout = itemView.findViewById(R.id.carViewPedido);
    }
    public void bind(Pedido pedido, OnListenerAcao<Pedido> onListenerAcao){

        textNumeroMesa.setText(""+pedido.getNumeroMesa());
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(pedido);
        });

    }


}
