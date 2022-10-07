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
    @ColumnInfo(name = "idMesa")
    @SerializedName("idMesa")
    private int idMesa;
    @ColumnInfo(name = "total")
    @SerializedName("total")
    private Float total = 0f;
    @ColumnInfo(name = "aberturaPedido")
    @SerializedName("aberturaPedido")
    private String aberturaPedido = Constantes.FECHADO; // ( 0 ) fechado ( 1 ) aberto
    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status = Constantes.NAO_ENVIADO;
    @ColumnInfo(name = "identificadorUnico")
    @SerializedName("identificadorUnico")
    private String identificadorUnico ;
    @ColumnInfo(name = "sincronizado")
    private String sincronizado = Constantes.NAO;

    public Pedido() {
    }

    public Pedido(Long id, String sincronizado) {
        this.id = id;
        this.sincronizado = sincronizado;
    }

    public Pedido(Long idUsuario, int idMesa, String aberturaPedido) {
        this.idUsuario = idUsuario;
        this.idMesa = idMesa;
        this.aberturaPedido = aberturaPedido;
    }

    public Pedido(Long idUsuario, int idMesa, String aberturaPedido, String status) {
        this.idUsuario = idUsuario;
        this.idMesa = idMesa;
        this.aberturaPedido = aberturaPedido;
        this.status = status;
    }

    public Pedido(Long idUsuario, int idMesa, String aberturaPedido, String status, String identificadorUnico) {
        this.idUsuario = idUsuario;
        this.idMesa = idMesa;
        this.aberturaPedido = aberturaPedido;
        this.status = status;
        this.identificadorUnico = identificadorUnico;
    }

    public String getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(String sincronizado) {
        this.sincronizado = sincronizado;
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

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getAberturaPedido() {
        return aberturaPedido;
    }

    public void setAberturaPedido(String aberturaPedido) {
        this.aberturaPedido = aberturaPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentificadorUnico() {
        return identificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        this.identificadorUnico = identificadorUnico;
    }
}
