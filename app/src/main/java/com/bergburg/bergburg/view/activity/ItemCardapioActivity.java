package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityItemCardapioBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.view.adapter.ItemCardapioAdapter;
import com.bergburg.bergburg.viewmodel.CardapioViewModel;
import com.bergburg.bergburg.viewmodel.ItemCardapioViewModel;

import java.util.List;

public class ItemCardapioActivity extends AppCompatActivity {
    private ActivityItemCardapioBinding binding;
    private ItemCardapioAdapter adapter = new ItemCardapioAdapter();
    private ItemCardapioViewModel viewModel ;
    private Long idCategoria = 0L;
    private String tituloCategoria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemCardapioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ItemCardapioViewModel.class);
        recuperarId();
        getSupportActionBar().setTitle(tituloCategoria);

        configurarrRecyclerView();
        adapteListener();
        observe();
    }

    private void recuperarId(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idCategoria = bundle.getLong("idCategoria");
            tituloCategoria = bundle.getString("tituloCategoria");
            viewModel.produtosPorCategoria(idCategoria);
        }
    }

    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewItemCardapio.setLayoutManager(manager);
        binding.recyclerViewItemCardapio.setAdapter(adapter);
    }

    private void adapteListener() {
        OnListenerAcao<Produto> onListenerAcao = new OnListenerAcao<Produto>() {
            @Override
            public void onClick(Produto obj) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }

    private void observe() {
        viewModel.produtos.observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                adapter.attackProdutos(produtos);
            }
        });
    }
}