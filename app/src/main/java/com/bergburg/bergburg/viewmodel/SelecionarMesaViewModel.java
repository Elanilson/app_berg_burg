package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.ItemDoPedidoRepositorio;
import com.bergburg.bergburg.repositorio.MesaRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class SelecionarMesaViewModel extends AndroidViewModel {
    private MesaRepositorio repositorio ;
    private PedidoRepositorio pedidoRepositorio;
    private ItemDoPedidoRepositorio itemDoPedidoRepositorio;

    private List<Mesa> listaDeMesasExemplo = new ArrayList<>();

    private MutableLiveData<List<Pedido>> _Pedidos = new MutableLiveData<>();
    public LiveData<List<Pedido>> pedidos = _Pedidos;

    private MutableLiveData<List<ItemDePedido>> _ItensDosPedidos = new MutableLiveData<>();
    public LiveData<List<ItemDePedido>> itensDosPedidos = _ItensDosPedidos;

    private MutableLiveData<List<Mesa>> _Mesas = new MutableLiveData<>();
    public LiveData<List<Mesa>> mesas = _Mesas;

    private MutableLiveData<List<Mesa>> _MesasOnline = new MutableLiveData<>();
    public LiveData<List<Mesa>> mesasOnline = _MesasOnline;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public SelecionarMesaViewModel(@NonNull Application application) {
        super(application);
        repositorio = new MesaRepositorio(application.getBaseContext());
        pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
        itemDoPedidoRepositorio = new ItemDoPedidoRepositorio(application.getApplicationContext());
    }

    public void listaDeMesas(){
       // System.out.println("SelecionarMesaViewModel: "+this.listaDeMesasExemplo.size());
        _Mesas.setValue(repositorio.mesas());
    }
    public void listarMesasOnline(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                System.out.println("SelecionarMesaViewModel total: "+result.mesas.size());
                _MesasOnline.setValue(result.mesas);

            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("SelecionarMesaViewModel "+mensagem);

                _Resposta.setValue(new Resposta(mensagem));

            }
        };

       // repositorio.getMesas(listener);
    }


    public void carregarPedidos(){
        _Pedidos.setValue(pedidoRepositorio.todosOsPedidos());
    }

    public void sincronizarPedidoOnline(List<Pedido> pedidos){
        for(Pedido pedido : pedidos){
            if(pedido.getSincronizado().equalsIgnoreCase(Constantes.NAO)){
                if(pedido.getStatus().equalsIgnoreCase(Constantes.CANCELADO)){
                    APIListener<Pedido> listene = new APIListener<Pedido>() {
                        @Override
                        public void onSuccess(Pedido result) {

                        }

                        @Override
                        public void onFailures(String mensagem) {
                            _Resposta.setValue(new Resposta(mensagem));
                        }
                    };
                    pedidoRepositorio.salvarPedidoOnline(listene,pedido.getIdUsuario(),pedido.getIdMesa(), pedido.getAberturaPedido(), pedido.getStatus(), pedido.getTotal(), pedido.getIdentificadorUnico());

                }else{

                APIListener<Pedido> listene = new APIListener<Pedido>() {
                    @Override
                    public void onSuccess(Pedido result) {

                    }

                    @Override
                    public void onFailures(String mensagem) {
                        _Resposta.setValue(new Resposta(mensagem));
                    }
                };
                    pedidoRepositorio.salvarPedidoOnline(listene,pedido.getIdUsuario(),pedido.getIdMesa(), pedido.getAberturaPedido(), pedido.getStatus(), pedido.getTotal(), pedido.getIdentificadorUnico());
             //   pedidoRepositorio.atualizarPedidoOnline(listene,pedido.getId(),pedido.getIdentificadorUnico(),pedido.getTotal(),pedido.getStatus(),pedido.getAberturaPedido());
                }

            }
        }
    }

    public void carregarItensDosPedidos(){
        _ItensDosPedidos.setValue(itemDoPedidoRepositorio.itensDosPedidosNaoSincronizados());
    }
    public void sincronizarItensDosPedidos(List<ItemDePedido> itens){
        for (ItemDePedido item : itens){
            APIListener<ItemDePedido> listene = new APIListener<ItemDePedido>() {
                @Override
                public void onSuccess(ItemDePedido result) {

                }

                @Override
                public void onFailures(String mensagem) {
                    _Resposta.setValue(new Resposta(mensagem));
                }
            };
            itemDoPedidoRepositorio.salvar_OU_Atualizar_ItemDoPedidoOnline(listene,item);
        }
    }


}
