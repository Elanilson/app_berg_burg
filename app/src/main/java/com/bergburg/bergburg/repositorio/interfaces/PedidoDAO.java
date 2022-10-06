package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("select * from pedidos where numeroMesa = :numeroMesa and  aberto = 1")
    public Pedido getPedido (int numeroMesa);

    @Query("select * from pedidos where numeroMesa like  '%' || :numeroMesa || '%'and  aberto = 1")
    public List<Pedido> consultarPedido (int numeroMesa);


    @Query("select id from pedidos where numeroMesa = :numeroMesa and aberto = 1")
    public Long getIdPedido(int numeroMesa);



    @Query("select * from pedidos")
    public List<Pedido> pedidos();

}
