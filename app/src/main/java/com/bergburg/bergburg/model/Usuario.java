package com.bergburg.bergburg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bergburg.bergburg.constantes.Constantes;

import java.io.Serializable;

@Entity(tableName = "usuarios")
public class Usuario implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "nome")
    private String nome;
    @ColumnInfo(name = "senha")
    private String senha;
    @ColumnInfo(name = "cargo")
    private String cargo;
    @ColumnInfo(name = "status")
    private String status = Constantes.USUARIO_DESLOGADO;


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", cargo='" + cargo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Usuario() {
    }

    public Usuario(String nome, String senha, String cargo) {
        this.nome = nome;
        this.senha = senha;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
