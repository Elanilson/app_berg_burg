package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityItemCardapioBinding;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.view.adapter.ItemCardapioAdapter;
import com.bergburg.bergburg.viewmodel.CardapioViewModel;
import com.bergburg.bergburg.viewmodel.ItemCardapioViewModel;
import com.bergburg.bergburg.viewmodel.MesaViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ItemCardapioActivity extends AppCompatActivity {
    private ActivityItemCardapioBinding binding;
    private ItemCardapioAdapter adapter = new ItemCardapioAdapter();
    private ItemCardapioViewModel viewModel ;
    private MesaViewModel mesaViewModel;
    private Long idCategoria = 0L;
    private String tituloCategoria = "";
    private String observacao = "";
    private Long id_mesa = 0L;
    private int quantidade = 1; // padrao
    private CoordinatorLayout layout;
    private BottomSheetBehavior bottomSheetBehavior;
    private FrameLayout frameLayoutEditarItemPedido;
    private List<Produto> listProdutos = new ArrayList<>();
    private int posicao = 0;
    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemCardapioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        viewModel = new ViewModelProvider(this).get(ItemCardapioViewModel.class);
        mesaViewModel = new ViewModelProvider(this).get(MesaViewModel.class);

        layout = binding.constraintItemCardapio;
        frameLayoutEditarItemPedido = binding.frameSheetEditarItemPedido;
        bottomSheetBehavior =BottomSheetBehavior.from(frameLayoutEditarItemPedido);
        bottomSheetBehavior.setDraggable(false);



        configurarrRecyclerView();
        adapteListener();
        observe();
    }

    private void configuracaoToolbar(){
        // getSupportActionBar().setTitle("Mesa 25");
        Toolbar toolbar = binding.toolbar.toolbarPersonalizado;
        binding.toolbar.textViewLabelToolbar.setText(tituloCategoria);

        binding.toolbar.imageButtonVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);
    }
    private void voltarTela() {
        onBackPressed();
    }



    private void exibirButtonSheetPedido(Produto produto){
        binding.recyclerViewItemCardapio.setVisibility(View.GONE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        EditText editCampoQuantidade = frameLayoutEditarItemPedido.findViewById(R.id.editQuantidade);
        EditText editCampoObservacao = frameLayoutEditarItemPedido.findViewById(R.id.editTextObservacao);
        Button btnConfirmar = frameLayoutEditarItemPedido.findViewById(R.id.buttonConfirmarQuantidade);
        Button btnCancelar = frameLayoutEditarItemPedido.findViewById(R.id.buttonCancelar);
        TextView nomeProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoNomeProduto);
        TextView descricaoProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoDescricao);
        TextView totalProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoTotal);

        nomeProduto.setText(""+produto.getTitulo());
        descricaoProduto.setText(""+produto.getDescricao());
        totalProduto.setText("R$ "+String.format("%.2f", produto.getPreco()));


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String campo = "";
                campo = editCampoQuantidade.getText().toString();
                if(campo != null && campo != "" && !campo.equalsIgnoreCase(" ")){
                    quantidade = Integer.parseInt(campo);

                    observacao = editCampoObservacao.getText().toString();
                    Long indentificadorUnico = System.currentTimeMillis();
                    ItemDePedido item = new ItemDePedido();
                    item.setTitulo(produto.getTitulo());
                    item.setIdProduto(produto.getId());
                    item.setIdCategoria(produto.getIdCategoria());
                    item.setDescricao(produto.getDescricao());
                    item.setPreco(produto.getPreco());
                    item.setQuantidade(quantidade);
                    item.setObservacao(observacao);
                    item.setIndentificadorUnico(String.valueOf(indentificadorUnico));
                    System.out.println(id_mesa+" idMEsa - idProduto"+ item.getIdProduto() );
                    mesaViewModel.adicionarItemComanda(id_mesa,item.getIdProduto(),item.getObservacao(), item.getQuantidade());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    finish();
                }else{
                    configurarSnackBar(layout,"Informe a quantidade nescessária");
                }



            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.recyclerViewItemCardapio.setVisibility(View.VISIBLE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idCategoria = bundle.getLong(Constantes.ID_CATEGORIA);
            tituloCategoria = bundle.getString(Constantes.TITULO_CATEGORIA);
            id_mesa = Long.parseLong(String.valueOf(bundle.getInt(Constantes.ID_MESA)));
            viewModel.produtosPorCategoria(idCategoria);

            configuracaoToolbar();
        }
    }

    private void configurarSnackBar(View view, String mensagem){
        Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewItemCardapio.setLayoutManager(manager);
        binding.recyclerViewItemCardapio.setAdapter(adapter);
       // swipe();
    }

    private void adapteListener() {
        OnListenerAcao<Produto> onListenerAcao = new OnListenerAcao<Produto>() {
            @Override
            public void onClick(Produto produto) {
                //solicitarQuantidade(produto);
               //alertaQuantidade(produto);
                exibirButtonSheetPedido(produto);


            }

            @Override
            public void onLongClick(Produto obj) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }

    private void observe() {
        mesaViewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(ItemCardapioActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ItemCardapioActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        viewModel.produtos.observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                if(produtos != null){
                    if(produtos.size() > 0){
                        binding.progressBaritemCardapio.setVisibility(View.GONE);
                        listProdutos.addAll(produtos);
                        adapter.attackProdutos(produtos);
                    }else{
                        binding.progressBaritemCardapio.setVisibility(View.VISIBLE);

                    }
                }else{
                        binding.progressBaritemCardapio.setVisibility(View.VISIBLE);

                }
            }
        });


        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(!resposta.getStatus()){
                    System.out.println("Produto: "+resposta.getMensagem());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        recuperar();

    }

    @Override
    protected void onStop() {
        super.onStop();
      //
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.CancelarRequisicoes();
    }
}