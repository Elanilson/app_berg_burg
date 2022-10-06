package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.repositorio.interfaces.MesaDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesaRepositorio {
    private BergburgService service = RetrofitClient.classService(BergburgService.class);
    private MesaDAO mesaDAO;
    private Context context;

    public MesaRepositorio() {
    }

    public MesaRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        mesaDAO = db.mesaDAO();
    }

    public Boolean insert(Mesa mesa){
        return mesaDAO.insert(mesa) > 0;
    }

    public Boolean update(Mesa mesa){
        return mesaDAO.update(mesa) > 0;
    }
    public Boolean delete(Mesa mesa){
        return mesaDAO.delete(mesa) > 0;
    }

    public List<Mesa> mesas(){
        return mesaDAO.mesas();
    }

    public  void getMesas(APIListener<Dados> listener){

        Call<Dados>  call =  service.getMesas();
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                if(response.code() == 200){
                    if(response.body() != null){
                        listener.onSuccess(response.body());
                    }
                }else{
                    listener.onFailures("Erro na requisição");
                }
            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage());
            }
        });

    }
}
