package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;

import java.util.List;

@Dao
public interface MesaDAO {

    @Insert
    public Long insert(Mesa mesa);
    @Update
    public int update(Mesa mesa);
    @Delete
    public int delete(Mesa mesa);

    @Query("select * from mesas")
    public List<Mesa> mesas();

    @Query("select * from mesas where id = :idMesa")
    public Mesa mesas(int idMesa);

}
