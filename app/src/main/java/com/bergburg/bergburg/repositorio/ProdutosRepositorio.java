package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;

import java.util.List;

public class ProdutosRepositorio {
    private Context context;
    private ProdutoDAO produtoDAO;

    public ProdutosRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        produtoDAO = db.produtoDAO();
    }

    public Boolean insert(Produto produto){
        return produtoDAO.insert(produto ) > 0;
    }
    public Boolean update(Produto produto){
        return produtoDAO.update(produto) > 0;
    }
    public void deletar(){
        //delete não deve deletar apenas não deixar visivel
    }

    public Produto getProduto(int id){
        return produtoDAO.getProduto(id);
    }
    public List<Produto> produtosPorCategoria(Long idCategoria){
        return produtoDAO.getProdutosPorCategoria(idCategoria);
    }
    public  List<Produto> produtos(){
        return produtoDAO.produtos();
    }
}
