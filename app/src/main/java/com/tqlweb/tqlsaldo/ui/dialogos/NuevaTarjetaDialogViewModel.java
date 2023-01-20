package com.tqlweb.tqlsaldo.ui.dialogos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;
import com.tqlweb.tqlsaldo.repositorios.TarjetaRepository;

import java.util.List;

public class NuevaTarjetaDialogViewModel extends AndroidViewModel {
    private LiveData<List<TarjetaEntity>> allnotas;

    private TarjetaRepository tarjetaRepository;

    public NuevaTarjetaDialogViewModel(@NonNull Application application) {

        super(application);
        tarjetaRepository = new TarjetaRepository(application);
        allnotas = tarjetaRepository.getAllnotas();



    }


    //fragment que necesita recibir la nueva lista de datis
    public LiveData<List<TarjetaEntity>> getAllTarjetas(){
        return  allnotas;
    }

    //el fragment que insrte nueva nota debera comunicarlo a este view model
    public void insertarTarjeta(TarjetaEntity newnotaent){
        tarjetaRepository.insert(newnotaent);
    }


    public void updateTarjeta(TarjetaEntity notaActualizarEntity){
        tarjetaRepository.update(notaActualizarEntity);
    }

    public void removefromDb(int id) {
        tarjetaRepository.deleteTarjeta(id);
    }


}