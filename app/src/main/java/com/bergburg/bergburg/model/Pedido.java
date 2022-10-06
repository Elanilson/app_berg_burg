package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bergburg.bergburg.constantes.Constantes;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pedidos")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;
    @ColumnInfo(name = "idUsuario")
    @SerializedName("idUsuario")
    private Long idUsuario;
    @ColumnInfo(name = "numeroMesa")
    @SerializedName("numeroMesa")
    private int numeroMesa;
    @ColumnInfo(name = "total")
    @SerializedName("total")
    private Float total = 0f;
    @ColumnInfo(name = "aberto")
    @SerializedName("aberto")
    private String aberto = Constantes.FECHADO; // ( 0 ) fechado ( 1 ) aberto
    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status = Constantes.NAO_ENVIADO;

    public Pedido() {
    }

    public Pedido(Long idUsuario, int numeroMesa, String aberto) {
        this.idUsuario = idUsuario;
        this.numeroMesa = numeroMesa;
        this.aberto = aberto;
    }

    public Pedido(Long idUsuario, int numeroMesa, String aberto, String status) {
        this.idUsuario = idUsuario;
        this.numeroMesa = numeroMesa;
        this.aberto = aberto;
        this.status = status;
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

    public String getAberto() {
        return aberto;
    }

    public void setAberto(String aberto) {
        this.aberto = aberto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
