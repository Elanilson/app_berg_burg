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
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelecionarMesaViewModel extends AndroidViewModel {
    private MesaRepositorio repositorio ;
    private PedidoRepositorio pedidoRepositorio;
    private ItemDoPedidoRepositorio itemDoPedidoRepositorio;

    private BergburgService service = RetrofitClient.classService(BergburgService.class);

    private List<Mesa> mesasTemporarias = new ArrayList<>();

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



}
