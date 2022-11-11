package com.bergburg.bergburg.repositorio.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO {
    @Insert
    public Long insert(Usuario usuario);
    @Update
    public int update(Usuario usuario);
    @Delete
    public int delete(Usuario usuario);

    @Query("select * from usuarios where id = :id")
    public Usuario getUsuaurio (Long id);

    @Query("select * from usuarios where status = 'Logado' limit 1")
    public Usuario getUsuarioLogado ();

    @Query("select * from usuarios where nome = :usuario and senha = :senha")
    public Usuario login (String usuario, String senha);



}
