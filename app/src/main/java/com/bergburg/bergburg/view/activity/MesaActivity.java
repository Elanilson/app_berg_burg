package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityMesaBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.view.adapter.MesaAdapter;
import com.bergburg.bergburg.viewmodel.MesaViewModel;

import java.util.List;

public class MesaActivity extends AppCompatActivity {
    private ActivityMesaBinding binding;
    private MesaViewModel viewModel;
    private MesaAdapter adapter = new MesaAdapter();
    private int numeroMesa = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MesaViewModel.class);


        binding.buttonAdicionarProdutos.setOnClickListener(v -> abriCardapio());

        configuracaoToolbar();
        observe();
        configurarrRecyclerView();
        adapteListener();


    }

    private void adapteListener() {
        OnListenerAcao<Produto> onListenerAcao = new OnListenerAcao<Produto>() {
            @Override
            public void onClick(Produto obj) {
               // viewModel.salvarProdutoSelecionado(numeroMesa,obj.getId());
              //  finish();

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }


    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewDetalhesMesa.setLayoutManager(manager);
        binding.recyclerViewDetalhesMesa.setAdapter(adapter);
    }

    private void observe() {
        viewModel.produtos.observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                adapter.attackProdutos(produtos);
            }
        });

        viewModel.pedido.observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                if(pedido != null){
                    if(pedido.getAberto() == 0){
                        //solicito a abertura do pedido
                        solicitarAberturaDoPedido();
                    }else{
                        //pedido aberto, solicito os produtos do pedido
                        viewModel.getItemPedido(pedido.getId());
                    }
                }else{
                    //solicito a abertura do pedido
                    solicitarAberturaDoPedido();

                }

                //e se nao tiver pedido aberto ele nao retorna nada ne
            }
        });
    }

    private void configuracaoToolbar(){
        // getSupportActionBar().setTitle("Mesa 25");
        Toolbar toolbar = binding.toolbarMesa.toolbarCentralizado;
        binding.toolbarMesa.textViewToolbarTitulo.setText("Mesa "+numeroMesa);
        binding.toolbarMesa.imageButtonMesaVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);
    }

    private void abriCardapio(){
        Bundle bundle = new Bundle();
        bundle.putInt("numeroMesa",numeroMesa);
        Intent intent = new Intent(this,CardapioActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void voltarTela() {
        onBackPressed();
    }

    private void solicitarAberturaDoPedido(){
        new AlertDialog.Builder(this)
                .setTitle("Abertura de Pedido")
                .setMessage("Continuar com abertura do pedido ?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.abrirPedido(1l,numeroMesa);
                        viewModel.getPedido(numeroMesa);

                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
       // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getPedido(numeroMesa);
    }
}