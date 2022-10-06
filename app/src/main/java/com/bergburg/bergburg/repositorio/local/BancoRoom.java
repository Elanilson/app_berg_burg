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
import com.bergburg.bergburg.repositorio.interfaces.CategoriaDAO;
import com.bergburg.bergburg.repositorio.interfaces.ItemDePedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.MesaDAO;
import com.bergburg.bergburg.repositorio.interfaces.PedidoDAO;
import com.bergburg.bergburg.repositorio.interfaces.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database( entities = {Categoria.class, Produto.class, Pedido.class, ItemDePedido.class, Mesa.class},version = 1)
public abstract class BancoRoom extends RoomDatabase {
    public static BancoRoom INSTACE;

    public abstract CategoriaDAO categoriaDAO();
    public abstract ProdutoDAO produtoDAO();
    public abstract PedidoDAO pedidoDAO();
    public abstract ItemDePedidoDAO itemDePedidoDAO();
    public abstract MesaDAO mesaDAO();

    private static Executor mExecutor = Executors.newSingleThreadExecutor();
    public static BancoRoom getInstance(Context context){
        if(INSTACE == null){
            INSTACE = Room.databaseBuilder(context,BancoRoom.class,"bergBurg_db")
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                                List<Categoria> categorias = new ArrayList<>();
                                List<Produto> produtos = new ArrayList<>();
                                List<Mesa> mesas = new ArrayList<>();
                                //teste
                                CategoriaDAO dao = getInstance(context).categoriaDAO();
                                categorias.add(new Categoria("Artesanais Regionais"));
                                categorias.add(new Categoria("Bebidas"));
                                categorias.add(new Categoria("Entradas"));
                                categorias.add(new Categoria("Lanches"));
                                categorias.add(new Categoria("Lanches Especiais"));
                                categorias.add(new Categoria("Misto Quente"));
                                categorias.add(new Categoria("Pizzas"));

                                for (Categoria categoria : categorias) {
                                    mExecutor.execute(() ->   dao.insert(categoria));

                                }

                                ProdutoDAO dao1 = getInstance(context).produtoDAO();
                                produtos.add(new Produto(1, "Picanha Pai D´egua", "Pão Brioche, Carne 160Gg (100% Picanha), queijo cheddar, Cebola Caramelizada e Salada", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/2597/154DE1582BBCD56F629B77BC3E76B7E058FFF8E9B68256528182872B6223782F.jpeg", 24f));
                                produtos.add(new Produto(2, "Refri Lata", "Sabores", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/2597/6FA845A443BA1577DE8B0C210FA3661BCA22127C69DE848A221B083E11DFB59D.jpeg", 5f));
                                produtos.add(new Produto(3, "Onions Rings", " Porção de Onions Rings", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/c6e2eb99-129e-4953-a256-770fd0d9471a.jpg", 12f));
                                produtos.add(new Produto(4, "Hot Dog ", "salsicha + salada", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/2597/59C6B8D533DF52ED688796C9DABAE15DCAF080F49A75904EF3C6EEAD08B32717.jpeg", 10f));
                                produtos.add(new Produto(5, "Especial da Casa", "ovo + frango + bacon + calabresa + queijo + presunto", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/2597/4BB32C9D3E09DC8EEFED39979590CDACB7511A34BE4DED427E377CB5F2184F4F.jpeg", 16f));
                                produtos.add(new Produto(6, "Misto Quente", "queijo + presunto", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/2597/955A16E179E49190E2C1452F2CE642F0A01D57AE9D856D04582B8C812F63E9E3.jpeg", 10f));
                                produtos.add(new Produto(7, "Pizza Grande", "8 fatias", "https://jotajaimagestorage.s3.sa-east-1.amazonaws.com/produtos/75ee3dbb-51e2-4625-bf2e-8e7a4639d520.jpg", 20f));

                                for (Produto produto : produtos) {
                                    mExecutor.execute(() -> dao1.insert(produto));
                                }
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
                                mesas.add(new Mesa(27));
                                mesas.add(new Mesa(28));
                                mesas.add(new Mesa(29));
                                mesas.add(new Mesa(30));
                                mesas.add(new Mesa(31));
                                mesas.add(new Mesa(32));
                                mesas.add(new Mesa(33));
                                mesas.add(new Mesa(34));
                                mesas.add(new Mesa(35));
                                mesas.add(new Mesa(36));
                                mesas.add(new Mesa(37));
                                mesas.add(new Mesa(38));
                                mesas.add(new Mesa(39));
                                mesas.add(new Mesa(40));

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
