package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produtos")
public class Produto {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "idCategoria")
    private int idCategoria;
    @ColumnInfo(name = "nome")
    private String nome;
    @ColumnInfo(name = "descricao")
    private String descricao;
    @ColumnInfo(name = "urlImagem")
    private String urlImagem;
    @ColumnInfo(name = "preco")
    private Float preco;

    public Produto() {
    }


}
