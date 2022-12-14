package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.CategoriasRepositorio;

import java.util.List;

public class CardapioViewModel extends AndroidViewModel {
    private CategoriasRepositorio  repositorio;

    private MutableLiveData<List<Categoria>> _Categorias = new MutableLiveData<>();
    public LiveData<List<Categoria>> categorias = _Categorias;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public CardapioViewModel(@NonNull Application application) {
        super(application);

       repositorio = new CategoriasRepositorio(application.getApplicationContext());
    }



    public void getCategorias(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Categorias.setValue(result.getCategorias());

            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };
        repositorio.getCategoria(listener);
    }



}
