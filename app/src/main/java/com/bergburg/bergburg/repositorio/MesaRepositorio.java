package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;

import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;
import com.bergburg.bergburg.view.activity.SelecionarMesaActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesaRepositorio {
    private BergburgService service = RetrofitClient.classService(BergburgService.class);
    private Context context;


    public MesaRepositorio() {
    }

    public MesaRepositorio(Context context) {
        this.context = context;

    }



    public void carregarMesas(APIListener<Dados> listener){
        Call<Dados> call = service.getMesas();
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {

                listener.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                System.out.println("mesa TEM INTERNET ? "+ VerificadorDeConexao.isConnectionAvailable(context));
                listener.onFailures(t.getMessage());
            }
        });

    }

    public void getMesa(APIListener<Dados> listener,Long id){
        Call<Dados> call = service.getMesa(id);
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

    public void removerItemComanda(APIListener<Dados> listener,Long idItemComanda){
        Call<Dados> call = service.removerItemComanda(idItemComanda);
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

    public void atualizarItemComanda(APIListener<Dados> listener,Long idITemComanda,int quantidade,String observacao){
        Call<Dados> call = service.atualizarItemComanda(idITemComanda,quantidade,observacao);
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

    public void abrirMesa(APIListener<Dados> listener,Long id){
        Call<Dados> call = service.alterarStatusMesa(id);
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

    public void itensComanda(APIListener<Dados> listener,Long idMesa){
        Call<Dados> call = service.itensComanda(idMesa);
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

    public void cancelarComanda(APIListener<Dados> listener,Long idMesa){
        Call<Dados> call = service.cancelarComanda(idMesa);
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


    public void adicionarItemComanda(APIListener<Dados> listener,Long idMesa,Long idProduto,String observacao, int quantidade){
        Call<Dados> call = service.adicionarItemComanda(idProduto,idMesa,quantidade,observacao);
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
