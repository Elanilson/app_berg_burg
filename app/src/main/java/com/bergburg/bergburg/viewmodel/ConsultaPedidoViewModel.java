package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.view.adapter.PedidoAdapter;

import java.util.List;

public class ConsultaPedidoViewModel extends AndroidViewModel {
    private PedidoRepositorio  repositorio;


    private MutableLiveData<List<Pedido>> _Pedidos = new MutableLiveData<>();
    public LiveData<List<Pedido>> pedidos = _Pedidos;

    public ConsultaPedidoViewModel(@NonNull Application application) {
        super(application);
        repositorio = new PedidoRepositorio(application.getBaseContext());
    }

    public void consultarPedido(int numeroDaMesa){
        _Pedidos.setValue(repositorio.consultaPedido(numeroDaMesa));
    }

    public void getPedidos(){
        _Pedidos.setValue(repositorio.getPedidos());
    }
}
