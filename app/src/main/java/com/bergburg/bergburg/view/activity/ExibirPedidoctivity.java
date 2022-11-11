package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityExibirPedidoctivityBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.ItensComanda;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.ItemPedidoAdapter;
import com.bergburg.bergburg.viewmodel.ExibirPedidoViewModel;

import java.util.Calendar;
import java.util.List;

public class ExibirPedidoctivity extends AppCompatActivity {
    private ActivityExibirPedidoctivityBinding binding;
    private ExibirPedidoViewModel viewModel;
    private ItemPedidoAdapter adapter = new ItemPedidoAdapter();
    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;
    private Long idPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExibirPedidoctivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ExibirPedidoViewModel.class);
        recuperar();
        configurarrRecyclerView();
        adapteListener();
        configuracaoToolbar();
        observe();
    }

    private void configuracaoToolbar(){
        Toolbar toolbar = binding.toolbar.toolbarPersonalizado;
        binding.toolbar.textViewLabelToolbar.setText("Voltar");

        binding.toolbar.imageButtonVoltar.setOnClickListener(v -> finish());
        setSupportActionBar(toolbar);
    }
    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
           idPedido = bundle.getLong(Constantes.ID_PEDIDO);

        }
    }
    private void adapteListener() {
        OnListenerAcao<ItemDePedido> onListenerAcao = new OnListenerAcao<ItemDePedido>() {
            @Override
            public void onClick(ItemDePedido item) {

            }

            @Override
            public void onLongClick(ItemDePedido item) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }
    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerviewItensPedido.setLayoutManager(manager);
        binding.recyclerviewItensPedido.setAdapter(adapter);
        // swipe();
    }

    private  void observe(){
        viewModel.pedido.observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                if(pedido != null){
                    binding.progressBarPedido.setVisibility(View.GONE);
                    binding.textViewIdPedido.setText(""+pedido.getId());
                    binding.textViewIdMesa.setText(""+pedido.getIdMesa());
                    binding.textViewPedido.setText(pedido.getAberturaPedido());
                    binding.textViewStatus.setText(pedido.getStatus());
                    binding.textViewTotal.setText("R$ "+String.format("%.2f", pedido.getTotal()));
                }else{
                    binding.progressBarPedido.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.itensDoPedido.observe(this, new Observer<List<ItemDePedido>>() {
            @Override
            public void onChanged(List<ItemDePedido> itemDePedidos) {

                if(itemDePedidos != null){
                    binding.progressBarPedidoItem.setVisibility(View.GONE);
                    adapter.attackProdutos(itemDePedidos);
                }else{
                    binding.progressBarPedidoItem.setVisibility(View.VISIBLE);

                }
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(ExibirPedidoctivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExibirPedidoctivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void startClock(){

        final Calendar calendar = Calendar.getInstance();
        this.runnable = new Runnable() {
            @Override
            public void run() {
                if(!ticker){
                    return;
                }

                try {
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    System.out.println("Exibir_Pedido -Milisegundos: "+System.currentTimeMillis());

                    if(idPedido != null){
                        viewModel.getPedido(idPedido);
                        viewModel.getItensPedido(idPedido);
                    }

                    Long now = SystemClock.uptimeMillis();
                    Long next = now + (1000 - (now % 1000));
                    handler.postAtTime(runnable,next);

                }catch (Exception e){
                    System.out.println("Error "+e.getMessage());
                }


            }
        };
        this.runnable.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ticker = true;
        startClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ticker = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ticker = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticker = false;
    }

}