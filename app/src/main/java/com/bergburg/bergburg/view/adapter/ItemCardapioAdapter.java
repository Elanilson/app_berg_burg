package com.bergburg.bergburg.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.view.viewholder.CardapioViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioViewHolder> {
    private List<Categoria> categorias = new ArrayList<>();
    private OnListenerAcao<Categoria> onListenerAcao;

    @NonNull
    @Override
    public CardapioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardapio,parent,false);
        return new CardapioViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CardapioViewHolder holder, int position) {
        holder.bind(categorias.get(position),onListenerAcao);

    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void attackCategorias(List<Categoria> categorias){
        this.categorias = categorias;
        notifyDataSetChanged();
    }
    public void attackOnListener(OnListenerAcao<Categoria> onListenerAcao){
        this.onListenerAcao =  onListenerAcao;
    }
}
