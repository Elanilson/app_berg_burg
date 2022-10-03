package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class SelecionarMesaViewModel extends AndroidViewModel {

    private List<Integer> listaDeMesasExemplo = new ArrayList<>();

    private MutableLiveData<List<Integer>> _Mesas = new MutableLiveData<>();
    public LiveData<List<Integer>> mesas = _Mesas;

    public SelecionarMesaViewModel(@NonNull Application application) {
        super(application);
    }

    public void listaDeMesas(){
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

        _Mesas.setValue(listaDeMesasExemplo);
    }


}
