package com.tqlweb.tqlsaldo.ui.saldo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tqlweb.tqlsaldo.common.Constantes;
import com.tqlweb.tqlsaldo.common.SharedPreferencesManager;
import com.tqlweb.tqlsaldo.repositorios.SaldoRepository;
import com.tqlweb.tqlsaldo.retrofit.response.Carga;
import com.tqlweb.tqlsaldo.retrofit.response.Estacion;
import com.tqlweb.tqlsaldo.retrofit.response.Saldo;

import java.util.List;

public class SaldoViewModel extends AndroidViewModel {

    private SaldoRepository saldoRepository;
    public MutableLiveData<Saldo> respuestaSaldoLiveData;
    private LiveData<List<Estacion>> estaciones;
    private LiveData<List<Carga>> cargas;

    public SaldoViewModel(@NonNull Application application) {
        super(application);

        saldoRepository = new SaldoRepository();
        //iniciamos una lista si quieres
        //getSaldo("0","0","0");


        //Log.println(Log.ERROR,"REPOSITORIO","inicia saldo view model"+SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUTARJETA)+"--"
        //+SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUCLIENTE));
        respuestaSaldoLiveData= saldoRepository.getSaldoRepository(SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUTARJETA),
               SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUCLIENTE),"0");

    }

    public void getSaldo(String nutarjeta, String nucliente, String nip) {
        saldoRepository.getSaldoRepository(nutarjeta,nucliente,nip);
    }

    public void BuscarEstaciones(){

        estaciones = saldoRepository.getAllTEstaciones();
    }

    public LiveData<List<Estacion>> getEstaciones (){

        return estaciones;
    }

    public LiveData<List<Estacion>> getNewEstaciones (){
        estaciones = saldoRepository.getAllTEstaciones();
        return estaciones;
    }

    public LiveData<List<Carga>> getCargas (String nucliente,String nutarjeta){
        //aqui consulta
        cargas= saldoRepository.getLastTwCargas(nucliente,nutarjeta);
        return cargas;
    }

    public LiveData<List<Carga>> getNewCargas (String nucliente,String nutarjeta){
        cargas = saldoRepository.getLastTwCargas(nucliente,nutarjeta);
        return cargas;
    }


}


