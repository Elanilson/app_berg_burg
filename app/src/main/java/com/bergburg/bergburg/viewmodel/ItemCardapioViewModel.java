package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.CategoriasRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;

import java.util.List;

public class ItemCardapioViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;
    private PedidoRepositorio pedidoRepositorio;


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

    }

    public  void produtosPorCategoria(Long idCategoria){
        _Produtos.setValue(repositorio.produtosPorCategoria(idCategoria));
    }
    public void salvarProdutoSelecionado(int idMesa,Long idProduto,int quantidade,String observacao,String indentificador,Float preco){
        //enviar para se salvo na api
        Long idPedido = pedidoRepositorio.getPedido(idMesa).getId();
        salvarProdutoSelecionadoOnline(idPedido,idProduto,quantidade,observacao,indentificador,preco);
        //salvo localmente
        if(repositorio.salvarProdutoSelecionado(idMesa, idProduto,quantidade,observacao,indentificador,preco)){
            _Resposta.setValue(new Resposta("Salvo",true));
        }
    }
    public void atualizarQuantidadeDoPedido(int idMesa,Long idProduto,int quantidade,String observacao){

        if( repositorio.atualizarQuantidadeDoItemPedido(idMesa, idProduto,quantidade,observacao)){
            _Resposta.setValue(new Resposta("Atualizado",true));
        }
    }
    public void removerProdutoDoPedido(int idMesa,Long idProduto){

        if(repositorio.removerProdutoDoPedido(idMesa, idProduto)){
            _Resposta.setValue(new Resposta("Removido",true));
        }
    }


    public void salvarProdutoSelecionadoOnline(Long idPedido,Long idProduto,int quantidade,String observacao,String identificador,Float preco){

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
        repositorio.salvarProdutoSelecionadoOnline(listener,idPedido,idProduto,quantidade,observacao,identificador,preco);

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
        repositorio.produtosPorCategoriaOnline(listener,id);
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
        repositorio.produtosOnline(listener);
    }

}
