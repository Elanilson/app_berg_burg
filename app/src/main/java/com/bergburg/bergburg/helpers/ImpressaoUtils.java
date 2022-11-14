package com.bergburg.bergburg.helpers;

import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Pedido;

import java.util.List;

public class ImpressaoUtils {

    public static void preparaDados(Pedido pedido , List<ItemDePedido> itens){
        String papel = "";

        papel = "Mesa "+pedido.getIdMesa()+" \n";
        papel += "N° pedido "+pedido.getId()+" \n";
        papel += "Total R$ "+pedido.getTotal()+" \n";
        for(ItemDePedido item : itens){
            papel += item.getQuantidade()+"x - "+item.getTitulo()+" \n";
            papel += "valor: R$ "+item.getPreco()+" \n";
        }


        System.out.println("Impressao");
        System.out.println(papel);
       // return papel;
    }
}
