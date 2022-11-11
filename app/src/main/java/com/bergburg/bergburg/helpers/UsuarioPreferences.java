package com.bergburg.bergburg.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class UsuarioPreferences {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "usuario.preferencias";

    private final String CHAVE_ID = "id";
    private final String CHAVE_NOME = "nome";
    private final String CHAVE_STATUS = "status";


    public UsuarioPreferences(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(NOME_ARQUIVO,0);
        editor = preferences.edit();

    }


         public void salvar(Long id,String nome,String status){
        editor.putLong(CHAVE_ID, id );
        editor.putString(CHAVE_NOME, nome );
        editor.putString(CHAVE_STATUS, status );
        editor.commit();
    }

    public void salvarStatus(String status){
        editor.putString(CHAVE_STATUS, status );
        editor.commit();
    }

    public void salvar(Long id,String nome){
        editor.putLong(CHAVE_ID, id );
        editor.putString(CHAVE_NOME, nome );
        editor.commit();
    }


    public String recuperarNome(){
        return preferences.getString(CHAVE_NOME, "");
    }
    public Long recuperarIDUSuario(){
        return preferences.getLong(CHAVE_ID, 0);
    }
    public String recuperarStatus(){
        return preferences.getString(CHAVE_STATUS, "");
    }

}
