package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itemDePedido")
public class ItemDePedido {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "idPedido")
    private Long idPedido;
    @ColumnInfo(name = "idProduto")
    private Long idProduto;
    @ColumnInfo(name = "quantidade")
    private int quantidade = 1;

    public ItemDePedido() {
    }

    public ItemDePedido(Long idPedido, Long idProduto) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
    }

    public ItemDePedido(Long idPedido, Long idProduto, int quantidade) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
