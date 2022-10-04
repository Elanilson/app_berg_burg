package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
    private int numeroMesa = 0;
    private int quantidade = 1;

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemCardapioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ItemCardapioViewModel.class);


        getSupportActionBar().setTitle(tituloCategoria);
        configurarrRecyclerView();
        adapteListener();
        observe();
    }

    private void solicitarQuantidade(Produto produto){
        new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Quantidade teste")
                .setMessage("====== teste ====")
                .setPositiveButton("Quero 2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    quantidade = 2;
                        viewModel.salvarProdutoSelecionado(numeroMesa,produto.getId(),quantidade);
                        finish();
                    }
                })
                .setNeutralButton("Quero 1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quantidade = 1;
                        viewModel.salvarProdutoSelecionado(numeroMesa,produto.getId(),quantidade);
                        finish();
                    }
                })
                .show();
    }

    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idCategoria = bundle.getLong("idCategoria");
            tituloCategoria = bundle.getString("tituloCategoria");
            numeroMesa = bundle.getInt("numeroMesa");
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
            public void onClick(Produto produto) {
                solicitarQuantidade(produto);


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

    @Override
    protected void onResume() {
        super.onResume();
        recuperar();

    }
}