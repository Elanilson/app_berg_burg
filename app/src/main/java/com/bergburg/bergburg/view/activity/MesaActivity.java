package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityMesaBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.MesaAdapter;
import com.bergburg.bergburg.viewmodel.ItemCardapioViewModel;
import com.bergburg.bergburg.viewmodel.MesaViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MesaActivity extends AppCompatActivity {
    private ActivityMesaBinding binding;
    private MesaViewModel viewModel;
    private Pedido pedidoDaMesa = new Pedido();
    private MesaAdapter adapter = new MesaAdapter();
    private Mesa mesa = new Mesa();
    private ItemDePedido itemDePedido = new ItemDePedido();
    private ItemCardapioViewModel itemCardapioViewModel;

   // private int numeroMesa = 0;

    private int quantidade = 1; // padrao
    private String observacao = "";
    private CoordinatorLayout layout;
    private TextView textViewTotalDaMesa;
    private BottomSheetBehavior bottomSheetBehavior;
    private FrameLayout frameLayoutEditarItemPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModel = new ViewModelProvider(this).get(MesaViewModel.class);
        itemCardapioViewModel = new ViewModelProvider(this).get(ItemCardapioViewModel.class);
        layout = binding.constraintMesa;
        textViewTotalDaMesa = binding.textViewTotalDaMesa;
        frameLayoutEditarItemPedido = binding.frameSheetEditarItemPedido;
        bottomSheetBehavior =BottomSheetBehavior.from(frameLayoutEditarItemPedido);


        binding.buttonAdicionarProdutos.setOnClickListener(v -> abriCardapio());
        binding.buttonEnviarComnda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEnVioDeComanda();
            }
        });

        bottomSheetBehavior.setDraggable(false);



        observe();
        configurarrRecyclerView();
        adapteListener();


    }

    private void exibirButtonSheetPedido(ItemDePedido itemDePedido){
        binding.recyclerViewDetalhesMesa.setVisibility(View.GONE);
        binding.linearLayoutTotal.setVisibility(View.GONE);
        binding.layoutButton.setVisibility(View.GONE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        EditText editCampoQuantidade = frameLayoutEditarItemPedido.findViewById(R.id.editQuantidade);
        EditText editCampoObservacao = frameLayoutEditarItemPedido.findViewById(R.id.editTextObservacao);
        TextView nomeProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoNomeProduto);
        TextView descricaoProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoDescricao);
        TextView totalProduto = frameLayoutEditarItemPedido.findViewById(R.id.textViewInfoTotal);
        Button btnConfirmar = frameLayoutEditarItemPedido.findViewById(R.id.buttonConfirmarQuantidade);
        Button btnCancelar = frameLayoutEditarItemPedido.findViewById(R.id.buttonCancelar);
        editCampoQuantidade.setText(""+itemDePedido.getQuantidade());
        editCampoObservacao.setText(""+itemDePedido.getObservacao());
        nomeProduto.setText(""+itemDePedido.getTitulo());
        descricaoProduto.setText(""+itemDePedido.getDescricao());
        totalProduto.setText(""+itemDePedido.getPreco());
        System.out.println("Produto: "+itemDePedido.toString());
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String campo = "";
                campo = editCampoQuantidade.getText().toString();
                if(campo != null && campo != "" && !campo.equalsIgnoreCase(" ")){
                    quantidade = Integer.parseInt(campo);
                    observacao = editCampoObservacao.getText().toString();
                    itemDePedido.setQuantidade(quantidade);
                    itemDePedido.setObservacao(observacao);
                    itemCardapioViewModel.atualizarItemDoPedido(itemDePedido,mesa.getId());
                   // itemCardapioViewModel.atualizarQuantidadeItemDoPedido(mesa.getId(),itemDePedido.getId(),quantidade,observacao);
                  //  itemCardapioViewModel.atualizarQuantidadeDoProdutoSelecionadoOnline(itemDePedido.getId(),itemDePedido.getIndentificadorUnico(),quantidade);
                  //  itemCardapioViewModel.autlizarItemDoPedidoOnline(produto.getId(),produto.getIdentificador(),quantidade);
                    // configurarSnackBar(layout,"Sucesso");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }else{
                    configurarSnackBar(layout,"Informe a quantidade nescessária");
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.recyclerViewDetalhesMesa.setVisibility(View.VISIBLE);
                binding.linearLayoutTotal.setVisibility(View.VISIBLE);
                binding.layoutButton.setVisibility(View.VISIBLE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void adapteListener() {
        OnListenerAcao<ItemDePedido> onListenerAcao = new OnListenerAcao<ItemDePedido>() {
            @Override
            public void onClick(ItemDePedido itemDePedido) {
               // viewModel.salvarProdutoSelecionado(numeroMesa,obj.getId());
              //  finish();
             //   alertaAleterarQuantidade(produto);
                exibirButtonSheetPedido(itemDePedido);
            }

            @Override
            public void onLongClick(ItemDePedido itemDePedido) {
                alertaRemocao(itemDePedido);
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
        viewModel.itensDoPedido.observe(this, new Observer<List<ItemDePedido>>() {
            @Override
            public void onChanged(List<ItemDePedido> itensDoPedido) {
                if(itensDoPedido.size() > 0){
                    binding.linearLayoutTotal.setVisibility(View.VISIBLE);
                    binding.buttonEnviarComnda.setVisibility(View.VISIBLE);

                }else{
                    binding.buttonEnviarComnda.setVisibility(View.GONE);
                    binding.linearLayoutTotal.setVisibility(View.GONE);

                }
                calcularTotalDaMesa(itensDoPedido);
                adapter.attackProdutos(itensDoPedido);
            }
        });

        viewModel.pedido.observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                if(pedido != null){
                    pedidoDaMesa = pedido;
                    if(pedido.getAberturaPedido().equalsIgnoreCase(Constantes.FECHADO)){
                        //solicito a abertura do pedido
                        solicitarAberturaDoPedido();
                    }else{
                        //pedido aberto, solicito os produtos do pedido
                        viewModel.getItemPedido(pedido.getId());
                        //sincronizar todos os itens que nao tiverem sincronizados
                       /* if(pedido.getSincronizado().equalsIgnoreCase(Constantes.NAO)){
                            Long identificadorUnico = System.currentTimeMillis(); // pegar o milesegundos
                            viewModel.sincronizarPedido(pedido.getId(),mesa,String.valueOf(identificadorUnico));
                        }*/
                    }
                }else{
                    //solicito a abertura do pedido
                    solicitarAberturaDoPedido();

                }

                //e se nao tiver pedido aberto ele nao retorna nada ne
            }
        });
        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    if(resposta.getMensagem().equals(Constantes.CANCELADO) || resposta.getMensagem().equals(Constantes.FECHADO)){
                        finish();
                    }

                }else{
                    Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                }
            }
        });
        itemCardapioViewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    adapter.limparProdutos();
                    viewModel.getPedido(mesa.getId());
                }else{
                    Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void configuracaoToolbar(){
        // getSupportActionBar().setTitle("Mesa 25");
        Toolbar toolbar = binding.includeToolbar.toolbarCentralizado;
        toolbar.setTitle("");
           binding.includeToolbar.textViewToolbarTitulo.setText("Mesa "+mesa.getNumero());
        /*   binding.includeToolbar.textViewEnviarComanda.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(MesaActivity.this, "Aqui você envia o pedido", Toast.LENGTH_SHORT).show();
               }
           });*/
        binding.includeToolbar.imageButtonMesaVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);
    }

    private void abriCardapio(){
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.ID_MESA,mesa.getId());
        Intent intent = new Intent(this,CardapioActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void recuperar(){
        if(getIntent().getExtras() != null){
            mesa = (Mesa)  getIntent().getSerializableExtra(Constantes.MESA);
         //   System.out.println(mesa.toString());
        }
    }

    private void voltarTela() {
        onBackPressed();
    }

    private void alertaAleterarQuantidade(Produto produto){
      /*  Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_quantidade_pedido);
        dialog.setCancelable(false);
        EditText editCampoQuantidade = dialog.findViewById(R.id.editQuantidade);
        Button btnConfirmar = dialog.findViewById(R.id.buttonConfirmarQuantidade);
        Button btnCancelar = dialog.findViewById(R.id.buttonCancelar);
        editCampoQuantidade.setText(""+produto.getQuantidade());
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String campo = "";
                campo = editCampoQuantidade.getText().toString();
                if(campo != null && campo != "" && !campo.equalsIgnoreCase(" ")){
                    quantidade = Integer.parseInt(campo);
                    itemCardapioViewModel.atualizarQuantidadeDoPedido(mesa.getNumero(),produto.getId(),quantidade);
                    // configurarSnackBar(layout,"Sucesso");
                    dialog.dismiss();

                }else{
                    configurarSnackBar(layout,"Informe a quantidade nescessária");
                }

            }
        });

        btnCancelar.setOnClickListener( v -> dialog.dismiss());

        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();*/

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

    private void alertaRemocao(ItemDePedido itemDePedido){
        new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Remoção de item")
                .setMessage("Proseguir com a remoção")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemCardapioViewModel.removerItemDoPedido(itemDePedido,mesa.getId());
                        //itemCardapioViewModel.removerProdutoDoPedido(mesa.getNumero(),itemDePedido.getId());
                    }
                })
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void alertaCancelamento(){
        new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Cancelamento de pedido")
                .setMessage("O pedido será excluido")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pedidoDaMesa.setAberturaPedido(Constantes.FECHADO);
                        pedidoDaMesa.setStatus(Constantes.CANCELADO);
                        viewModel.cancelarPedido(pedidoDaMesa,mesa);

                    }
                })
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void alertaEnVioDeComanda(){
        new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Comanda")
                .setCancelable(false)
                .setMessage("A Comanda será enviada.")
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void solicitarAberturaDoPedido(){
        new AlertDialog.Builder(this)
                .setTitle("Abertura de Pedido")
                .setCancelable(false)
                .setMessage("Continuar com abertura do pedido ?")
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long identificadorUnico = System.currentTimeMillis();
                        viewModel.abrirPedido(1l,mesa,String.valueOf(identificadorUnico));
                        viewModel.getPedido(mesa.getId());

                    }
                })
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                })
                .show();
    }
    private void calcularTotalDaMesa(List<ItemDePedido> itensDoPedido){
        Float total = 0f;
        for (ItemDePedido ItemDePedido : itensDoPedido){
            total += ItemDePedido.getQuantidade() * ItemDePedido.getPreco();
        }
        pedidoDaMesa.setTotal(total);
        viewModel.atualizarTotalPedido(pedidoDaMesa);//local
        viewModel.atualizarPedidoOnline(pedidoDaMesa.getId(), pedidoDaMesa.getIdentificadorUnico(), pedidoDaMesa.getTotal(),pedidoDaMesa.getStatus(),pedidoDaMesa.getAberturaPedido());
        textViewTotalDaMesa.setText("R$ "+total);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mesa,menu);

        return super.onCreateOptionsMenu(menu);
       // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.menuCancelar:
                if(pedidoDaMesa.getStatus().equalsIgnoreCase(getString(R.string.preparando))){
                    Toast.makeText(this, "Não é possível cancelar, o pedido está sendo preparado", Toast.LENGTH_SHORT).show();
                }else{
                    alertaCancelamento();
                }

                break;
        }
        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();
        recuperar();
        adapter.limparProdutos();
        viewModel.getPedido(mesa.getId());
        configuracaoToolbar();
    }
}