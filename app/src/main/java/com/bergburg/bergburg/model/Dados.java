package com.bergburg.bergburg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dados {
    @SerializedName("mesas")
    private List<Mesa> mesas;
    @SerializedName("mesa")
    private Mesa mesa;
    @SerializedName("usuario")
    private Usuario usuario;
    @SerializedName("categorias")
    private List<Categoria> categorias;
    @SerializedName("pedidos")
    private List<Pedido> pedidos;
    @SerializedName("pedido")
    private Pedido pedido;
    @SerializedName("itensPedido")
    private List<ItemDePedido> itensPedido;
    @SerializedName("itensComanda")
    private List<ItensComanda> ItensComanda;
    @SerializedName("produtos")
    private List<Produto> produtos;
    @SerializedName("error")
    private String error = "";
    @SerializedName("status")
    private Boolean status = false;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<com.bergburg.bergburg.model.ItensComanda> getItensComanda() {
        return ItensComanda;
    }

    public void setItensComanda(List<com.bergburg.bergburg.model.ItensComanda> itensComanda) {
        ItensComanda = itensComanda;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<ItemDePedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemDePedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
