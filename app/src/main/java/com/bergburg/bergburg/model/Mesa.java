package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bergburg.bergburg.constantes.Constantes;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "mesas")
public class Mesa implements Serializable {
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @SerializedName("numero")
    @ColumnInfo(name = "numero")
    private int numero;
    @ColumnInfo(name = "status")
    private String status = Constantes.LIVRE; // ( 0 ) fechado ( 1 ) aberto
    private Float total;


    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", status='" + status + '\'' +
                '}';
    }

    public Mesa() {
    }

    public Mesa(int numero) {
        this.numero = numero;
    }


    public Mesa(int numero, String status) {
        this.numero = numero;
        this.status = status;
    }

    public Mesa(Long id, int numero, String status) {
        this.id = id;
        this.numero = numero;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
