package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Categoria  implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("titulo")
    private String titulo;

    public Categoria() {
    }

    public Categoria(String titulo) {
        this.titulo = titulo;
    }

    public Categoria(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
