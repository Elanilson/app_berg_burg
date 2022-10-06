package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;

import java.util.List;

@Dao
public interface PedidoDAO {
    @Insert
    public Long insert(Pedido pedido);
    @Update
    public int update(Pedido pedido);
    @Delete
    public int delete(Pedido pedido);

    @Query("select * from pedidos where numeroMesa = :numeroMesa and  aberturaPedido = 'Aberto' and status != 'Cancelado' ")
    public Pedido getPedido (int numeroMesa);

    @Query("select * from pedidos where numeroMesa like  '%' || :numeroMesa || '%'and  aberturaPedido = 'Aberto' and status != 'Cancelado'")
    public List<Pedido> consultarPedido (int numeroMesa);


    @Query("select id from pedidos where numeroMesa = :numeroMesa and aberturaPedido = 'Aberto' and status != 'Cancelado'")
    public Long getIdPedido(int numeroMesa);



    @Query("select * from pedidos where aberturaPedido = 'Aberto' and status != 'Cancelado' ")
    public List<Pedido> pedidos();

}
