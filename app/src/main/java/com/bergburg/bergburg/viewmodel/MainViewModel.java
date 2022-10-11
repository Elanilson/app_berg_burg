package com.bergburg.bergburg.viewmodel;

import android.app.Application;
import android.os.CpuUsageInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.UsuarioRepositorio;

public class MainViewModel extends AndroidViewModel {

    private UsuarioRepositorio repositorio;

    private MutableLiveData<Usuario> _Usuario = new MutableLiveData<>();
    public LiveData<Usuario> usuario = _Usuario;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repositorio = new UsuarioRepositorio(application);
    }

    public void verificarUsuarioLogado(){
        Usuario  usuario = repositorio.getUsuarioLogado();
        if(usuario != null){
            _Usuario.setValue(usuario);
            _Resposta.setValue(new Resposta(true));
        }else{
         //   _Resposta.setValue(new Resposta("não Logado"));
        }
    }

    public void login(String nome, String senha){
        Usuario usuario = repositorio.login(nome,senha);
        if( usuario != null){
            usuario.setStatus(Constantes.USUARIO_LOGADO);
            repositorio.update(usuario);
            _Resposta.setValue(new Resposta("Autenticado",true));
        }else{
            _Resposta.setValue(new Resposta("Usuário ou senha incorretos!"));

        }

    }



    public void deslogar(){
        Usuario  usuario = repositorio.getUsuarioLogado();
        if(usuario != null){
            usuario.setStatus(Constantes.USUARIO_DESLOGADO);
            if(repositorio.update(usuario)){
                _Usuario.setValue(usuario);
                _Resposta.setValue(new Resposta("Deslogado",true));
            }else{
                _Resposta.setValue(new Resposta("Erro ao deslogar",true));

            }
        }

    }
}
