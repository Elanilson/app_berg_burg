package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;

import java.util.ArrayList;
import java.util.List;

public class MesaViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;
    private PedidoRepositorio pedidoRepositorio;

    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    private MutableLiveData<Pedido> _Pedido = new MutableLiveData<>();
    public LiveData<Pedido> pedido = _Pedido;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    private List<Produto> produtoList = new ArrayList<>();

    public MesaViewModel(@NonNull Application application) {
        super(application);

        repositorio = new ProdutosRepositorio(application.getApplicationContext());
        pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
    }

    public void getItemPedido(Long id){
        //pecorro os itens da tebela itensDoPedido
        for(ItemDePedido item: pedidoRepositorio.getItensDoPedido(id)){
            //busco o produto conforme o id
            //e crio uma lista de produtos
            Produto produto = new Produto();
            produto = repositorio.getProduto(item.getIdProduto());
            produto.setQuantidade(item.getQuantidade());
            produtoList.add(produto);
        }

        _Produtos.setValue(produtoList);
    }

    public void getPedido(int numeroMesa){
        _Pedido.setValue(pedidoRepositorio.getPedido(numeroMesa));
    }

    public void abrirPedido(Long idUsuario,int numeroMesa){
       if( pedidoRepositorio.insert(new Pedido(idUsuario,numeroMesa,1))){
           _Resposta.setValue(new Resposta(Constantes.PEDIDO_ABERTO,true));
       }
    }
    public void fecharPedido(Pedido pedido){
        if(pedidoRepositorio.update(pedido)){
            _Resposta.setValue(new Resposta(Constantes.PEDIDO_FECHADO,true));
        }
    }

}