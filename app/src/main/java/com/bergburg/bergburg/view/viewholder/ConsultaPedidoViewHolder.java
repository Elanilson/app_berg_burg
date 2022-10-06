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

public class ConsultaPedidoViewHolder extends RecyclerView.ViewHolder {
    TextView textNumeroMesa,textStatus,textViewTotal;
    CardView layout;
    private View view;


    public ConsultaPedidoViewHolder(@NonNull View itemView) {
        super(itemView);
        textNumeroMesa = itemView.findViewById(R.id.textViewNumeroDaMesa);
        textStatus = itemView.findViewById(R.id.textViewStatusDoPedido);
        textViewTotal = itemView.findViewById(R.id.textViewTotalPedido);
        layout = itemView.findViewById(R.id.carViewPedido);
        view = itemView;
    }
    public void bind(Pedido pedido, OnListenerAcao<Pedido> onListenerAcao){
        String status = "";
        if(pedido.getStatus() == 0){
            status = view.getContext().getString(R.string.pendente);
        }else if(pedido.getStatus() == 1){
            status = view.getContext().getString(R.string.cancelado);
        }else if(pedido.getStatus() == 2){
            status = view.getContext().getString(R.string.preparando);
        }else if(pedido.getStatus() == 3){
            status = view.getContext().getString(R.string.pronto);
        }else if(pedido.getStatus() == 4){
            status = view.getContext().getString(R.string.entregue);
        }else if(pedido.getStatus() == 5){
            status = view.getContext().getString(R.string.naoEnviado);
        }

        textStatus.setText(status);
        textViewTotal.setText("R$ "+pedido.getTotal());
        textNumeroMesa.setText(""+pedido.getNumeroMesa());
        layout.setOnClickListener(v -> {
          onListenerAcao.onClick(pedido);
        });

    }


}
