package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;

import java.util.List;

public class ProdutosRepositorio {
    private Context context;
    private ProdutoDAO produtoDAO;
    private ItemDePedidoDAO itemDePedidoDAO;
    private PedidoDAO pedidoDAO;

    public ProdutosRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        produtoDAO = db.produtoDAO();
        itemDePedidoDAO = db.itemDePedidoDAO();
        pedidoDAO = db.pedidoDAO();
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


    public Produto getProduto(Long id){
        return produtoDAO.getProduto(id);
    }

    public List<Produto> produtosPorCategoria(Long idCategoria){
        return produtoDAO.getProdutosPorCategoria(idCategoria);
    }
    public  List<Produto> produtos(){
        return produtoDAO.produtos();
    }

    public Boolean salvarProdutoSelecionado(int numeroMesa,Long idProduto,int quantidade){
        Long idPedido = pedidoDAO.getIdPedido(numeroMesa);
        return itemDePedidoDAO.insert(new ItemDePedido(idPedido,idProduto,quantidade)) > 0;
    }
}
