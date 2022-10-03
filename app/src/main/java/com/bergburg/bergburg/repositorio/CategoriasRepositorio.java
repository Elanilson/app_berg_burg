package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.repositorio.banco.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.CategoriaDAO;

import java.util.List;

public class CategoriasRepositorio {
    private Context context;
    private CategoriaDAO categoriaDAO;

    public CategoriasRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        categoriaDAO = db.categoriaDAO();
    }

    public Boolean insert(Categoria categoria){
        return categoriaDAO.insert(categoria) > 0;
    }
    public Boolean update (Categoria categoria){
        return categoriaDAO.update(categoria) > 0;
    }
    public void delete(){
        //delete não deve deletar apenas não deixar visivel
    }


    public Categoria getCategoria(int id){
        return categoriaDAO.getCategoria(id);
    }
    public List<Categoria> categorias(){
        return categoriaDAO.categorias();
    }
}
