package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityItemCardapioBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.view.adapter.ItemCardapioAdapter;
import com.bergburg.bergburg.viewmodel.CardapioViewModel;
import com.bergburg.bergburg.viewmodel.ItemCardapioViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemCardapioActivity extends AppCompatActivity {
    private ActivityItemCardapioBinding binding;
    private ItemCardapioAdapter adapter = new ItemCardapioAdapter();
    private ItemCardapioViewModel viewModel ;
    private Long idCategoria = 0L;
    private String tituloCategoria = "";
    private int numeroMesa = 0;
    private int quantidade = 1; // padrao
    private ConstraintLayout layout;

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
        layout = binding.constraintItemCardapio;



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

    private void alertaQuantidade(Produto produto){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_quantidade_pedido);
        dialog.setCancelable(false);
        EditText editCampoQuantidade = dialog.findViewById(R.id.editQuantidade);
        Button btnConfirmar = dialog.findViewById(R.id.buttonConfirmarQuantidade);
        Button btnCancelar = dialog.findViewById(R.id.buttonCancelar);


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String campo = "";
                campo = editCampoQuantidade.getText().toString();
                if(campo != null && campo != "" && !campo.equalsIgnoreCase(" ")){
                    quantidade = Integer.parseInt(campo);

                    viewModel.salvarProdutoSelecionado(numeroMesa,produto.getId(),quantidade);
                    // configurarSnackBar(layout,"Sucesso");
                    Toast.makeText(ItemCardapioActivity.this, getString(R.string.pedido_confirmado), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    finish();
                }else{
                    configurarSnackBar(layout,"Informe a quantidade nescessária");
                }



            }
        });
        btnCancelar.setOnClickListener( v -> dialog.dismiss());

        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

    }

    private void aguardar(int segundos){
        try {
            TimeUnit.SECONDS.sleep(segundos);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idCategoria = bundle.getLong(Constantes.ID_CATEGORIA);
            tituloCategoria = bundle.getString(Constantes.TITULO_CATEGORIA);
            numeroMesa = bundle.getInt(Constantes.NUMERO_MESA);
            viewModel.produtosPorCategoria(idCategoria);
            getSupportActionBar().setTitle(tituloCategoria);
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
    }

    private void adapteListener() {
        OnListenerAcao<Produto> onListenerAcao = new OnListenerAcao<Produto>() {
            @Override
            public void onClick(Produto produto) {
                //solicitarQuantidade(produto);
                alertaQuantidade(produto);


            }

            @Override
            public void onLongClick(Produto obj) {

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