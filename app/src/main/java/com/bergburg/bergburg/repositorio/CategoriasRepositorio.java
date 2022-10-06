package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.CategoriaDAO;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasRepositorio {
    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    private CategoriaDAO categoriaDAO;

    public CategoriasRepositorio(Context context) {
        BancoRoom db = BancoRoom.getInstance(context);
        categoriaDAO = db.categoriaDAO();
    }

    public Boolean insert(Categoria categoria){
        return categoriaDAO.insert(categoria) > 0;
    }
    public Boolean update (Categoria categoria){
        return categoriaDAO.update(categoria) > 0;
    }
    public void delete(){
        //delete não deve deletar apenas não deixar visivel
    }


    public Categoria getCategoria(int id){
        return categoriaDAO.getCategoria(id);
    }

    public List<Categoria> categorias(){
        return categoriaDAO.categorias();
    }

    public void getcategoriasOnline(APIListener<Dados> listener){
        Call<Dados> call = service.getCategorias();
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                if(response.code() == 200){

                    if(response.body() != null){
                        listener.onSuccess(response.body());
                    }

                }else{

                    listener.onFailures("Erro na requisição");

                }

            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });
    }
}
