package com.tqlweb.tqlsaldo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;

import java.util.List;

@Dao
public interface TarjetaDao {

    @Insert
    void insert (TarjetaEntity tarjeta);

    @Update
    void updateTarjeta(TarjetaEntity tarjeta);

    @Query("DELETE FROM tarjetas WHERE idtarjeta = :idNota ")
    void deletebyID(int idNota);

    @Query("Select * from tarjetas order by idtarjeta asc")
    LiveData<List<TarjetaEntity>>getAll();

    @Query("Select * from tarjetas where nucliente = 'algo'")
    LiveData<List<TarjetaEntity >> getAllCliente();


}
