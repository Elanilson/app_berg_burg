package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDoPedidoRepositorio {
    private Context context;
    private ItemDePedidoDAO itemDePedidoDAO;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    public ItemDoPedidoRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        itemDePedidoDAO  = db.itemDePedidoDAO();

    }

    public Boolean salvarItemDoPedido(ItemDePedido item){
        return itemDePedidoDAO.insert(item ) > 0;
    }
    public Boolean atualizarItemDoPedido(ItemDePedido item){
        item.setSincronizado(Constantes.NAO);
        return itemDePedidoDAO.update(item) > 0;
    }
    public Boolean removerItemDoPedido(ItemDePedido item){
        item.setStatus(Constantes.REMOVIDO);
        item.setSincronizado(Constantes.NAO);
        return itemDePedidoDAO.update(item) > 0;
    }

    public List<ItemDePedido> itensDosPedidosNaoSincronizados(){
        return itemDePedidoDAO.itensDoPedidosNaoSincronizados();
    }
    public void deletar(){
        //delete não deve deletar apenas não deixar visivel
    }

    public void salvar_OU_Atualizar_ItemDoPedidoOnline(APIListener<ItemDePedido> listener,ItemDePedido item){
        Call<ItemDePedido> call = service.salvarItemDoPedido(item.getIdPedido(),item.getIdProduto(),item.getPreco(),item.getQuantidade(),item.getObservacao(),item.getStatus(),item.getIndentificadorUnico());
        call.enqueue(new Callback<ItemDePedido>() {
            @Override
            public void onResponse(Call<ItemDePedido> call, Response<ItemDePedido> response) {
                //QUANDO O ItemDePedido É ENVIADO COM SUCESSO ELE É SINCRONIZADO MARCADO COMO "SIM" O CAMPO SINCRONIZADO
                if(response.isSuccessful()){
                    if(response.body() != null){
                            item.setSincronizado(Constantes.SIM);
                            itemDePedidoDAO.update(item);
                    }

                }else {
                    item.setSincronizado(Constantes.NAO);
                    itemDePedidoDAO.update(item);
                    System.out.println(response.message());
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDePedido> call, Throwable t) {
                item.setSincronizado(Constantes.NAO);
                itemDePedidoDAO.update(item);
                System.out.println(": "+t.getMessage());
                listener.onFailures(t.getMessage());
            }
        });
    }


}
