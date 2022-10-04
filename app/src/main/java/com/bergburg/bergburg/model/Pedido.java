package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedidos")
public class Pedido {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "idUsuario")
    private Long idUsuario;
    @ColumnInfo(name = "numeroMesa")
    private int numeroMesa;
    @ColumnInfo(name = "total")
    private Float total = 0f;
    @ColumnInfo(name = "aberto")
    private int aberto = 0; // ( 0 ) fechado ( 1 ) aberto

    public Pedido() {
    }

    public Pedido(Long idUsuario, int numeroMesa, int aberto) {
        this.idUsuario = idUsuario;
        this.numeroMesa = numeroMesa;
        this.aberto = aberto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public int getAberto() {
        return aberto;
    }

    public void setAberto(int aberto) {
        this.aberto = aberto;
    }
}
