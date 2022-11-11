package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.CategoriasRepositorio;
import com.bergburg.bergburg.repositorio.ItemDoPedidoRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;

import java.util.List;

public class ItemCardapioViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;

    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    private MutableLiveData<List<Produto>> _ProdutosOnline = new MutableLiveData<>();
    public LiveData<List<Produto>> produtosOnline = _ProdutosOnline;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public ItemCardapioViewModel(@NonNull Application application) {
        super(application);

       repositorio = new ProdutosRepositorio(application.getApplicationContext());

    }



    public void produtosPorCategoria(Long idCategoria){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Produtos.setValue(result.getProdutos());

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };
        repositorio.produtosPorCategoria(listener,idCategoria);
    }






}
