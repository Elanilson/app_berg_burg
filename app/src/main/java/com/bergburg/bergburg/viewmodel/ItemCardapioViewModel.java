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
    private PedidoRepositorio pedidoRepositorio;
    private ItemDoPedidoRepositorio itemDoPedidoRepositorio;


    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    private MutableLiveData<List<Produto>> _ProdutosOnline = new MutableLiveData<>();
    public LiveData<List<Produto>> produtosOnline = _ProdutosOnline;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public ItemCardapioViewModel(@NonNull Application application) {
        super(application);

       repositorio = new ProdutosRepositorio(application.getApplicationContext());
       pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
       itemDoPedidoRepositorio = new ItemDoPedidoRepositorio(application.getApplicationContext());

    }

    public  void produtosPorCategoria(Long idCategoria){
        _Produtos.setValue(repositorio.produtosPorCategoria(idCategoria));
    }

    public void salvarItemDoPedido(ItemDePedido item, int idMesa){
        Long idPedido = pedidoRepositorio.getPedido(idMesa).getId();
        item.setIdPedido(idPedido);
       if( itemDoPedidoRepositorio.salvarItemDoPedido(item)){
           salvar_OU_Atualizar_ItemDoPedidoOnline(item);
           //salvarProdutoSelecionadoOnline(idPedido,item.getIdProduto(),item.getQuantidade(),item.getObservacao(),item.getIndentificadorUnico(),item.getPreco(), Constantes.ATIVO);
           _Resposta.setValue(new Resposta("Salvo",true));
       }
    }

    public void atualizarItemDoPedido(ItemDePedido item ,int idMesa){
        Long idPedido = pedidoRepositorio.getPedido(idMesa).getId();
        item.setIdPedido(idPedido);
        if( itemDoPedidoRepositorio.atualizarItemDoPedido(item)){
            _Resposta.setValue(new Resposta("Atualizado",true));
        }
    }


    public void removerItemDoPedido(ItemDePedido item ,int idMesa){
        Long idPedido = pedidoRepositorio.getPedido(idMesa).getId();
        item.setIdPedido(idPedido);
        if( itemDoPedidoRepositorio.removerItemDoPedido(item)){
            _Resposta.setValue(new Resposta("Removido",true));
        }else{
            _Resposta.setValue(new Resposta("Não Removido: "+item.toString()));
        }
    }


    public void salvarProdutoSelecionado(int idMesa,Long idProduto,int quantidade,String observacao,String indentificador,Float preco,String status){
        //enviar para se salvo na api
       // Long idPedido = pedidoRepositorio.getPedido(idMesa).getId();
        //salvarProdutoSelecionadoOnline(idPedido,idProduto,quantidade,observacao,indentificador,preco, Constantes.ATIVO);
        //salvo localmente
       /* if(repositorio.salvarProdutoSelecionado(idMesa, idProduto,quantidade,observacao,indentificador,preco,status)){
            _Resposta.setValue(new Resposta("Salvo",true));
        }*/
    }
    public void atualizarQuantidadeItemDoPedido(int idMesa,Long idProduto,int quantidade,String observacao){
       /* if( repositorio.atualizarQuantidadeDoItemPedido(idMesa, idProduto,quantidade,observacao)){
            _Resposta.setValue(new Resposta("Atualizado",true));
        }*/
    }

    public void removerProdutoDoPedido(int idMesa,Long idProduto){
      /*  if(repositorio.removerProdutoDoPedido(idMesa, idProduto)){
            _Resposta.setValue(new Resposta("Removido",true));
        }*/
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


  /*  public void salvarProdutoSelecionadoOnline(Long idPedido,Long idProduto,int quantidade,String observacao,String identificador,Float preco,String status){

        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {
                // precisa ter nada  aqui porque ta sendo feito no repositorio

                _Resposta.setValue(new Resposta(result.toString()));
            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }

        };
        repositorio.salvarProdutoSelecionadoOnline(listener,idPedido,idProduto,quantidade,observacao,identificador,preco,status);

    }*/

    public  void atualizarQuantidadeDoProdutoSelecionadoOnline(Long idItemDoPedido,String indentificador,int quantidade){

        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
       // repositorio.atualizarQuantidadeDoProdutoSelecionadoOnline(listener,idItemDoPedido,indentificador,quantidade);
    }

    public  void autlizarItemDoPedidoOnline(Long idProduto,String indentificador,int quantidade){

        APIListener<ItemDePedido> listener = new APIListener<ItemDePedido>() {
            @Override
            public void onSuccess(ItemDePedido result) {

            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
       // repositorio.autlizarItemDoPedidoOnline(listener,idProduto,indentificador,quantidade);
    }


    public void produtosPorCategoriaOnline(Long id){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Produtos.setValue(result.produtos);
            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
       // repositorio.produtosPorCategoriaOnline(listener,id);
    }
    public void produtosOnline(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Produtos.setValue(result.produtos);
            }

            @Override
            public void onFailures(String mensagem) {
                _Resposta.setValue(new Resposta(mensagem));
            }
        };
     //   repositorio.produtosOnline(listener);
    }

}
