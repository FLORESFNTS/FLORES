
package com.tqlweb.tqlsaldo.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carga {

    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("horacarga")
    @Expose
    private String horacarga;
    @SerializedName("nutarjeta")
    @Expose
    private String nutarjeta;
    @SerializedName("nufolio")
    @Expose
    private String nufolio;
    @SerializedName("escarga")
    @Expose
    private String escarga;
    @SerializedName("placacarga")
    @Expose
    private String placacarga;
    @SerializedName("importe")
    @Expose
    private String importe;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoracarga() {
        return horacarga;
    }

    public void setHoracarga(String horacarga) {
        this.horacarga = horacarga;
    }

    public String getNutarjeta() {
        return nutarjeta;
    }

    public void setNutarjeta(String nutarjeta) {
        this.nutarjeta = nutarjeta;
    }

    public String getNufolio() {
        return nufolio;
    }

    public void setNufolio(String nufolio) {
        this.nufolio = nufolio;
    }

    public String getEscarga() {
        return escarga;
    }

    public void setEscarga(String escarga) {
        this.escarga = escarga;
    }

    public String getPlacacarga() {
        return placacarga;
    }

    public void setPlacacarga(String placacarga) {
        this.placacarga = placacarga;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

}
