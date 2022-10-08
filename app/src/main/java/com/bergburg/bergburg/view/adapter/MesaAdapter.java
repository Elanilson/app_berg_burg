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
import com.bergburg.bergburg.view.viewholder.MesaViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MesaAdapter extends RecyclerView.Adapter<MesaViewHolder> {
    private List<ItemDePedido> itensDoPedido = new ArrayList<>();
    private OnListenerAcao<ItemDePedido> onListenerAcao;

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout__item_cardapio,parent,false);
        return new MesaViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder holder, int position) {
        holder.bind(itensDoPedido.get(position),onListenerAcao);

    }

    @Override
    public int getItemCount() {
        return itensDoPedido.size();
    }

    public void attackProdutos(List<ItemDePedido> itensDoPedido){
        this.itensDoPedido = itensDoPedido;
        notifyDataSetChanged();

    }
    public void limparProdutos(){
        this.itensDoPedido.clear();
        notifyDataSetChanged();
    }
    public void attackOnListener(OnListenerAcao<ItemDePedido> onListenerAcao){
        this.onListenerAcao =  onListenerAcao;
    }
}
