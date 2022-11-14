package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.view.adapter.PedidoAdapter;

import java.util.List;

public class ConsultaPedidoViewModel extends AndroidViewModel {
    private PedidoRepositorio  repositorio;


    private MutableLiveData<List<Pedido>> _Pedidos = new MutableLiveData<>();
    public LiveData<List<Pedido>> pedidos = _Pedidos;


    private MutableLiveData<Pedido> _Pedido = new MutableLiveData<>();
    public LiveData<Pedido> pedido = _Pedido;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public ConsultaPedidoViewModel(@NonNull Application application) {
        super(application);
        repositorio = new PedidoRepositorio(application.getBaseContext());
    }



    public void getTodosOsPedidos(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                   _Pedidos.setValue(result.getPedidos());

            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };
        repositorio.getTodosOsPedidos(listener);
    }

    public void getPedido(Long idPedido){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Pedido.setValue(result.getPedido());

            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };
        repositorio.getPedido(listener,idPedido);
    }

    public void getPedidos(Long idMesa){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
               _Pedidos.setValue(result.getPedidos());

            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };
        repositorio.getPedidos(listener,idMesa);
    }


}
