package com.bergburg.bergburg.repositorio;

import android.content.Context;
import android.view.View;

import com.bergburg.bergburg.constantes.Constantes;

import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.APIListener;

import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepositorio {
    private Context context;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);


    public PedidoRepositorio(Context context) {
        this.context = context;

    }


    public void criarPedido(APIListener<Dados> listener, Long idMesa, Long idUsuario, float total){
        Call<Dados> call = service.criarPedido(idUsuario,idMesa,total);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }
    public void getTodosOsPedidos(APIListener<Dados> listener){
        Call<Dados> call = service.getTodosOsPedidos();
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }

    public void getPedido(APIListener<Dados> listener, Long idPedido){
        Call<Dados> call = service.getPedido(idPedido);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }

    public void getPedidos(APIListener<Dados> listener, Long idMesa){
        Call<Dados> call = service.getPedidos(idMesa);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }


    public void getPedidosAbertos(APIListener<Dados> listener, Long idMesa){
        Call<Dados> call = service.getPedidosAbertos(idMesa);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }






}
