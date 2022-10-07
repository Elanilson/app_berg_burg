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

    @Query("select * from pedidos where idMesa = :idMesa and  aberturaPedido = 'Aberto' and status != 'Cancelado' ")
    public Pedido getPedido (int idMesa);

    @Query("select * from pedidos where idMesa like  '%' || :idMesa || '%'and  aberturaPedido = 'Aberto' and status != 'Cancelado'")
    public List<Pedido> consultarPedido (int idMesa);


    @Query("select id from pedidos where idMesa = :idMesa and aberturaPedido = 'Aberto' and status != 'Cancelado'")
    public Long getIdPedido(int idMesa);



    @Query("select * from pedidos where aberturaPedido = 'Aberto' and status != 'Cancelado' ")
    public List<Pedido> pedidos();

    @Query("UPDATE pedidos set sincronizado  = :sincronizado where id = :id")
    public int atualizarSincronismo(Long id,String sincronizado);

}
