package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityConsultarPedidoBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.view.adapter.PedidoAdapter;
import com.bergburg.bergburg.viewmodel.ConsultaPedidoViewModel;

import java.util.List;

public class ConsultarPedidoActivity extends AppCompatActivity {
    private ActivityConsultarPedidoBinding binding;
    private PedidoAdapter adapter = new PedidoAdapter();
    private ConsultaPedidoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultarPedidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ConsultaPedidoViewModel.class);

        binding.editTextPesquisaPedido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  System.out.println("beforeTextChanged: "+s);

            }

            @Override
            public void onTextChanged(CharSequence texto, int start, int before, int count) {
             //   System.out.println("onTextChanged: "+s);
                   int numeroMesa = 0;
               if(texto != null && texto != " " && !texto.toString().isEmpty()){
                   numeroMesa = Integer.parseInt(texto.toString());

                   viewModel.consultarPedido(numeroMesa);

               }else{
                   viewModel.getPedidos();
               }

            }

            @Override
            public void afterTextChanged(Editable s) {
               // System.out.println("afterTextChanged: "+s);

            }
        });

        configurarrRecyclerView();
        observe();
        adapteListener();
    }

    private void adapteListener() {
        OnListenerAcao<Pedido> onListenerAcao = new OnListenerAcao<Pedido>() {
            @Override
            public void onClick(Pedido pedido) {
                Intent intent = new Intent(ConsultarPedidoActivity.this,MesaActivity.class);

                intent.putExtra(Constantes.MESA,new Mesa(pedido.getNumeroMesa()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(Pedido pedido) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }
    private void observe() {
        viewModel.pedidos.observe(this, new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                if(pedidos.size() > 0){
                    adapter.limparPedidos();
                }else{

                }
                adapter.attackPedidos(pedidos);
            }
        });

    }

    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerviewConsulta.setLayoutManager(manager);
        binding.recyclerviewConsulta.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.limparPedidos();
        viewModel.getPedidos();
    }
}