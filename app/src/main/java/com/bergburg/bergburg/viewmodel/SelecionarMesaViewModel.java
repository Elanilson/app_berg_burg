package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.repositorio.MesaRepositorio;

import java.util.ArrayList;
import java.util.List;

public class SelecionarMesaViewModel extends AndroidViewModel {
    private MesaRepositorio repositorio ;

    private List<Mesa> listaDeMesasExemplo = new ArrayList<>();

    private MutableLiveData<List<Mesa>> _Mesas = new MutableLiveData<>();
    public LiveData<List<Mesa>> mesas = _Mesas;

    private MutableLiveData<List<Mesa>> _MesasOnline = new MutableLiveData<>();
    public LiveData<List<Mesa>> mesasOnline = _MesasOnline;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public SelecionarMesaViewModel(@NonNull Application application) {
        super(application);
        repositorio = new MesaRepositorio(application.getBaseContext());
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

        repositorio.getMesas(listener);
    }


}
