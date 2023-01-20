package com.tqlweb.tqlsaldo.retrofit;

import com.tqlweb.tqlsaldo.retrofit.request.RequestCarga;
import com.tqlweb.tqlsaldo.retrofit.request.RequestObtenerSaldo;
import com.tqlweb.tqlsaldo.retrofit.response.Carga;
import com.tqlweb.tqlsaldo.retrofit.response.Estacion;
import com.tqlweb.tqlsaldo.retrofit.response.Saldo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SaldoService {
    //TODO crear rutas
    @POST("Mapa/getAppSaldo")
    Call<Saldo> obtenerSaldo(@Body RequestObtenerSaldo requestObtenerSaldo);

    @POST("Mapa/listarEstaciones")
    Call<List<Estacion>> getAllEstaciones();

    @POST("Mapa/ultimosConsumosLimiteApp")
    Call<List<Carga>> getLastCargas(@Body RequestCarga requestCarga);


}
