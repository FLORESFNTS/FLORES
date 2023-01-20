package com.tqlweb.tqlsaldo.repositorios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.db.TqlRoomDataBase;
import com.tqlweb.tqlsaldo.db.dao.TarjetaDao;
import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;

import java.util.List;

public class TarjetaRepository {
    private TarjetaDao tarjetaDao;
    private LiveData<List<TarjetaEntity>> alltarjetas;

    public TarjetaRepository(Application application) {
        TqlRoomDataBase db = TqlRoomDataBase.getDatabase(application);
        tarjetaDao = db.tarjetaDao();
        alltarjetas = tarjetaDao.getAll();
        //allnotasfavs = notadao.getAllFavoritas();
    }


    public LiveData<List<TarjetaEntity>> getAllnotas(){
        return alltarjetas;
    }

    public void insert (TarjetaEntity tarjetaEntity){
        new insertAsyncTask(tarjetaDao).execute(tarjetaEntity);
    }

    public void deleteTarjeta(int id) {
        new deleteAsyncTask(tarjetaDao).execute(id);
    }


    private static class  deleteAsyncTask extends AsyncTask<Integer,Void,Void>{
        private TarjetaDao tarjetaDatoAsyncTask;

        deleteAsyncTask(TarjetaDao dao){

            tarjetaDatoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            tarjetaDatoAsyncTask.deletebyID(integers[0]);
            return null;
        }

    }



    private static class  insertAsyncTask extends AsyncTask<TarjetaEntity,Void,Void> {
        private TarjetaDao tarjetaDatoAsyncTask;

        insertAsyncTask(TarjetaDao dao){

            tarjetaDatoAsyncTask = dao;
        }


        @Override
        protected Void doInBackground(TarjetaEntity... tarjetaEntities) {
            tarjetaDatoAsyncTask.insert(tarjetaEntities[0]);
            return null;
        }
    }


    public void update (TarjetaEntity nota){

        new updateAsyncTask(tarjetaDao).execute(nota);
    }


    private static class  updateAsyncTask extends AsyncTask<TarjetaEntity,Void,Void> {
        private TarjetaDao notaDatoAsyncTask;

        updateAsyncTask(TarjetaDao dao){

            notaDatoAsyncTask = dao;
        }


        @Override
        protected Void doInBackground(TarjetaEntity... tarjetaEntities) {
            notaDatoAsyncTask.updateTarjeta(tarjetaEntities[0]);
            return null;
        }
    }




}
