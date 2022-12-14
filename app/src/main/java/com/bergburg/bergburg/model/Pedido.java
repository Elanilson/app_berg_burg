package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bergburg.bergburg.constantes.Constantes;
import com.google.gson.annotations.SerializedName;

public class Pedido {
    @SerializedName("id")
    private Long id;
    @SerializedName("idUsuario")
    private Long idUsuario;
    @SerializedName("idMesa")
    private int idMesa;
    @SerializedName("total")
    private Float total = 0f;
    @SerializedName("aberturaPedido")
    private String aberturaPedido = Constantes.FECHADO; // ( 0 ) fechado ( 1 ) aberto
    @SerializedName("status")
    private String status = Constantes.NAO_ENVIADO;
    @SerializedName("identificadorUnico")
    private String identificadorUnico ;
    private String sincronizado = Constantes.NAO;
    private String  data_create;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idMesa=" + idMesa +
                ", total=" + total +
                ", aberturaPedido='" + aberturaPedido + '\'' +
                ", status='" + status + '\'' +
                ", identificadorUnico='" + identificadorUnico + '\'' +
                ", sincronizado='" + sincronizado + '\'' +
                ", data_create='" + data_create + '\'' +
                '}';
    }

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

    public String getData_create() {
        return data_create;
    }

    public void setData_create(String data_create) {
        this.data_create = data_create;
    }
}
