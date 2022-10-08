package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;

import java.util.List;

@Dao
public interface ItemDePedidoDAO {
    @Insert
    public Long insert(ItemDePedido itemDePedido);
    @Update
    public int update(ItemDePedido itemDePedido);
    @Delete
    public int delete(ItemDePedido itemDePedido);

    @Query("select * from itemDePedido where idPedido = :id ")
    public List<ItemDePedido> getItensDoPedido (Long id);


    @Query("select * from itemDePedido where idProduto = :idProduto and idPedido = :idPedido ")
    public ItemDePedido getItemDoPedido (Long idPedido,Long idProduto);

    @Query("select * from itemDePedido where indentificadorUnico = :indentificadorUnico  ")
    public ItemDePedido getIdDoItem (String indentificadorUnico);


// and status != 'REMOVIDO'




}
