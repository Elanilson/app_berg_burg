package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepositorio {
    private PedidoDAO pedidoDAO;
    private ItemDePedidoDAO itemDePedidoDAO;
    private Context context;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);


    public PedidoRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        pedidoDAO = db.pedidoDAO();
        itemDePedidoDAO = db.itemDePedidoDAO();

    }

    public Boolean insert(Pedido pedido){
        return pedidoDAO.insert(pedido) > 0;
    }

    public Boolean update(Pedido pedido){
        return pedidoDAO.update(pedido) > 0;
    }
    public Boolean delete(Pedido pedido){
        return pedidoDAO.delete(pedido) > 0;
    }
    public Boolean atualizarSincronismo(Long id, String sincronizado){
        return pedidoDAO.atualizarSincronismo(id,sincronizado) > 0;
    }

    public Pedido getPedido(int idMesa){
        return pedidoDAO.getPedido(idMesa);
    }
    public Pedido getQualquerPedido(String identificadorUnico){
        return pedidoDAO.getQualQuerPedido(identificadorUnico);
    }


    public List<ItemDePedido> getItensDoPedido(Long idPedido){
        return itemDePedidoDAO.getItensDoPedido(idPedido);
    }


    public List<Pedido> getPedidos(){
        return pedidoDAO.pedidos();
    }

    // fechado , aberto, cancelado.  Tudo apra ser sincronizado
    public List<Pedido> todosOsPedidos(){
        return pedidoDAO.getTodosOsPedidos();
    }

    public List<Pedido> consultaPedido(int numeroMesa){
        return pedidoDAO.consultarPedido(numeroMesa);
    }


    public void salvarPedidoOnline(APIListener<Pedido> listener,Long idUsuario,int idMesa,String aberturaPedido,String status,Float total, String indentificadorUnico ){
        Call<Pedido> call = service.salvarPedido(idUsuario,idMesa,aberturaPedido,status,total,indentificadorUnico);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                     //   System.out.println("Pedido enviado com sucesso: "+response.body());
                       // listener.onSuccess(response.body());
                        //QUANDO O PEIDO É ENVIADO COM SUCESSO ELE É SINCRONIZADO MARCADO COMO "SIM" O CAMPO SINCRONIZADO
                        //CASO CONTRARIO ELE VAI TENTAR ATER O ENVIO SER CONFIRMADO STATUS 200 HTTP
                        Long id = getQualquerPedido(indentificadorUnico).getId(); // trás qualquer pedido pelo id da mesa
                        atualizarSincronismo(id,Constantes.SIM);
                    }
                }else{
                    listener.onFailures(response.message());
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });
    }

    public void atualizarPedidoOnline(APIListener<Pedido> listener,Long idPedido,String indentificador,Float total,String status,String aberturaPedido){
        Call<Pedido> call = service.atualizarPedido(indentificador,total,status,aberturaPedido);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){

                        atualizarSincronismo(idPedido,Constantes.SIM);
                        listener.onFailures(response.message());
                        System.out.println("atualizarPedidoOnline:indentificador "+indentificador);
                        System.out.println("atualizarPedidoOnline:idPedido "+idPedido);
                        System.out.println("atualizarPedidoOnline:total "+total);
                        System.out.println("atualizarPedidoOnline:status "+status);
                        System.out.println("atualizarPedidoOnline:aberturaPedido "+aberturaPedido);
                    }
                }else{
                    System.out.println("atualizarPedidoOnline:indentificador "+indentificador);
                    System.out.println("atualizarPedidoOnline:idPedido "+idPedido);
                    System.out.println("atualizarPedidoOnline:total "+total);
                    System.out.println("atualizarPedidoOnline:status "+status);
                    System.out.println("atualizarPedidoOnline:aberturaPedido "+aberturaPedido);
                    atualizarSincronismo(idPedido,Constantes.NAO);
                    listener.onFailures("erro "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                atualizarSincronismo(idPedido,Constantes.NAO);
                listener.onFailures(t.getMessage());
            }
        });




    }


}
