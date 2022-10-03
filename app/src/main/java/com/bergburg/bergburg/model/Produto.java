package com.bergburg.bergburg.model;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private String urlImagem;
    private String tipo;
    private Float preco;

    public Produto() {
    }


    public Produto(Long id, String nome, String descricao, String urlImagem, String tipo, Float preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.tipo = tipo;
        this.preco = preco;
    }

    public Produto(String nome, String descricao, String urlImagem, String tipo, Float preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.tipo = tipo;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", urlImagem='" + urlImagem + '\'' +
                ", tipo='" + tipo + '\'' +
                ", preco=" + preco +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }
}
