package com.bergburg.bergburg.listeners;

public interface APIListener<T>{
    void onSuccess(T result);
    void onFailures(String mensagem);

}
