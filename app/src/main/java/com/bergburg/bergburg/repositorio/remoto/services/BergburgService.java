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
import retrofit2.http.PUT;

public interface BergburgService {
    @GET("getCategorias.php")
    Call<Dados>  getCategorias();

    @GET("getTodosOsPedidos.php")
    Call<Dados> getTodosOsPedidos();

    @GET("getMesas.php")
    Call<Dados>  getMesas();
    @GET("getMesasOcupadas.php")
    Call<Dados>  getMesasOcupadas();

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
            @Field("indentificadorUnico") String indentificadorUnico
    );

    @POST("criarPedido.php/")
    @FormUrlEncoded
    Call<Dados>  criarPedido(
            @Field("idUsuario") Long idUsuario,
            @Field("idMesa") Long idMesa,
            @Field("total") Float total
    );

    @POST("adicionarItemComanda.php/")
    @FormUrlEncoded
    Call<Dados>  adicionarItemComanda(
            @Field("idProduto") Long idPoduto,
            @Field("idMesa") Long idMesa,
            @Field("quantidade") int quantidade,
            @Field("observacao") String observacao
    );

    @POST("itensComanda.php/")
    @FormUrlEncoded
    Call<Dados>  itensComanda(
            @Field("idMesa") Long idMesa
    );
    @POST("getPedidosAbertos.php/")
    @FormUrlEncoded
    Call<Dados>  getPedidosAbertos(
            @Field("idMesa") Long idMesa
    );
    @POST("cancelarComanda.php/")
    @FormUrlEncoded
    Call<Dados>  cancelarComanda(
            @Field("idMesa") Long idMesa
    );
    @POST("getMesa.php/")
    @FormUrlEncoded
    Call<Dados>  getMesa(
            @Field("id") Long id
    );
    @POST("removerItemComanda.php/")
    @FormUrlEncoded
    Call<Dados>  removerItemComanda(
            @Field("idItemComanda") Long idItemComanda
    );

    @PUT("atualizarItemComanda.php/")
    @FormUrlEncoded
    Call<Dados>  atualizarItemComanda(
            @Field("id") Long idItemComanda,
            @Field("quantidade") int quantidade,
            @Field("observacao") String observacao
    );

    @POST("getItemPedido.php/")
    @FormUrlEncoded
    Call<Dados>  getItensPedido(
            @Field("idPedido") Long idPedido
    );
    @POST("getPedido.php/")
    @FormUrlEncoded
    Call<Dados>  getPedido(
            @Field("idPedido") Long idPedido
    );
    @POST("getPedidos.php/")
    @FormUrlEncoded
    Call<Dados>  getPedidos(
            @Field("idMesa") Long idPedido
    );
    @POST("login.php/")
    @FormUrlEncoded
    Call<Dados>  login(
            @Field("nome") String nome,
            @Field("senha") String senha
    );
    @POST("verificarUsuarioLogado.php/")
    @FormUrlEncoded
    Call<Dados>  verificarUsuarioLogado(
            @Field("id") Long id
    );
    @PUT("deslogar.php/")
    @FormUrlEncoded
    Call<Dados>  deslogar(
            @Field("id") Long id
    );

    @PUT("atualizarPedido.php/")
    @FormUrlEncoded
    Call<Pedido>  atualizarPedido(
            @Field("indentificadorUnico") String identificadorUnico,
            @Field("total") Float total,
            @Field("status") String status,
            @Field("aberturaPedido") String aberturaPedido
    );

    @PUT("alterarStatusMesa.php/")
    @FormUrlEncoded
    Call<Dados>  alterarStatusMesa(
            @Field("id") Long id
    );

    @PUT("atualizarQuantidadeitemPedido.php/")
    @FormUrlEncoded
    Call<ItemDePedido>  atualizarQuantidadeitemPedido(
            @Field("indentificadorUnico") String identificadorUnico,
            @Field("quantidade") int quantidade
    );

    @PUT("removerItemPedido.php/")
    @FormUrlEncoded
    Call<ItemDePedido>  removerItemDoPedido(
            @Field("indentificadorUnico") String identificadorUnico,
            @Field("status") String status
    );

    @PUT("atualizaritemPedido.php/")
    @FormUrlEncoded
    Call<ItemDePedido>  atualizarItemDoPedido(
            @Field("indentificadorUnico") String identificadorUnico,
            @Field("status") String status,
            @Field("quantidade") int quantidade,
            @Field("preco") Float preco,
            @Field("observacao") String observacao
    );

    @POST("salvarItemDoPedido.php/")
    @FormUrlEncoded
    Call<ItemDePedido> salvarItemDoPedido(
            @Field("idPedido") Long idPedido,
            @Field("idProduto") Long idProduto,
            @Field("preco") Float preco,
            @Field("quantidade") int quantidade,
            @Field("observacao") String observacao,
            @Field("status") String status,
            @Field("indentificadorUnico") String identificador
    );

    @FormUrlEncoded
    @POST("getProdutosPorcategoria.php/")
    Call<Dados>  produtosPorCategoria(@Field("idCategoria") Long id);
    @FormUrlEncoded
    @POST("getProdutosPorcategoria.php/")
    Call<Dados>  getItensDoPedido(@Field("idCategoria")Long id);
}
