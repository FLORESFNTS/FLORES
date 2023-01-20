package com.tqlweb.tqlsaldo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tqlweb.tqlsaldo.db.dao.TarjetaDao;
import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;

@Database(entities = {TarjetaEntity.class}, version = 1)
public abstract class TqlRoomDataBase extends RoomDatabase {

    public abstract TarjetaDao tarjetaDao();

    private static volatile  TqlRoomDataBase INSTANCE;


    public static  TqlRoomDataBase getDatabase(final Context context){

        if (INSTANCE == null){
            synchronized (TqlRoomDataBase.class){
                if (INSTANCE== null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TqlRoomDataBase.class,"tqlmxapp_Database").build();
                }
            }
        }

        return INSTANCE;

    }
}
