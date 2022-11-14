package com.bergburg.bergburg.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.view.viewholder.ItemPedidoInfoViewHolder;
import com.bergburg.bergburg.view.viewholder.ItemPedidoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoInfoAdapter extends RecyclerView.Adapter<ItemPedidoInfoViewHolder> {
    private List<ItemDePedido> itens = new ArrayList<>();
    private OnListenerAcao<ItemDePedido> onListenerAcao;

    @NonNull
    @Override
    public ItemPedidoInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pedido_info,parent,false);
        return new ItemPedidoInfoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPedidoInfoViewHolder holder, int position) {
        holder.bind(itens.get(position),onListenerAcao,position);

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void attackProdutos(List<ItemDePedido> produtos){
        this.itens = produtos;
        notifyDataSetChanged();
    }
    public void attackOnListener(OnListenerAcao<ItemDePedido> onListenerAcao){
        this.onListenerAcao =  onListenerAcao;
    }
}
