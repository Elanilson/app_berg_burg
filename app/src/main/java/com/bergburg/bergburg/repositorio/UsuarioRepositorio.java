package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.interfaces.UsuarioDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;

public class UsuarioRepositorio {
    private Context context;
    private UsuarioDAO usuarioDAO;

    public UsuarioRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        usuarioDAO = db.usuarioDAO();
    }

    public Boolean insert(Usuario usuario){
        return usuarioDAO.insert(usuario ) > 0;
    }
    public Boolean update(Usuario usuario){
        return usuarioDAO.update(usuario) > 0;
    }
    public void deletar(){
        //delete não deve deletar apenas não deixar visivel
    }

    public Usuario getUsuario(Long id){
        return usuarioDAO.getUsuaurio(id);
    }

    public Usuario getUsuarioLogado( ){
        return usuarioDAO.getUsuarioLogado();
    }

    public Usuario login(String usuario, String senha){
        return usuarioDAO.login(usuario,senha);
    }
}
