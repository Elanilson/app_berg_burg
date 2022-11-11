package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;

import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDoPedidoRepositorio {
    private Context context;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    public ItemDoPedidoRepositorio(Context context) {
        this.context = context;


    }


    public void getItensPedido(APIListener<Dados> listener,Long idPedido){
        Call<Dados> call = service.getItensPedido(idPedido);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

               if(response.isSuccessful()){
                   listener.onSuccess(response.body());
               }else{
                   listener.onFailures("Falha ao tentar conectar");
               }

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }


}
