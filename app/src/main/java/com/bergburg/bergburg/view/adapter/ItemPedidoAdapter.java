package com.bergburg.bergburg.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.view.viewholder.ItemCardapioViewHolder;
import com.bergburg.bergburg.view.viewholder.ItemPedidoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoViewHolder> {
    private List<ItemDePedido> itens = new ArrayList<>();
    private OnListenerAcao<ItemDePedido> onListenerAcao;

    @NonNull
    @Override
    public ItemPedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout__item_cardapio,parent,false);
        return new ItemPedidoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPedidoViewHolder holder, int position) {
        holder.bind(itens.get(position),onListenerAcao);

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
