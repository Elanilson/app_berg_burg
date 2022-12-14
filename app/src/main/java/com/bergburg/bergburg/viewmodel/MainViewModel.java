package com.bergburg.bergburg.viewmodel;

import android.app.Application;
import android.os.CpuUsageInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.helpers.UsuarioPreferences;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.UsuarioRepositorio;

public class MainViewModel extends AndroidViewModel {

    private UsuarioRepositorio repositorio;
    private UsuarioPreferences preferences;

    private MutableLiveData<Usuario> _Usuario = new MutableLiveData<>();
    public LiveData<Usuario> usuario = _Usuario;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repositorio = new UsuarioRepositorio(application);
        preferences = new UsuarioPreferences(application.getBaseContext());
    }


    public void deslogar(Long id){
        if(id != null){
            APIListener<Dados> listener = new APIListener<Dados>() {
                @Override
                public void onSuccess(Dados result) {
                    _Usuario.setValue(result.getUsuario());
                    _Resposta.setValue(new Resposta(Constantes.USUARIO_DESLOGADO,true));
                    if(result.getUsuario() != null){
                        preferences.salvarStatus(result.getUsuario().getStatus());
                    }
                }
                @Override
                public void onFailures(String mensagem) {
                    System.out.println("Error: "+mensagem);
                    _Resposta.setValue(new Resposta("Falha ao tentar conectar"));
                }
            };
            repositorio.deslogar(listener,id);
        }else{
            _Resposta.setValue(new Resposta("Usu??rio sem ID"));
        }
    }

    public void verificarUsuarioLogado(Long id){
        if(id != null){
            APIListener<Dados> listener = new APIListener<Dados>() {
                @Override
                public void onSuccess(Dados result) {
                    _Usuario.setValue(result.getUsuario());
                    _Resposta.setValue(new Resposta("Autenticado",true));
                    if(result.getUsuario() != null){
                     System.out.println(result.getUsuario().toString());
                        preferences.salvarStatus(result.getUsuario().getStatus());
                    }
                }
                @Override
                public void onFailures(String mensagem) {
                    System.out.println("Error: "+mensagem);
                    //_Resposta.setValue(new Resposta("Falha ao tentar conectar"));
                }
            };
            repositorio.verificarUsuarioLogado(listener,id);
        }else{
          //  _Resposta.setValue(new Resposta("Usu??rio sem ID"));
        }
    }

    public void login(String nome, String senha){
        if(nome != null && !nome.isEmpty()){
            if(senha != null && !senha.isEmpty()){

                APIListener<Dados> listener = new APIListener<Dados>() {
                    @Override
                    public void onSuccess(Dados result) {

                            if(!result.getError().isEmpty()){
                                _Resposta.setValue(new Resposta(result.getError()));
                            }else{
                                _Usuario.setValue(result.getUsuario());
                                _Resposta.setValue(new Resposta("Autenticado",true));
                                preferences.salvar(result.getUsuario().getId(),result.getUsuario().getNome(),result.getUsuario().getStatus() );

                            }


                    }
                    @Override
                    public void onFailures(String mensagem) {
                        System.out.println("Error: "+mensagem);
                        _Resposta.setValue(new Resposta("Falha ao tentar conectar"));
                    }
                };
                repositorio.login(listener,nome,senha);

            }else{
                _Resposta.setValue(new Resposta("Usu??rio ou senha incorretos!"));
            }

        }else{
            _Resposta.setValue(new Resposta("Usu??rio ou senha incorretos!"));
        }


    }



}
