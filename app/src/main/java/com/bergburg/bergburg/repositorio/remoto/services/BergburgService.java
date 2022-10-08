package com.bergburg.bergburg.repositorio.remoto.services;

import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BergburgService {
    @GET("getCategorias.php")
    Call<Dados>  getCategorias();
    @GET("getPedidos.php")
    Call<Dados>  getPedido();
    @GET("getMesas.php")
    Call<Dados>  getMesas();
    @GET("getProdutos.php")
    Call<Dados>  getProdutos();

    @POST("salvarPedido.php/")
    @FormUrlEncoded
    Call<Pedido>  salvarPedido(
            @Field("idUsuario") Long idUsuario,
            @Field("idMesa") int idMesa,
            @Field("aberturaPedido") String aberturaPedido,
            @Field("status") String status,
            @Field("total") Float total,
            @Field("identificadorUnico") String identificadorUnico
    );

    @POST("salvarItemDoPedido.php/")
    @FormUrlEncoded
    Call<ItemDePedido>  salvarItemDoPedido(
            @Field("idPedido") Long idPedido,
            @Field("idProduto") Long idProduto,
            @Field("preco") Float preco,
            @Field("quantidade") int quantidade,
            @Field("observacao") String observacao,
            @Field("indentificadorUnico") String identificador
    );

    @FormUrlEncoded
    @POST("getProdutosPorcategoria.php/")
    Call<Dados>  produtosPorCategoria(@Field("idCategoria") Long id);
    @FormUrlEncoded
    @POST("getProdutosPorcategoria.php/")
    Call<Dados>  getItensDoPedido(@Field("idCategoria")Long id);
}
