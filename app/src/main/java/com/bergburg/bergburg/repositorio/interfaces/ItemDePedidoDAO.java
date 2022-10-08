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

    @Query("select * from itemDePedido where idPedido = :id")
    public List<ItemDePedido> getItensDoPedido (Long id);


    @Query("select * from itemDePedido where idProduto = :idProduto and idPedido = :idPedido")
    public ItemDePedido getItemDoPedido (Long idPedido,Long idProduto);

    @Query("select * from itemDePedido where idPedido = :idPedido and id order by id desc limit 1")
    public ItemDePedido getUltimoItemDoPedidoAdicionado (Long idPedido);

    @Query("UPDATE itemDePedido set sincronizado  = :sincronizado where id = :id")
    public int atualizarSincronismo(Long id,String sincronizado);





}
