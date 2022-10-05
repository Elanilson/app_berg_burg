package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.model.Resposta;

import java.util.ArrayList;
import java.util.List;

public class SelecionarMesaViewModel extends AndroidViewModel {

    private List<Integer> listaDeMesasExemplo = new ArrayList<>();

    private MutableLiveData<List<Integer>> _Mesas = new MutableLiveData<>();
    public LiveData<List<Integer>> mesas = _Mesas;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public SelecionarMesaViewModel(@NonNull Application application) {
        super(application);
    }

    public void listaDeMesas(){
        System.out.println("SelecionarMesaViewModel: "+this.listaDeMesasExemplo.size());

        this.listaDeMesasExemplo.add(1);
        this.listaDeMesasExemplo.add(2);
        this.listaDeMesasExemplo.add(3);
        this.listaDeMesasExemplo.add(4);
        this.listaDeMesasExemplo.add(5);
        this.listaDeMesasExemplo.add(6);
        this.listaDeMesasExemplo.add(7);
        this.listaDeMesasExemplo.add(8);
        this.listaDeMesasExemplo.add(9);
        this.listaDeMesasExemplo.add(10);
        this.listaDeMesasExemplo.add(11);
        this.listaDeMesasExemplo.add(12);
        this.listaDeMesasExemplo.add(13);
        this.listaDeMesasExemplo.add(14);
        this.listaDeMesasExemplo.add(15);
        this.listaDeMesasExemplo.add(16);
        this.listaDeMesasExemplo.add(17);
        this.listaDeMesasExemplo.add(18);
        this.listaDeMesasExemplo.add(19);
        this.listaDeMesasExemplo.add(20);
        this.listaDeMesasExemplo.add(21);
        this.listaDeMesasExemplo.add(22);
        this.listaDeMesasExemplo.add(23);
        this.listaDeMesasExemplo.add(24);
        this.listaDeMesasExemplo.add(25);
        this.listaDeMesasExemplo.add(26);
        this.listaDeMesasExemplo.add(27);
        this.listaDeMesasExemplo.add(28);
        this.listaDeMesasExemplo.add(29);
        this.listaDeMesasExemplo.add(30);
        this.listaDeMesasExemplo.add(31);
        this.listaDeMesasExemplo.add(32);
        this.listaDeMesasExemplo.add(33);
        this.listaDeMesasExemplo.add(34);
        this.listaDeMesasExemplo.add(35);
        this.listaDeMesasExemplo.add(36);
        this.listaDeMesasExemplo.add(37);
        this.listaDeMesasExemplo.add(38);
        this.listaDeMesasExemplo.add(39);
        this.listaDeMesasExemplo.add(40);


        _Mesas.setValue(listaDeMesasExemplo);
    }


}
