package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "produtos")
public class Produto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = "idCategoria")
    @SerializedName("idCategoria")
    private int idCategoria;

    @ColumnInfo(name = "titulo")
    @SerializedName("titulo")
    private String titulo;

    @ColumnInfo(name = "descricao")
    @SerializedName("descricao")
    private String descricao;

    @ColumnInfo(name = "urlImagem")
    @SerializedName("urlImagem")
    private String urlImagem;

    @ColumnInfo(name = "preco")
    @SerializedName("preco")
    private Float preco;

    @ColumnInfo(name = "quantidade")
    @SerializedName("quantidade")
    private int quantidade ;

    @ColumnInfo(name = "observacao")
    @SerializedName("observacao")
    private String observacao;
    @Ignore
    private String identificador;
    @Ignore
    private Long idItemPedido;

    public Produto() {
    }

    public Produto( String titulo, Float preco,int idCategoria) {
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.preco = preco;
    }

    public Produto(String titulo, String descricao, Float preco,int idCategoria ) {
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Produto(int idCategoria, String titulo, String descricao, String urlImagem, Float preco) {
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.preco = preco;
    }

    public Produto(int idCategoria, String titulo, String descricao, String urlImagem, Float preco, int quantidade, String observacao) {
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }



    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", idCategoria=" + idCategoria +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", urlImagem='" + urlImagem + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", observacao='" + observacao + '\'' +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
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
