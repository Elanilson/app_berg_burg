package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivitySelecionarMesaBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.SelecionarMesaGridAdapter;
import com.bergburg.bergburg.viewmodel.SelecionarMesaViewModel;

import java.util.List;

public class SelecionarMesaActivity extends AppCompatActivity {
    private ActivitySelecionarMesaBinding binding;

   // private SelecionarMesaAdapter adapter = new SelecionarMesaAdapter();
    private SelecionarMesaViewModel viewModel ;
    private OnListenerAcao<Mesa> onListenerAcao;
    private SelecionarMesaGridAdapter mesasAdapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelecionarMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.selecionar_mesa);
        viewModel = new ViewModelProvider(this).get(SelecionarMesaViewModel.class);
        mesasAdapte = new SelecionarMesaGridAdapter(getApplicationContext());

       // viewModel.listarMesasOnline();

        onListenerAcao = new OnListenerAcao<Mesa>() {
            @Override
            public void onClick(Mesa mesa) {

                Intent intent = new Intent(SelecionarMesaActivity.this,MesaActivity.class);
                intent.putExtra(Constantes.MESA,mesa);
                startActivity(intent);

               // startActivity(new Intent(SelecionarMesaActivity.this,MesaActivity.class));
            }

            @Override
            public void onLongClick(Mesa obj) {

            }
        };
        mesasAdapte.attackListener(onListenerAcao);


        observe();
        configurarRecyclerview();



    }

    private void observe() {
        viewModel.mesas.observe(this, new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                mesasAdapte.limparMesas();
                mesasAdapte.attackMesas(mesas);
            }
        });
     /*   viewModel.mesasOnline.observe(this, new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                mesasAdapte.limparMesas();
                mesasAdapte.attackMesas(mesas);
            }
        });*/
        viewModel.itensDosPedidos.observe(this, new Observer<List<ItemDePedido>>() {
            @Override
            public void onChanged(List<ItemDePedido> itensDePedidos) {
                viewModel.sincronizarItensDosPedidos(itensDePedidos);
            }
        });

        viewModel.pedidos.observe(this, new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                viewModel.sincronizarPedidoOnline(pedidos);
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(!resposta.getStatus()){
                    Toast.makeText(SelecionarMesaActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configurarRecyclerview(){
       // GridLayoutManager manager = new GridLayoutManager(this,3);
     //   manager.setOrientation(RecyclerView.HORIZONTAL);
      //  binding.recyclerViewSelecionarMesa.setLayoutManager(manager);
       // binding.recyclerViewSelecionarMesa.setAdapter(adapter);
        //binding.gridviewteste.setAdapter(adapter);
        //binding.gridLayout.setad
        binding.gridViewMesas.setAdapter(mesasAdapte);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  MenuInflater inflater = getMenuInflater();
     //   inflater.inflate(R.menu.menu_principal,menu);
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
        mesasAdapte.limparMesas();
        viewModel.listaDeMesas();
        viewModel.carregarItensDosPedidos();
        viewModel.carregarPedidos();

    }
}