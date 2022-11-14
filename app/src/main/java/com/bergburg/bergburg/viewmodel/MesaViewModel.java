package com.bergburg.bergburg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.listeners.APIListener;
import com.bergburg.bergburg.model.Dados;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.ItensComanda;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.ItemDoPedidoRepositorio;
import com.bergburg.bergburg.repositorio.MesaRepositorio;
import com.bergburg.bergburg.repositorio.PedidoRepositorio;
import com.bergburg.bergburg.repositorio.ProdutosRepositorio;
import com.bergburg.bergburg.repositorio.UsuarioRepositorio;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.repositorio.remoto.services.BergburgService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesaViewModel extends AndroidViewModel {
    private ProdutosRepositorio repositorio;
    private PedidoRepositorio pedidoRepositorio;
    private MesaRepositorio mesaRepositorio;
    private ItemDoPedidoRepositorio itemDoPedidoRepositorio;
    private UsuarioRepositorio usuarioRepositorio;




    private MutableLiveData<List<Produto>> _Produtos = new MutableLiveData<>();
    public LiveData<List<Produto>> produtos = _Produtos;

    private MutableLiveData<List<ItemDePedido>> _ItensDoPedido = new MutableLiveData<>();
    public LiveData<List<ItemDePedido>> itensDoPedido = _ItensDoPedido;

    private MutableLiveData<List<ItensComanda>> _ItensComanda = new MutableLiveData<>();
    public LiveData<List<ItensComanda>> itensComanda = _ItensComanda;


    private MutableLiveData<Pedido> _Pedido = new MutableLiveData<>();
    public LiveData<Pedido> pedido = _Pedido;

    private MutableLiveData<List<Pedido>> _Pedidos = new MutableLiveData<>();
    public LiveData<List<Pedido>> pedidos = _Pedidos;

    private MutableLiveData<Usuario> _Usuario = new MutableLiveData<>();
    public LiveData<Usuario> usuario = _Usuario;

    private MutableLiveData<Mesa> _Mesa = new MutableLiveData<>();
    public LiveData<Mesa> mesa = _Mesa;

    private MutableLiveData<Resposta> _Resposta = new MutableLiveData<>();
    public LiveData<Resposta> resposta = _Resposta;

    private List<ItemDePedido> itensDoPedido_Sem_OsRemovidos = new ArrayList<>();

    private MutableLiveData<List<Mesa>> _Mesas = new MutableLiveData<>();
    public LiveData<List<Mesa>> mesas = _Mesas;

    public MesaViewModel(@NonNull Application application) {
        super(application);

        repositorio = new ProdutosRepositorio(application.getApplicationContext());
        pedidoRepositorio = new PedidoRepositorio(application.getApplicationContext());
        mesaRepositorio = new MesaRepositorio(application.getApplicationContext());
        itemDoPedidoRepositorio = new ItemDoPedidoRepositorio(application.getApplicationContext());
        usuarioRepositorio = new UsuarioRepositorio(application.getApplicationContext());
    }


    public void carregarMesas(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Mesas.setValue(result.getMesas());
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Não foi possível carregar"));
            }
        };

        mesaRepositorio.carregarMesas(listener);

    }
    public void getMesa(Long id){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
               _Mesa.setValue(result.getMesa());
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Não foi possível Abrir Mesa"));
            }
        };

        mesaRepositorio.getMesa(listener,id);
    }

    public void getPedidosAbertos(Long idMesa){
        System.out.println("getPedidosAbertos idRecebido "+idMesa);
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Pedidos.setValue(result.getPedidos());
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Falha ao tentar conectar"));
            }
        };

        pedidoRepositorio.getPedidosAbertos(listener,idMesa);
    }

    public void atualizarItemComanda(Long idItemComanda, int quantidade, String observacao){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Resposta.setValue(new Resposta(Constantes.ATUALIZADO,true));
                }else{
                    _Resposta.setValue(new Resposta(Constantes.NAO_ATUALIZADO));
                }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.VERIFIQUE_CONEXAO));
            }
        };

        mesaRepositorio.atualizarItemComanda(listener,idItemComanda,quantidade,observacao);
    }
    public void abrirMesa(Long id){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Resposta.setValue(new Resposta(Constantes.MESA_ABERTA,true));
                }else{
                    _Resposta.setValue(new Resposta(Constantes.NAO_CONSEGUIR_ABRIR_MESA));
                }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.VERIFIQUE_CONEXAO));
            }
        };

        mesaRepositorio.abrirMesa(listener,id);
    }
    public void removerItemComanda(Long idItemComanda){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Resposta.setValue(new Resposta(Constantes.REMOVIDO,true));
                }else{
                    _Resposta.setValue(new Resposta(Constantes.NAO_REMOVIDO));
                }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.VERIFIQUE_CONEXAO));
            }
        };

        mesaRepositorio.removerItemComanda(listener,idItemComanda);
    }
    public void itensComanda(Long idMesa){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _ItensComanda.setValue(result.getItensComanda());
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta("Não foi possível carregar os itens da comanda"));
            }
        };

        mesaRepositorio.itensComanda(listener,idMesa);
    }
    public void cancelarComanda(Long idMesa){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
               if(result.getStatus()){
                   _Resposta.setValue(new Resposta(Constantes.CANCELADO,true));
               }else{
                   _Resposta.setValue(new Resposta(Constantes.NAO_CANCELADO));

               }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.VERIFIQUE_CONEXAO));
            }
        };

        mesaRepositorio.cancelarComanda(listener,idMesa);
    }

    public void getUsuario(Long idUsuario){
        if(idUsuario != null){
            APIListener<Dados> listener = new APIListener<Dados>() {
                @Override
                public void onSuccess(Dados result) {
                    _Usuario.setValue(result.getUsuario());
                }

                @Override
                public void onFailures(String mensagem) {
                    System.out.println("Error: "+mensagem);
                    _Resposta.setValue(new Resposta("Falha ao tentar conectar"));
                }
            };

            usuarioRepositorio.verificarUsuarioLogado(listener,idUsuario);
        }else{
            _Resposta.setValue(new Resposta("ID do usuário não encontrado"));
        }
    }

    public void adicionarItemComanda(Long idMesa,Long idProduto,String observacao,int quantidade){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Resposta.setValue(new Resposta(Constantes.ADICIONADO,true));
                }else{
                    _Resposta.setValue(new Resposta(Constantes.NAO_ADICIONADO));
                }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.VERIFIQUE_CONEXAO));
            }
        };
        mesaRepositorio.adicionarItemComanda(listener,idMesa,idProduto,observacao,quantidade);
    }

    public void criarPedido(Long idMesa,Long idUsuario,float total){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Resposta.setValue(new Resposta(Constantes.ENVIADO,true));
                }else{
                    _Resposta.setValue(new Resposta(Constantes.PEDIDO_NAO_ENVIADO));
                }
            }

            @Override
            public void onFailures(String mensagem) {
                System.out.println("Error: "+mensagem);
                _Resposta.setValue(new Resposta(Constantes.PEDIDO_NAO_ENVIADO));
            }
        };
        pedidoRepositorio.criarPedido(listener,idMesa,idUsuario,total);
    }



}
