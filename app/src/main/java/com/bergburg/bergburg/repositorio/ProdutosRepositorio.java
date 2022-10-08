package com.bergburg.bergburg.repositorio;

import android.content.Context;
import android.widget.Toast;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosRepositorio {
    private Context context;
    private ProdutoDAO produtoDAO;
    private ItemDePedidoDAO itemDePedidoDAO;
    private PedidoDAO pedidoDAO;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);
    private ItemDePedido item = new ItemDePedido();

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

    public Boolean atualizarSincronismoItemDoPedido(ItemDePedido itemDePedido){
        return itemDePedidoDAO.update(itemDePedido) > 0;
    }

    public Boolean salvarProdutoSelecionado(int id_mesa,Long idProduto,int quantidade,String observacao,String indentificador,Float preco,String status){
        Long idPedido = pedidoDAO.getIdPedido(id_mesa);
        return itemDePedidoDAO.insert(new ItemDePedido(idPedido,idProduto,quantidade,observacao,indentificador,preco,status)) > 0;
    }

    public Boolean atualizarQuantidadeDoItemPedido(int id_mesa,Long idProduto,int quantidade,String observacao){
        Long idPedido = pedidoDAO.getIdPedido(id_mesa);
        ItemDePedido item  = new ItemDePedido();
        item = itemDePedidoDAO.getItemDoPedido(idPedido,idProduto);
        item.setObservacao(observacao);// mudei de posicao
        item.setQuantidade(quantidade);
        return itemDePedidoDAO.update(item) > 0;
    }

    public Boolean removerProdutoDoPedido(int numeroMesa,Long idProduto){
        Long idPedido = pedidoDAO.getIdPedido(numeroMesa);
      //  return itemDePedidoDAO.delete(itemDePedidoDAO.getItemDoPedido(idPedido,idProduto)) > 0;
        ItemDePedido item = new ItemDePedido();
        item = itemDePedidoDAO.getItemDoPedido(idPedido,idProduto);
        item.setStatus(Constantes.REMOVIDO);
        item.setSincronizado(Constantes.NAO);
        return itemDePedidoDAO.update(item) > 0;
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


    public void atualizarQuantidadeDoProdutoSelecionadoOnline(APIListener<ItemDePedido> listener ,Long idProduto,String indentificador,int quantidade){

        item = itemDePedidoDAO.getIdDoItem(indentificador);

        Call<ItemDePedido> call = service.atualizarQuantidadeitemPedido(indentificador,quantidade);
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                if(response.isSuccessful()){
                    //deu tudo certo beleza
                    item.setSincronizado(Constantes.SIM);
                  //  atualizarSincronismoItemDoPedido(item );
                    itemDePedidoDAO.update(item);

                }else{
                    item.setSincronizado(Constantes.NAO);
                   // atualizarSincronismoItemDoPedido(item);
                    itemDePedidoDAO.update(item);
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                item.setSincronizado(Constantes.NAO);
              //  atualizarSincronismoItemDoPedido(item);
                itemDePedidoDAO.update(item);
                listener.onFailures(t.getMessage());
            }
        });


    }

    public void autlizarItemDoPedidoOnline(APIListener<ItemDePedido> listener,ItemDePedido itemDePedido){
        Call<ItemDePedido> call = service.atualizarItemDoPedido(itemDePedido.getIndentificadorUnico(),itemDePedido.getStatus(), itemDePedido.getQuantidade(),itemDePedido.getPreco(),itemDePedido.getObservacao());
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                if(response.isSuccessful()){
                    item.setSincronizado(Constantes.SIM);
                    //  atualizarSincronismoItemDoPedido(itemDePedido);
                    itemDePedidoDAO.update(itemDePedido);
                }else{
                    item.setSincronizado(Constantes.NAO);
                    // atualizarSincronismoItemDoPedido(itemDePedido);
                    itemDePedidoDAO.update(itemDePedido);
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                item.setSincronizado(Constantes.NAO);
                // atualizarSincronismoItemDoPedido(itemDePedido);
                itemDePedidoDAO.update(itemDePedido);
                listener.onFailures(t.getMessage());
            }
        });

    }

    public void autlizarItemDoPedidoOnline(APIListener<ItemDePedido> listener,Long idProduto,String indentificador,int quantidade){
        item = itemDePedidoDAO.getIdDoItem(indentificador);
        item.setQuantidade(quantidade);
        Call<ItemDePedido> call = service.atualizarItemDoPedido(item.getIndentificadorUnico(),item.getStatus(), item.getQuantidade(),item.getPreco(),item.getObservacao());
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                if(response.isSuccessful()){
                    item.setSincronizado(Constantes.SIM);
                    //  atualizarSincronismoItemDoPedido(itemDePedido);
                    itemDePedidoDAO.update(item);
                }else{
                    item.setSincronizado(Constantes.NAO);
                    // atualizarSincronismoItemDoPedido(itemDePedido);
                    itemDePedidoDAO.update(item);
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                item.setSincronizado(Constantes.NAO);
                // atualizarSincronismoItemDoPedido(itemDePedido);
                itemDePedidoDAO.update(item);
                listener.onFailures(t.getMessage());
            }
        });

    }

    public void salvarProdutoSelecionadoOnline(APIListener<ItemDePedido> listener,Long idPedido,Long idProduto,int quantidade,String observacao,String identificador,Float preco,String status){
        Call<ItemDePedido> call = service.salvarItemDoPedido(idPedido,idProduto,preco,quantidade,observacao,status,identificador);
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                //QUANDO O ItemDePedido É ENVIADO COM SUCESSO ELE É SINCRONIZADO MARCADO COMO "SIM" O CAMPO SINCRONIZADO
                if(response.isSuccessful()){
                    if(response.body() != null){
                            // recupero o id do ultimo item adicionado no pedido
                       try{
                           //vai da erro em algum lugar
                           ItemDePedido item = new ItemDePedido();
                           item = itemDePedidoDAO.getIdDoItem(identificador);
                           item.setSincronizado(Constantes.SIM);
                         //  atualizarSincronismoItemDoPedido(item);
                           itemDePedidoDAO.update(item);
                      }catch (Exception e){
                           e.printStackTrace();
                           listener.onFailures(e.getMessage());


                       }



                    }

                }else {
                    System.out.println(response.message());
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                System.out.println(": "+t.getMessage());
                listener.onFailures(t.getMessage());
            }
        });
    }
}
