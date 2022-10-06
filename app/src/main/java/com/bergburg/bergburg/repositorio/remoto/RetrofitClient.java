package com.bergburg.bergburg.repositorio.remoto;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private RetrofitClient(){}

    private static Retrofit INSTACE;
    private static Retrofit getINSTACE(){
        if(INSTACE == null){
            INSTACE = new Retrofit.Builder()
                    .baseUrl("https://apkdoandroidonline.com/bergburg/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTACE;
    }

    public static <T> T classService(Class<T> classService){
        return getINSTACE().create(classService);
    }

}
