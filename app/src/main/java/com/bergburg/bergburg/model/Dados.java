package com.bergburg.bergburg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dados {
    @SerializedName("mesas")
    public List<Mesa> mesas;
    @SerializedName("categorias")
    public List<Categoria> categorias;
    @SerializedName("produtos")
    public List<Produto> produtos;


}
