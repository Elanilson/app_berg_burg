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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityMesaBinding;
import com.bergburg.bergburg.helpers.UsuarioPreferences;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.ItensComanda;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.view.adapter.MesaAdapter;
import com.bergburg.bergburg.view.adapter.PedidoAdapter;
import com.bergburg.bergburg.viewmodel.ItemCardapioViewModel;
import com.bergburg.bergburg.viewmodel.MesaViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MesaActivity extends AppCompatActivity {
    private ActivityMesaBinding binding;
    private MesaViewModel viewModel;
    private Pedido pedidoDaMesa = new Pedido();
    private MesaAdapter adapter = new MesaAdapter();
    private PedidoAdapter pedidoAdapter = new PedidoAdapter();
    private Mesa mesa = new Mesa();
    private ItemDePedido itemDePedido = new ItemDePedido();
    private ItemCardapioViewModel itemCardapioViewModel;
    private List<ItensComanda> listItens = new ArrayList<>();
    private int posicao = 0;
    private Usuario usuarioLogado = new Usuario();


    private int quantidade = 1; // padrao
    private String observacao = "";
    private CoordinatorLayout layout;
    private TextView textViewTotalDaMesa;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetBehavior bottomSheetBehaviorPedidos;
    private FrameLayout frameLayoutEditarItemPedido;
    private FrameLayout frameLayoutPedidos;

    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;
    private  int totalPedidos = 0;
    private UsuarioPreferences preferences;
    private AlertDialog alertDialogCarregamento;
    private Boolean esconderPedidos = false;
    private Boolean carregarUmaVez = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alertaDeCarregando();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        viewModel = new ViewModelProvider(this).get(MesaViewModel.class);
        layout = binding.constraintMesa;
        textViewTotalDaMesa = binding.textViewTotalDaMesa;
        frameLayoutEditarItemPedido = binding.frameSheetEditarItemPedido;
        frameLayoutPedidos = binding.frameSheetPedidosAbertos;
        bottomSheetBehavior =BottomSheetBehavior.from(frameLayoutEditarItemPedido);
        bottomSheetBehaviorPedidos =BottomSheetBehavior.from(frameLayoutPedidos);

        preferences = new UsuarioPreferences(this);


        binding.buttonAdicionarProdutos.setOnClickListener(v -> abriCardapio());
        binding.buttonEnviarComnda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEnVioDeComanda();
               // impremirPedido();
            }
        });

        bottomSheetBehavior.setDraggable(false);



        observe();
        configurarrRecyclerView();
        configurarrRecyclerViewPedidos();
        adapteListener();
        adapteListenerPedido();


    }

    private void impremirPedido(){
        String notaPedido = "";
        String pedidos = "";
/*
        for (ItemDePedido item : listItens){
            pedidos += item.getIndentificadorUnico()+"   "+item.getTitulo()+"   "+item.getQuantidade()+"    "+(item.getQuantidade() * item.getPreco())+" \n";

        }

        notaPedido +="Berg burg \n" +
                "(91) 9xxxx-xxxx \n" +
                "CNP: xx.xxx.xxx/xxxx-xx \n" +
                "----------------------------- \n"+
                "Impresso em 10/10/2022 \n"+
                "****** NÃO É DOCUMENTO FISCAL***** \n"+
                "Obs: \n"+
                "Pedido n°: \n"+
                "Codigo    Descrição      Qtd.   Subtotal \n"+
                pedidos+
                ""+
                ""+
                "\n";

        System.out.println(notaPedido);*/
    }
    private void exibirButtonSheetPedido(ItensComanda itensComanda){
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

        editCampoQuantidade.setText(""+itensComanda.getQuantidade());
        editCampoObservacao.setText(""+itensComanda.getObservacao());
        nomeProduto.setText(""+itensComanda.getTitulo());
        descricaoProduto.setText(""+itensComanda.getDescricao());
        totalProduto.setText("R$ "+String.format("%.2f", itensComanda.getPreco()));
        System.out.println("itensComanda: "+itensComanda.toString());
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

                    viewModel.atualizarItemComanda(itensComanda.getId(),quantidade,observacao);

                   // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

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
        OnListenerAcao<ItensComanda> onListenerAcao = new OnListenerAcao<ItensComanda>() {
            @Override
            public void onClick(ItensComanda item) {
                exibirButtonSheetPedido(item);
            }

            @Override
            public void onLongClick(ItensComanda item) {
                alertaRemocao(item);
            }
        };
        adapter.attackOnListener(onListenerAcao);
    }
    private void adapteListenerPedido() {
        OnListenerAcao<Pedido> onListenerAcao = new OnListenerAcao<Pedido>() {
            @Override
            public void onClick(Pedido pedido) {
                // mesaViewModel.getMesa(pedido.getIdMesa());
                // idParaProximaTela();



                Bundle bundle = new Bundle();
                bundle.putLong(Constantes.ID_PEDIDO,pedido.getId());
               // Intent intent = new Intent(MesaActivity.this,TesteImpremirActivity.class);
                Intent intent = new Intent(MesaActivity.this,ExibirPedidoctivity.class);
              // Intent intent = new Intent(MesaActivity.this,EmitirPedidoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onLongClick(Pedido pedido) {

            }
        };
        pedidoAdapter.attackOnListener(onListenerAcao);
    }
    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewDetalhesMesa.setLayoutManager(manager);
        binding.recyclerViewDetalhesMesa.setAdapter(adapter);
       // swipe();
    }
    private void configurarrRecyclerViewPedidos() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerviewPedidosAbertos.setLayoutManager(manager);
        binding.recyclerviewPedidosAbertos.setAdapter(pedidoAdapter);
        // swipe();
    }
    private void observe() {
        viewModel.pedidos.observe(this, new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                if(pedidos != null){
                    if(pedidos.size() > 0){
                        binding.progressBarMesaPedido.setVisibility(View.GONE);
                        binding.textViewInfoPedidos.setVisibility(View.GONE);
                        totalPedidos = pedidos.size();
                        binding.frameSheetPedidosAbertos.setVisibility(View.VISIBLE);
                        pedidoAdapter.attackPedidos(pedidos);
                    }else{
                        if(esconderPedidos == false){
                            bottomSheetBehaviorPedidos.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            esconderPedidos = true;
                        }
                        binding.frameSheetPedidosAbertos.setVisibility(View.GONE);
                        binding.progressBarMesaPedido.setVisibility(View.VISIBLE);
                        binding.textViewInfoPedidos.setVisibility(View.VISIBLE);
                    }
                }else{
                        binding.progressBarMesaPedido.setVisibility(View.GONE);
                }
            }
        });
        viewModel.mesa.observe(this, new Observer<Mesa>() {
            @Override
            public void onChanged(Mesa mesa) {
                if(mesa != null){
                    if(mesa.getStatus().equalsIgnoreCase(Constantes.LIVRE)){
                        System.out.println(mesa.toString());
                        System.out.println("Livre ? "+mesa.getStatus().equalsIgnoreCase(Constantes.LIVRE));
                        alertDialogCarregamento.dismiss();
                        solicitarAberturaDoPedido();
                    }else{
                        alertDialogCarregamento.dismiss();
                    }
                }
            }
        });

        viewModel.itensComanda.observe(this, new Observer<List<ItensComanda>>() {
            @Override
            public void onChanged(List<ItensComanda> itensComanda) {
                if(itensComanda != null){
                    if(itensComanda.size() > 0){
                        if(carregarUmaVez == false){
                            carregarUmaVez = true;
                            bottomSheetBehaviorPedidos.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                        listItens.addAll(itensComanda);
                        binding.progressBarMesa.setVisibility(View.GONE);
                        binding.layoutImagemFazPed.setVisibility(View.GONE);
                        binding.linearLayoutTotal.setVisibility(View.VISIBLE);
                        binding.buttonEnviarComnda.setVisibility(View.VISIBLE);
                    }else{
                        if(totalPedidos > 0){
                             bottomSheetBehaviorPedidos.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                        System.out.println("sem itens");

                        binding.progressBarMesa.setVisibility(View.GONE);
                        binding.layoutImagemFazPed.setVisibility(View.VISIBLE);
                        binding.buttonEnviarComnda.setVisibility(View.GONE);
                        binding.linearLayoutTotal.setVisibility(View.GONE);
                    }
                    System.out.println("Total "+itensComanda.size());
                    calcularTotalDaMesa(itensComanda);
                    adapter.attackProdutos(itensComanda);
                }else{
                    binding.layoutImagemFazPed.setVisibility(View.VISIBLE);
                    binding.progressBarMesa.setVisibility(View.VISIBLE);

                }
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    if(resposta.getMensagem().equalsIgnoreCase(Constantes.ATUALIZADO)){
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        viewModel.itensComanda(mesa.getId());
                        binding.recyclerViewDetalhesMesa.setVisibility(View.VISIBLE);
                        binding.linearLayoutTotal.setVisibility(View.VISIBLE);
                        binding.layoutButton.setVisibility(View.VISIBLE);
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.REMOVIDO)){
                        viewModel.itensComanda(mesa.getId());
                        binding.recyclerViewDetalhesMesa.setVisibility(View.VISIBLE);
                        binding.linearLayoutTotal.setVisibility(View.VISIBLE);
                        binding.layoutButton.setVisibility(View.VISIBLE);
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.CANCELADO)){
                        finish();
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.ENVIADO)){
                        viewModel.itensComanda(mesa.getId());
                        viewModel.getPedidosAbertos(mesa.getId());
                    }

                    Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();

                }else{
                    if(resposta.getMensagem().equalsIgnoreCase(Constantes.NAO_ATUALIZADO) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.NAO_REMOVIDO) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.NAO_ADICIONADO) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.PEDIDO_NAO_ENVIADO) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.NAO_CONSEGUIR_ABRIR_MESA) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    }else if(resposta.getMensagem().equalsIgnoreCase(Constantes.NAO_CANCELADO) || resposta.getMensagem().equalsIgnoreCase(Constantes.VERIFIQUE_CONEXAO)){
                        Toast.makeText(MesaActivity.this, resposta.getMensagem(), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        viewModel.usuario.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                if(usuario != null){
                    System.out.println(usuario.toString());
                    usuarioLogado.setId(usuario.getId());
                    usuarioLogado.setStatus(usuario.getStatus());
                    usuarioLogado.setCargo(usuario.getCargo());
                    usuarioLogado.setNome(usuario.getNome());
                }

            }
        });
    }
    private void configuracaoToolbar(){

        Toolbar toolbar = binding.includeToolbar.toolbarCentralizado;
        toolbar.setTitle("");
        binding.includeToolbar.textViewToolbarTitulo.setText("Mesa "+mesa.getNumero());
        binding.includeToolbar.imageButtonMesaVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);
    }
    private void abriCardapio(){
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.ID_MESA,Integer.parseInt(String.valueOf(mesa.getId())));
        Intent intent = new Intent(this,CardapioActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void recuperar(){
        if(getIntent().getExtras() != null){
            mesa = (Mesa)  getIntent().getSerializableExtra(Constantes.MESA);
            System.out.println("Recuperado: "+mesa.toString());
            // busco os dados da mesa
            viewModel.getMesa(mesa.getId());
            System.out.println(mesa.toString());
        }
    }
    private void voltarTela() {
       // onBackPressed();
        finish();
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
    private void alertaRemocao(ItensComanda item){
        new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Remoção de item")
                .setMessage("Prosseguir com a remoção")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.removerItemComanda(item.getId());
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
                .setTitle("Cancelamento de comanda")
                .setMessage("A comanda será excluida")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        viewModel.cancelarComanda(mesa.getId());


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
                .setMessage("Confirmar o envio da comanda ?")
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(mesa.getId()+"--"+usuarioLogado.getId()+"--"+mesa.getTotal());
                        viewModel.criarPedido(mesa.getId(), usuarioLogado.getId(),mesa.getTotal());

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
                .setTitle("Abertura de comanda")
                .setCancelable(false)
                .setMessage("Continuar com abertura da comanda ?")
                .setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.abrirMesa(mesa.getId());

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

    private void alertaDeCarregando(){
        alertDialogCarregamento = new AlertDialog.Builder(this)
                .setTitle("Carregando")
                .setCancelable(false)
                .setMessage("Processando informações...")
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
    private void calcularTotalDaMesa(List<ItensComanda> itensComanda){
        if(itensComanda != null){
            Float total = 0f;
            for (ItensComanda item : itensComanda){
               if(item.getQuantidade() != 0 && item.getPreco() != null){
                   total += item.getQuantidade() * item.getPreco();
               }
            }
            mesa.setTotal(total);

            textViewTotalDaMesa.setText("R$ "+String.format("%.2f", total));
        }


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
                    System.out.println("Mesa -Milisegundos: "+System.currentTimeMillis());
                    System.out.println(mesa.toString());

                    viewModel.itensComanda(mesa.getId());
                    viewModel.getPedidosAbertos(mesa.getId());

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mesa,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.menuCancelar:
                   if(totalPedidos <= 0){
                       alertaCancelamento();
                   }else{
                       Toast.makeText(this, "Não é possível cancelar", Toast.LENGTH_SHORT).show();
                   }
                break;
        }
        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();

        ticker = true;
        startClock();

        recuperar();

       // viewModel.getMesa(preferences.recuperarIDUSuario());
        viewModel.getUsuario(preferences.recuperarIDUSuario());

       /* if(listItens != null){
            if(listItens.size() > 0){
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }*/


        adapter.limparProdutos();

        configuracaoToolbar();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ticker = false;
        carregarUmaVez = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ticker = false;
        carregarUmaVez = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticker = false;
    }

}