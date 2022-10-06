package com.bergburg.bergburg.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.view.viewholder.ConsultaPedidoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<ConsultaPedidoViewHolder> {
    private List<Pedido> pedidos = new ArrayList<>();
    private OnListenerAcao<Pedido> onListenerAcao;

    @NonNull
    @Override
    public ConsultaPedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pedido,parent,false);
        return new ConsultaPedidoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultaPedidoViewHolder holder, int position) {
        holder.bind(pedidos.get(position),onListenerAcao);

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public void attackPedidos(List<Pedido> pedidos){
        this.pedidos = pedidos;
        notifyDataSetChanged();

    }
    public void limparPedidos(){
        this.pedidos.clear();
        notifyDataSetChanged();
    }
    public void attackOnListener(OnListenerAcao<Pedido> onListenerAcao){
        this.onListenerAcao =  onListenerAcao;
    }
}
