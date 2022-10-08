package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosRepositorio {
    private Context context;
    private ProdutoDAO produtoDAO;
    private ItemDePedidoDAO itemDePedidoDAO;
    private PedidoDAO pedidoDAO;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    public ProdutosRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        produtoDAO = db.produtoDAO();
        itemDePedidoDAO = db.itemDePedidoDAO();
        pedidoDAO = db.pedidoDAO();
    }

    public Boolean insert(Produto produto){
        return produtoDAO.insert(produto ) > 0;
    }
    public Boolean update(Produto produto){
        return produtoDAO.update(produto) > 0;
    }
    public void deletar(){
        //delete não deve deletar apenas não deixar visivel
    }


    public Produto getProduto(Long id){
        return produtoDAO.getProduto(id);
    }

    public List<Produto> produtosPorCategoria(Long idCategoria){
        return produtoDAO.getProdutosPorCategoria(idCategoria);
    }


    public  List<Produto> produtos(){
        return produtoDAO.produtos();
    }

    public Boolean atualizarSincronismo(Long id, String sincronizado){
        return itemDePedidoDAO.atualizarSincronismo(id,sincronizado) > 0;
    }

    public Boolean salvarProdutoSelecionado(int id_mesa,Long idProduto,int quantidade,String observacao,String indentificador,Float preco){
        Long idPedido = pedidoDAO.getIdPedido(id_mesa);
        return itemDePedidoDAO.insert(new ItemDePedido(idPedido,idProduto,quantidade,observacao,indentificador,preco)) > 0;
    }

    public Boolean atualizarQuantidadeDoItemPedido(int numeroMesa,Long idProduto,int quantidade,String observacao){
        Long idPedido = pedidoDAO.getIdPedido(numeroMesa);
        ItemDePedido item  = new ItemDePedido();
        item.setObservacao(observacao);
        item = itemDePedidoDAO.getItemDoPedido(idPedido,idProduto);
        item.setQuantidade(quantidade);
        return itemDePedidoDAO.update(item) > 0;
    }

    public Boolean removerProdutoDoPedido(int numeroMesa,Long idProduto){
        Long idPedido = pedidoDAO.getIdPedido(numeroMesa);
        return itemDePedidoDAO.delete(itemDePedidoDAO.getItemDoPedido(idPedido,idProduto)) > 0;
    }


    public void produtosPorCategoriaOnline(APIListener<Dados> listener,Long id){
        Call<Dados> call = service.produtosPorCategoria(id);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                if(response.isSuccessful()){
                    if(response.body().produtos != null){
                        listener.onSuccess(response.body());
                    }
                }else{
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });
    }
    public void produtosOnline(APIListener<Dados> listener){
        Call<Dados> call = service.getProdutos();
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                if(response.isSuccessful()){
                    if(response.body().produtos != null){
                        System.out.println("Produtos: "+response.body().produtos.size());
                        listener.onSuccess(response.body());
                    }
                }else{
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });
    }



    public void salvarProdutoSelecionadoOnline(APIListener<ItemDePedido> listener,Long idPedido,Long idProduto,int quantidade,String observacao,String identificador,Float preco){
        Call<ItemDePedido> call = service.salvarItemDoPedido(idPedido,idProduto,preco,quantidade,observacao,identificador);
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                //QUANDO O ItemDePedido É ENVIADO COM SUCESSO ELE É SINCRONIZADO MARCADO COMO "SIM" O CAMPO SINCRONIZADO
                if(response.isSuccessful()){
                    if(response.body() != null){
                        // recupero o id do ultimo item adicionado no pedido
                        Long idItem = itemDePedidoDAO.getUltimoItemDoPedidoAdicionado(idPedido).getId();
                        atualizarSincronismo(idItem, Constantes.SIM);

                    }

                }else {
                    listener.onFailures("ProdutosRepositorio "+response.message());
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                System.out.println(": "+t.getMessage());
                listener.onFailures("ProdutosRepositorio "+t.getMessage());
            }
        });
    }
}
