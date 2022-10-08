package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.ItemDoPedidoRepositorio;
import com.bergburg.bergburg.repositorio.MesaRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;

import java.util.ArrayList;
import java.util.List;

public class MesaViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;
    private PedidoRepositorio pedidoRepositorio;
    private MesaRepositorio mesaRepositorio;
    private ItemDoPedidoRepositorio itemDoPedidoRepositorio;


    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    private MutableLiveData<List<ItemDePedido>> _ItensDoPedido = new MutableLiveData<>();
    public LiveData<List<ItemDePedido>> itensDoPedido = _ItensDoPedido;


    private MutableLiveData<Pedido> _Pedido = new MutableLiveData<>();
    public LiveData<Pedido> pedido = _Pedido;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    private List<ItemDePedido> itensDoPedido_Sem_OsRemovidos = new ArrayList<>();

    public MesaViewModel(@NonNull Application application) {
        super(application);

        repositorio = new ProdutosRepositorio(application.getApplicationContext());
        pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
        mesaRepositorio = new MesaRepositorio(application.getApplicationContext());
        itemDoPedidoRepositorio = new ItemDoPedidoRepositorio(application.getApplicationContext());
    }

    public void getItemPedido(Long id){
        //pecorro os itens da tebela itensDoPedido
        for(ItemDePedido item: pedidoRepositorio.getItensDoPedido(id)){
            //caso não sincroninazo ele vai sincronizar - principalmente os removidos
            if(item.getSincronizado().equalsIgnoreCase(Constantes.NAO)){
                salvar_OU_Atualizar_ItemDoPedidoOnline(item);
            }
            //somente os itens ativos
           if(item.getStatus().equalsIgnoreCase(Constantes.ATIVO)){
               itensDoPedido_Sem_OsRemovidos.add(item);
           }
        }
       // _ItensDoPedido.setValue(pedidoRepositorio.getItensDoPedido(id));
        _ItensDoPedido.setValue(itensDoPedido_Sem_OsRemovidos);
      //  _Produtos.setValue(produtoList);
    }

    public void getPedido(int idMesa){
        _Pedido.setValue(pedidoRepositorio.getPedido(idMesa));
    }

    public void atualizarTotalPedido(Pedido pedido){
        pedidoRepositorio.update(pedido);
    }

    //local
    public void sincronizarPedido(Long idUsuario, Mesa mesa,String identificadorUnico){
        salvarPedidoOnline(idUsuario,mesa.getId(),Constantes.ABERTO,Constantes.NAO_ENVIADO,0f,identificadorUnico);
    }

    public void abrirPedido(Long idUsuario, Mesa mesa,String identificadorUnico){
        sincronizarPedido(idUsuario,mesa,identificadorUnico);
       if( pedidoRepositorio.insert(new Pedido(idUsuario,mesa.getId(),Constantes.ABERTO,Constantes.NAO_ENVIADO,identificadorUnico))){
           mesa.setLivre(Constantes.OCUPADO);
           mesaRepositorio.update(mesa); //atualiza a mesa
           _Resposta.setValue(new Resposta(Constantes.ABERTO,true));
       }
    }
    public void fecharPedido(Pedido pedido,Mesa mesa){
        if(pedidoRepositorio.update(pedido)){
            mesa.setLivre(Constantes.LIVRE);
            mesaRepositorio.update(mesa); //atualiza
            _Resposta.setValue(new Resposta(Constantes.FECHADO,true));
        }
    }

    public void cancelarPedido(Pedido pedido,Mesa mesa){
        pedido.setSincronizado(Constantes.NAO);
        if(pedidoRepositorio.update(pedido)){
            mesa.setLivre(Constantes.LIVRE);
            mesaRepositorio.update(mesa); //atualiza
            _Resposta.setValue(new Resposta(Constantes.CANCELADO,true));
        }
    }

    public void salvarPedidoOnline(Long idUsuario,int idMesa,String aberturaPedido,String status,Float total, String identificadorUnico){
        APIListener<Pedido> listener = new APIListener<Pedido>() {
            @Override
            public void onSuccess(Pedido result) {

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
        pedidoRepositorio.salvarPedidoOnline(listener,idUsuario,idMesa,aberturaPedido,status,total,identificadorUnico);
    }

    public void atualizarPedidoOnline(Long idPedido,String indentificador,Float total,String status, String aberturaPedido){
        APIListener<Pedido> listene = new APIListener<Pedido>() {
            @Override
            public void onSuccess(Pedido result) {

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };

        pedidoRepositorio.atualizarPedidoOnline(listene,idPedido,indentificador,total,status,aberturaPedido);
    }


    public void salvar_OU_Atualizar_ItemDoPedidoOnline(ItemDePedido item){
        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {
                // precisa ter nada  aqui porque ta sendo feito no repositorio

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }

        };
        itemDoPedidoRepositorio.salvar_OU_Atualizar_ItemDoPedidoOnline(listener,item);
    }

   /* public void salvarProdutoSelecionadoOnline(Long idPedido,Long idProduto,int quantidade,String observacao,String identificador,Float preco,String status){

        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {
                // precisa ter nada  aqui porque ta sendo feito no repositorio

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }

        };
        repositorio.salvarProdutoSelecionadoOnline(listener,idPedido,idProduto,quantidade,observacao,identificador,preco,status);

    }*/

   /* public void atualizarItemDoPedidoOnline(ItemDePedido itemDePedido){
        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
        repositorio.autlizarItemDoPedidoOnline(listener,itemDePedido);
    }*/

}
