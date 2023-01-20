package com.tqlweb.tqlsaldo.retrofit;

import com.tqlweb.tqlsaldo.common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaldoClient {

    private  static SaldoClient instance = null;
    private SaldoService saldoService;
    private Retrofit retrofit;

    public SaldoClient() {

        //incluir en la cabecera de la peticion el tojen que autoriza al usuario
        OkHttpClient.Builder okhttpcliente = new OkHttpClient.Builder();
        //okhttpcliente.addInterceptor(new AuthInterceptor()); quito el interceptor por que no incluye peticion
        OkHttpClient cliente = okhttpcliente.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)//adjuntamos el token a las epticiones
                .build();

        saldoService = retrofit.create(SaldoService.class);

    }

    //patron singleton
    public static SaldoClient getInstance(){
        if (instance == null){
            instance = new SaldoClient();
        }
        return instance;
    }

    public SaldoService getService(){
        return saldoService;
    }



}
