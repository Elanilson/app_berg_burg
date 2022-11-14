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
import android.view.View;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityConsultarPedidoBinding;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.PedidoAdapter;
import com.bergburg.bergburg.viewmodel.ConsultaPedidoViewModel;
import com.bergburg.bergburg.viewmodel.MesaViewModel;

import java.util.List;

public class ConsultarPedidoActivity extends AppCompatActivity {
    private ActivityConsultarPedidoBinding binding;
    private PedidoAdapter adapter = new PedidoAdapter();
    private ConsultaPedidoViewModel viewModel;
    private MesaViewModel mesaViewModel;
    private Mesa mMesa = new Mesa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultarPedidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        viewModel = new ViewModelProvider(this).get(ConsultaPedidoViewModel.class);
        mesaViewModel = new ViewModelProvider(this).get(MesaViewModel.class);

        binding.editTextPesquisaPedido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  System.out.println("beforeTextChanged: "+s);

            }

            @Override
            public void onTextChanged(CharSequence texto, int start, int before, int count) {
                   Long numeroMesa = 0L;
               if(texto != null && texto != " " && !texto.toString().isEmpty()){
                   numeroMesa = Long.parseLong(texto.toString());
                    System.out.println("onTextChanged: "+texto);

                   viewModel.getPedidos(numeroMesa);


               }else{
                   viewModel.getTodosOsPedidos();
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

                Bundle bundle = new Bundle();
                bundle.putLong(Constantes.ID_PEDIDO,pedido.getId());
                Intent intent = new Intent(ConsultarPedidoActivity.this,ExibirPedidoctivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onLongClick(Pedido pedido) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }
    private void observe() {
        mesaViewModel.mesa.observe(this, new Observer<Mesa>() {
            @Override
            public void onChanged(Mesa mesa) {
                mMesa = mesa;
            }
        });
        viewModel.pedidos.observe(this, new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                if(pedidos != null){
                    if(pedidos.size() > 0){
                        binding.progressBarConPedido.setVisibility(View.GONE);
                        binding.textViewSemPedidos.setVisibility(View.GONE);
                        adapter.limparPedidos();
                        adapter.attackPedidos(pedidos);
                    }else{
                        binding.progressBarConPedido.setVisibility(View.VISIBLE);
                        binding.textViewSemPedidos.setVisibility(View.VISIBLE);
                    }
                }else{
                    binding.progressBarConPedido.setVisibility(View.GONE);
                    binding.textViewSemPedidos.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(ConsultarPedidoActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ConsultarPedidoActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    private void idParaProximaTela(){
        Intent intent = new Intent(ConsultarPedidoActivity.this,MesaActivity.class);
        intent.putExtra(Constantes.MESA,mMesa);
        startActivity(intent);
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
        viewModel.getTodosOsPedidos();
    }
}