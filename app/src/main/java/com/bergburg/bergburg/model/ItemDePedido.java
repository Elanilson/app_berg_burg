package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bergburg.bergburg.constantes.Constantes;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "itemDePedido")
public class ItemDePedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = "titulo")
    @SerializedName("titulo")
    private String titulo;

    @ColumnInfo(name = "idPedido")
    @SerializedName("idPedido")
    private Long idPedido;

    @ColumnInfo(name = "idProduto")
    @SerializedName("idProduto")
    private Long idProduto;

    @ColumnInfo(name = "idCategoria")
    @SerializedName("idCategoria")
    private int idCategoria;

    @ColumnInfo(name = "descricao")
    @SerializedName("descricao")
    private String descricao;

    @ColumnInfo(name = "quantidade")
    @SerializedName("quantidade")
    private int quantidade = 1;

    @ColumnInfo(name = "observacao")
    private String observacao;

    @ColumnInfo(name = "sincronizado")
    private String sincronizado = Constantes.NAO;

    @ColumnInfo(name = "indentificadorUnico")
    @SerializedName("indentificadorUnico")
    private String indentificadorUnico ;

    @ColumnInfo(name = "preco")
    @SerializedName("preco")
    private Float preco = 0f;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status = Constantes.ATIVO;

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


    public ItemDePedido(Long idPedido, Long idProduto, int quantidade, String observacao) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public ItemDePedido(Long idPedido, Long idProduto, int quantidade, String observacao, String indentificadorUnico, Float preco) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.indentificadorUnico = indentificadorUnico;
        this.preco = preco;
    }

    public ItemDePedido(Long idPedido, Long idProduto, int quantidade, String observacao, String indentificadorUnico, Float preco, String status) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.indentificadorUnico = indentificadorUnico;
        this.preco = preco;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemDePedido{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idPedido=" + idPedido +
                ", idProduto=" + idProduto +
                ", idCategoria=" + idCategoria +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", observacao='" + observacao + '\'' +
                ", sincronizado='" + sincronizado + '\'' +
                ", indentificadorUnico='" + indentificadorUnico + '\'' +
                ", preco=" + preco +
                ", status='" + status + '\'' +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getIndentificadorUnico() {
        return indentificadorUnico;
    }

    public void setIndentificadorUnico(String indentificadorUnico) {
        this.indentificadorUnico = indentificadorUnico;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
