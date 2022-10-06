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
    private int id;
    @SerializedName("numero")
    @ColumnInfo(name = "numero")
    private int numero;
    @SerializedName("livre")
    @ColumnInfo(name = "livre")
    private String livre = Constantes.LIVRE; // ( 0 ) fechado ( 1 ) aberto

    public Mesa() {
    }

    public Mesa(int numero) {
        this.numero = numero;
    }

    public Mesa(int numero, String livre) {
        this.numero = numero;
        this.livre = livre;
    }

    public Mesa(int id, int numero, String livre) {
        this.id = id;
        this.numero = numero;
        this.livre = livre;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", livre=" + livre +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLivre() {
        return livre;
    }

    public void setLivre(String livre) {
        this.livre = livre;
    }
}
