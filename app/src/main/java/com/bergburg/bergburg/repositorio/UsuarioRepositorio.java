package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;

import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepositorio {
    private Context context;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    public UsuarioRepositorio(Context context) {
        this.context = context;

    }


    public void deslogar(APIListener<Dados> listener, Long id){
        Call<Dados> call = service.deslogar(id);
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


    public void verificarUsuarioLogado(APIListener<Dados> listener, Long id){
        Call<Dados> call = service.verificarUsuarioLogado(id);
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



    public void login(APIListener<Dados> listener, String nome, String senha){
        Call<Dados> call = service.login(nome,senha);
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
