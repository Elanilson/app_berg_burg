package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "mesas")
public class Mesa {
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("numero")
    @ColumnInfo(name = "numero")
    private int numero;
    @SerializedName("livre")
    @ColumnInfo(name = "livre")
    private int livre = 0; // ( 0 ) fechado ( 1 ) aberto

    public Mesa() {
    }

    public Mesa(int numero) {
        this.numero = numero;
    }

    public Mesa(int numero, int livre) {
        this.numero = numero;
        this.livre = livre;
    }

    public Mesa(int id, int numero, int livre) {
        this.id = id;
        this.numero = numero;
        this.livre = livre;
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

    public int getLivre() {
        return livre;
    }

    public void setLivre(int livre) {
        this.livre = livre;
    }
}
