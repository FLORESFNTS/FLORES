package com.tqlweb.tqlsaldo.db.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tarjetas")
public class TarjetaEntity {

    @PrimaryKey(autoGenerate = true)
    public int idtarjeta;

    public String nucliente;
    public String nutarjeta;
    public String leyenda;

    public TarjetaEntity(String nucliente, String nutarjeta, String leyenda) {

        this.nucliente = nucliente;
        this.nutarjeta = nutarjeta;
        this.leyenda = leyenda;
    }


    public int getIdtarjeta() {
        return idtarjeta;
    }

    public void setIdtarjeta(int idtarjeta) {
        this.idtarjeta = idtarjeta;
    }

    public String getNucliente() {
        return nucliente;
    }

    public void setNucliente(String nucliente) {
        this.nucliente = nucliente;
    }

    public String getNutarjeta() {
        return nutarjeta;
    }

    public void setNutarjeta(String nutarjeta) {
        this.nutarjeta = nutarjeta;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }


}
