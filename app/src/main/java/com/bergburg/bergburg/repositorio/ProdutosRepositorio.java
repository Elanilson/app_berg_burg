package com.bergburg.bergburg.repositorio;

import android.content.Context;
import android.widget.Toast;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;

import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosRepositorio {
    private Context context;
    private BergburgService service = RetrofitClient.classService(BergburgService.class);
    public ProdutosRepositorio(Context context) {
        this.context = context;

    }


    public void produtosPorCategoria(APIListener<Dados> listener, Long idCategoria){
        Call<Dados> call = service.produtosPorCategoria(idCategoria);
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
