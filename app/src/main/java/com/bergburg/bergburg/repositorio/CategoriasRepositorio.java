package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;

import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasRepositorio {
    private BergburgService service = RetrofitClient.classService(BergburgService.class);



    public CategoriasRepositorio(Context context) {
    }



    public void getCategoria(APIListener<Dados> listener){
        Call<Dados> call = service.getCategorias();
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
