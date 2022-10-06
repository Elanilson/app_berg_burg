package com.bergburg.bergburg.model;

import com.google.gson.annotations.SerializedName;

public class Resposta {
    @SerializedName("mensagem")
    private String mensagem;
    @SerializedName("status")
    private Boolean status = false;

    public Resposta() {
    }

    public Resposta(String mensagem, Boolean status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public Resposta(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
