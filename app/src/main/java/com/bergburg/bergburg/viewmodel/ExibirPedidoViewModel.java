package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.ItemDoPedidoRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;

import java.util.List;

public class ExibirPedidoViewModel extends AndroidViewModel {
    private ItemDoPedidoRepositorio repositorio;
    private PedidoRepositorio pedidoRepositorio;

    private MutableLiveData<List<ItemDePedido>> _ItensDoPedido = new MutableLiveData<>();
    public LiveData<List<ItemDePedido>> itensDoPedido = _ItensDoPedido;

    private MutableLiveData<Pedido> _Pedido = new MutableLiveData<>();
    public LiveData<Pedido> pedido = _Pedido;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public ExibirPedidoViewModel(@NonNull Application application) {
        super(application);
        repositorio = new ItemDoPedidoRepositorio(application.getApplicationContext());
        pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
    }

    public void getItensPedido(Long id){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
               if(result.getError().isEmpty()){
                   _ItensDoPedido.setValue(result.getItensPedido());
               }else{
                   _Resposta.setValue(new Resposta(result.getError()));
               }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao conectar"));
            }
        };

        repositorio.getItensPedido(listener,id);
    }

    public void getPedido(Long id){
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

        pedidoRepositorio.getPedido(listener,id);
    }


}
