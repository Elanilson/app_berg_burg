package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.model.Categoria;

import java.util.List;

@Dao
public interface CategoriaDAO {
    @Insert
    public Long insert(Categoria categoria);
    @Update
    public int update(Categoria categoria);
    @Delete
    public int delete(Categoria categoria);
    @Query("select * from categorias where id = :id")
    public Categoria getCategoria(int id);
    @Query("select * from categorias")
    public List<Categoria> categorias();
}
