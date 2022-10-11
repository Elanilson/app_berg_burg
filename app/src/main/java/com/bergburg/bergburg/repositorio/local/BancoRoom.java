package com.bergburg.bergburg.repositorio.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Produto;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.interfaces.CategoriaDAO;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.MesaDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;
import com.bergburg.bergburg.repositorio.interfaces.UsuarioDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database( entities = {Categoria.class, Produto.class, Pedido.class, ItemDePedido.class, Mesa.class, Usuario.class},version = 1)
public abstract class BancoRoom extends RoomDatabase {
    public static BancoRoom INSTACE;

    public abstract CategoriaDAO categoriaDAO();
    public abstract ProdutoDAO produtoDAO();
    public abstract PedidoDAO pedidoDAO();
    public abstract ItemDePedidoDAO itemDePedidoDAO();
    public abstract MesaDAO mesaDAO();
    public abstract UsuarioDAO usuarioDAO();

    private static Executor mExecutor = Executors.newSingleThreadExecutor();
    public static BancoRoom getInstance(Context context){
        if(INSTACE == null){
            INSTACE = Room.databaseBuilder(context,BancoRoom.class,"teste001")
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                                List<Categoria> categorias = new ArrayList<>();
                                List<Produto> produtos = new ArrayList<>();
                                List<Mesa> mesas = new ArrayList<>();
                                List<Usuario> usuarios = new ArrayList<>();
                                // INSERT CATEGORIA
                                CategoriaDAO dao = getInstance(context).categoriaDAO();
                                categorias.add(new Categoria("Entradas"));
                                categorias.add(new Categoria("Artesanais Regionais"));
                                categorias.add(new Categoria("Lanches"));
                                categorias.add(new Categoria("Lanches Especiais"));
                                categorias.add(new Categoria("Pizzas Tradicionais"));
                                categorias.add(new Categoria("Pizzas Premium "));
                                categorias.add(new Categoria("Bebidas"));
                                categorias.add(new Categoria("Drinks"));

                                for (Categoria categoria : categorias) {
                                    mExecutor.execute(() ->   dao.insert(categoria));

                                }

                                //INSERT USUARIOS
                            UsuarioDAO daoUsuario = getInstance(context).usuarioDAO();
                                usuarios.add(new Usuario( "atendimento", "atendimento", "atendimento"));
                                usuarios.add(new Usuario( "cozinha", "cozinha", "cozinha"));
                                usuarios.add(new Usuario( "suporte", "suporte", "suporte"));

                            for (Usuario usuario : usuarios) {
                                mExecutor.execute(() ->   daoUsuario.insert(usuario));

                            }

                            //INSERT PRODUTOS
                            ProdutoDAO dao1 = getInstance(context).produtoDAO();

							produtos.add(new Produto("Batata Frita","Batata Frita",12f, 1));
							produtos.add(new Produto("Onion Rings","Onion Rings",12f, 1));
							produtos.add(new Produto("Macaxeira Frita","Macaxeira Frita",12f, 1));
							produtos.add(new Produto("Bolinha de Queijo","Bolinha de Queijo",18f, 1));
							produtos.add(new Produto("Frango frito (coxinha)","FRANGO FRITO ( COXINHA)",20f, 1));
							produtos.add(new Produto("Picanha Pai Dégua","PÃO BRIOCHE, CARNE 16f0G (10f0% PICANHA));QUEIJO CHEDDAR, CEBOLA CARAMELIZADA ESALADA",24f, 2));
							produtos.add(new Produto("Pavulagem","PÃO BRIOCHE, SABOROSO BLEND DE 16f0G,CEBOLA CRISPY, QUEIJO CUIA E TOMATE",24f, 2));
							produtos.add(new Produto("Tchaba Marajoara","PÃO BRIOCHE, SABOROSO BLEND DE 16f0G,CEBOLA CRISPY, QUEIJO DE BÚFALA, ALFACE ETOMATE",24f, 2));
							produtos.add(new Produto("Égua do Duplo","PÃO BRIOCHE, 2 SUCULENTAS CARNES DE 12f0G, 2FATIAS DE QUEIJO, BACON E CEBOLACARAMELIZADA",27f, 2));
							produtos.add(new Produto("Pororoca","ÃO AUSTRALIANO, CARNE 16f0G , QUEIJO,CAMARÃO ROSA , REPOLHO REFOGADO, CEBOLACARAMELIZADA",27f, 2));
							produtos.add(new Produto("Dirrocha","PÃO AUSTRALIANO, CARNE 16f0G, QUEIJOCHEDDAR, CATUPIRY EMPANADO, ALFACE ETOMATE",27f, 2));
							produtos.add(new Produto("Fulano","AUSTRALIANO, CARNE 16f0G, MOLHO CHEDDAR,ANEL DE BACON, ALFACE E TOMATE",24f, 2));
							produtos.add(new Produto("Hambúrguer","Carne, salada",10f, 3));
							produtos.add(new Produto("Cachorro Quente","Cachorro Quente",10f, 3));
							produtos.add(new Produto("Hot Dog","Salsicha, salada",10f, 3));
							produtos.add(new Produto("Misto Quente","Queijo e Presunto",10f, 3));
							produtos.add(new Produto("Sanpará","Ovo, queijo, presunto",14f, 3));
							produtos.add(new Produto("X-Hot","Carne, salsicha, queij",14f, 3));
							produtos.add(new Produto("X-Salada","Carne, queijo, salada, presunto",14f, 3));
							produtos.add(new Produto("X-Bacon","Carne, queijo, bacon",14f, 3));
							produtos.add(new Produto("X-Calabresa","Carne, queijo, calabresa",14f, 3));
							produtos.add(new Produto("X-Burg","Carne, queijo",14f, 3));
							produtos.add(new Produto("X-Frango","Frango, queijo",14f, 3));
							produtos.add(new Produto("X-Frango Salada","Frango, queijo, presunto, salada",14f, 3));
							produtos.add(new Produto("X-Eggs","Carne, queijo, ovo",14f, 3));
							produtos.add(new Produto("Cachorrão","Cachorrão",14f, 3));
							produtos.add(new Produto("Mistão","3 fatias de pão de forma, 2 fatias de presunto 2 fatias dequeijo",14f, 3));
							produtos.add(new Produto("Americano","Ovo, salsicha, queijo, presunto",16f, 3));
							produtos.add(new Produto("Bauru","Ovo, calabresa, queijo, presunto",16f, 3));
							produtos.add(new Produto("X-Frango Calabresa","Frango, calabresa, queijo",16f, 3));
							produtos.add(new Produto("X-Frango Bacon","Frango, bacon, queijo",16f, 3));
							produtos.add(new Produto("X-Eggs Frango","Frango, ovo, queij",16f, 3));
							produtos.add(new Produto("X-Eggs Burg","Ovo, carne, queijo, presunto",16f, 3));
							produtos.add(new Produto("X-Eggs Calabresa","Carne, calabresa, queijo, ovo",16f, 3));
							produtos.add(new Produto("X-Eggs Bacon","Carne, bacon, queijo, ovo",16f, 3));
							produtos.add(new Produto("X-Eggs Frango Salada","Frango, ovo, queijo, presunto",18f, 3));
							produtos.add(new Produto("X-Eggs Frango Calabresa","Frango, ovo, calabresa, queijo",18f, 3));
							produtos.add(new Produto("X-Eggs Frango Bacon","Frango, ovo, bacon, queijo",18f, 3));
							produtos.add(new Produto("X-Eggs Burg Frango","Carne, frango, ovo, queijo",18f, 3));
							produtos.add(new Produto("Duplo Burg com 4 Queijos","2 carne, creme 4 queijos, mussarela, salada",16f, 4));
							produtos.add(new Produto("Cheddar Bacon","Carne, bacon, mussarela, creme cheddar, salada",16f, 4));
							produtos.add(new Produto("Cheddar Calabresa","Carne, calabresa, mussarela, creme cheddar, salada",16f, 4));
							produtos.add(new Produto("Eggs Cheddar Hot","Carne, ovo, salsicha, queijo, creme cheddar, salada",18f, 4));
							produtos.add(new Produto("Eggs Cheddar Bacon","Carne, ovo, bacon, queijo, creme cheddar, salada",18f, 4));
							produtos.add(new Produto("X-Catupiry","Carne, frango, mussarela, catupiry, salada",18f, 4));
							produtos.add(new Produto("X-Paraense","Camarão regional, ovo, mussarela, jambu, salada",18f, 4));
							produtos.add(new Produto("Especial da Casa","Ovo, frango, bacon, calabresa, queijo, presunto",18f, 4));
							produtos.add(new Produto("X-Charque","Pão careca, carne, charque, mussarela, salada",18f, 4));
							produtos.add(new Produto("X-Filé","Pão careca, filé mignon, mussarela, salada",18f, 4));
							produtos.add(new Produto("X-Leitão","Pão careca, pernil, mussarela, salada",18f, 4));
							produtos.add(new Produto("X-Eggs Filé","Pão careca, filé mignon, ovo, mussarela, salada",20f, 4));
							produtos.add(new Produto("X-Eggs Leitão","Pão careca, pernil, ovo, mussarela, salada",20f, 4));
							produtos.add(new Produto("X-Eggs Camarão","Carne, camarão rosa, mussarela, presunto, salada",20f, 4));
							produtos.add(new Produto("Mista","Mussarela, presunto, calabresa, cebola, milho e ervilha",20f, 5));
							produtos.add(new Produto("Mussarela","Queijo mussarela, tomate e orégano",20f, 5));
							produtos.add(new Produto("Calabresa","Mussarela, calabresa, cebola e orégano",20f, 5));
							produtos.add(new Produto("Marguerita","Mussarela, tomate, manjericão e orégano",35f, 6));
							produtos.add(new Produto("Frango Com Catupiry","Mussarela, frango, catupiry e orégano",35f, 6));
							produtos.add(new Produto("Portuguesa","Mussarela, presunto, calabresa, ovo, pimentões,cebola, milho e ervilha",35f, 6));
							produtos.add(new Produto("Búfala","Mussarela, queijo de búfala, manjericão e orégano",35f, 6));
							produtos.add(new Produto("Atum","Mussarela, Atum, cebola e orégano",35f, 6));
							produtos.add(new Produto("Quatro Queijos","Mussarela, queijo parmesão, queijo provolonee catupiry",35f, 6));
							produtos.add(new Produto("Bacon Com Catupiry","Mussarela, bacon, catupiry e orégano",35f, 6));
							produtos.add(new Produto("Cheddar Calabacon","Mussarela,bacon,calabresa, creme cheddar e orégano",35f, 6));
							produtos.add(new Produto("Peperone","Mussarela, peperone, cebola e orégano",35f, 6));
							produtos.add(new Produto("Filé com Fritas","Mussarela, filé, batata frita e orégano",40f, 6));
							produtos.add(new Produto("Filé com Bacon","Mussarela, filé, bacon, azeitona e orégano",40f, 6));
							produtos.add(new Produto("Moda da Casa","Mussarela, presunto, bacon, calabresa, azeitona, tomate e ovo",40f, 6));
							produtos.add(new Produto("Paraense","Mussarela, camarão rosa, jambu, tucupi e orégano",45f, 6));
							produtos.add(new Produto("Camarão com Catupiry","Mussarela, camarão rosa, catupiry e orégano",45f, 6));
							produtos.add(new Produto("Suprema","Mussarela, filé, camarão rosa, azeitona e orégano",50f, 6));
							produtos.add(new Produto("Prestígio","Chocolate e coco ralando",32f, 6));
							produtos.add(new Produto("Chocolate com Banana","Chocolate, banana e chocolate granulado",32f, 6));
							produtos.add(new Produto("Banana Com Canela","Queijo, banana e canela",32f, 6));
							produtos.add(new Produto("Refrigerante Lata","Refrigerante Lata",5f, 7));
							produtos.add(new Produto("Refrigerante 1L","Refrigerante 1L",6f, 7));
							produtos.add(new Produto("Refrigerante 1,5L","Refrigerante 1,5L",8f, 7));
							produtos.add(new Produto("Refrigerante 2L","Refrigerante 2L",10f, 7));
							produtos.add(new Produto("H2OH","H2OH",5f, 7));
							produtos.add(new Produto("Água","Água",3f, 7));
							produtos.add(new Produto("Suco","Consultar sabor ",3f, 7));
							produtos.add(new Produto("Caipirinha","Limão Taiti, Açúcar, 51",8f, 8));
							produtos.add(new Produto("Caipirinha de Jambu","Cachaça de Jambu, Limão Taiti, Açúcar",10f, 8));
							produtos.add(new Produto("Caipivodka","Vodka, Limão Taiti, Açúcar",12f, 8));
							produtos.add(new Produto("Caipifruta","Vodka, Leite Condensado ( consultar fruta disponível)",14f, 8));
							produtos.add(new Produto("Sex on the beach","Suco de Laranja, Vodka, Xarope de Frutas Vermelhas",14f, 8));
							produtos.add(new Produto("Lagoa Azul","Curaçal Blue, Vodka, Suco de Abacaxi",14f, 8));
							produtos.add(new Produto("Mojito Tradicional","Rum Branco, Hortelã, Tônica",14f, 8));
							produtos.add(new Produto("Mojito de Frutas","Rum Branco, Hortelã, ( consultar fruta disponível)",14f, 8));
							produtos.add(new Produto("Tropical Gin","Redbull Tropical, Limão Siciliano, Gin",20f, 8));
							produtos.add(new Produto("Maracugin","Maracujá, Tônica, Rodelas de Laranja, Gin",20f, 8));
							produtos.add(new Produto("Gin Côco","Redbull de Côco, Água de Côco, Gin",20f, 8));
							produtos.add(new Produto("Gin Pitaya","Redbull de Pitaya, Pitaya, Gin",20f, 8));

                                for (Produto produto : produtos) {
                                    mExecutor.execute(() -> dao1.insert(produto));
                                }


                             //INSERT MESAS
                            MesaDAO dao2 = getInstance(context).mesaDAO();
                                mesas.add(new Mesa(1)) ;
                                mesas.add(new Mesa(2)) ;
                                mesas.add(new Mesa(3)) ;
                                mesas.add(new Mesa(4)) ;
                                mesas.add(new Mesa(5)) ;
                                mesas.add(new Mesa(6)) ;
                                mesas.add(new Mesa(7)) ;
                                mesas.add(new Mesa(8)) ;
                                mesas.add(new Mesa(9)) ;
                                mesas.add(new Mesa(10));
                                mesas.add(new Mesa(11));
                                mesas.add(new Mesa(12));
                                mesas.add(new Mesa(13));
                                mesas.add(new Mesa(14));
                                mesas.add(new Mesa(15));
                                mesas.add(new Mesa(16));
                                mesas.add(new Mesa(17));
                                mesas.add(new Mesa(18));
                                mesas.add(new Mesa(19));
                                mesas.add(new Mesa(20));
                                mesas.add(new Mesa(21));
                                mesas.add(new Mesa(22));
                                mesas.add(new Mesa(23));
                                mesas.add(new Mesa(24));
                                mesas.add(new Mesa(25));
                                mesas.add(new Mesa(26));


                            for (Mesa mesa : mesas) {
                                mExecutor.execute(() -> dao2.insert(mesa));
                            }
                            }


                    })
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTACE;
    }
    private static Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
}
