package com.bergburg.bergburg.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
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
        if(pedido.getStatus().equalsIgnoreCase(Constantes.ENVIADO)){
            status = view.getContext().getString(R.string.enviado);
        }else if(pedido.getStatus().equalsIgnoreCase(Constantes.CANCELADO)){
            status = view.getContext().getString(R.string.cancelado);
        }else if(pedido.getStatus().equalsIgnoreCase(Constantes.PREPARANDO)){
            status = view.getContext().getString(R.string.preparando);
        }else if(pedido.getStatus().equalsIgnoreCase(Constantes.CONCLUIDO)){
            status = view.getContext().getString(R.string.concluido);

        }else if(pedido.getStatus().equalsIgnoreCase(Constantes.NAO_ENVIADO)){
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
