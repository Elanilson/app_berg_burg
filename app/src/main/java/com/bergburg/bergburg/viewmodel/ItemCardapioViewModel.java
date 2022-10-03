package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.CategoriasRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;

import java.util.List;

public class ItemCardapioViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;

    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    public ItemCardapioViewModel(@NonNull Application application) {
        super(application);

       repositorio = new ProdutosRepositorio(application.getApplicationContext());
    }

    public  void produtosPorCategoria(Long idCategoria){
        _Produtos.setValue(repositorio.produtosPorCategoria(idCategoria));
    }

}
