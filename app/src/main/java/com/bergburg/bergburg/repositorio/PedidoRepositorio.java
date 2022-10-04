package com.bergburg.bergburg.repositorio;

import android.content.Context;

import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.local.BancoRoom;

import java.util.List;

public class PedidoRepositorio {
    private PedidoDAO pedidoDAO;
    private ItemDePedidoDAO itemDePedidoDAO;
    private Context context;

    public PedidoRepositorio(Context context) {
        this.context = context;
        BancoRoom db = BancoRoom.getInstance(context);
        pedidoDAO = db.pedidoDAO();
        itemDePedidoDAO = db.itemDePedidoDAO();
    }

    public Boolean insert(Pedido pedido){
        return pedidoDAO.insert(pedido) > 0;
    }
    public Boolean update(Pedido pedido){
        return pedidoDAO.update(pedido) > 0;
    }
    public Boolean delete(Pedido pedido){
        return pedidoDAO.delete(pedido) > 0;
    }

    public Pedido getPedido(int numeroMesa){
        return pedidoDAO.getPedido(numeroMesa);
    }


    public List<ItemDePedido> getItensDoPedido(Long id){
        return itemDePedidoDAO.getItensDoPedido(id);
    }
}
