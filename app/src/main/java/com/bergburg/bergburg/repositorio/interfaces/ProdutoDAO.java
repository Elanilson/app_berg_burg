package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.model.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {
    @Insert
    public Long insert(Produto produto);
    @Update
    public int update(Produto produto);
    @Delete
    public int delete(Produto produto);

    @Query("select * from produtos where id = :id")
    public Produto getProduto (Long id);

    @Query("select * from produtos where idCategoria = :idCategoria")
    public List<Produto> getProdutosPorCategoria (Long idCategoria);

    @Query("select * from produtos")
    public List<Produto> produtos();

}
