
package com.tqlweb.tqlsaldo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCarga {

    @SerializedName("nucliente")
    @Expose
    private String nucliente;
    @SerializedName("nutarjeta")
    @Expose
    private String nutarjeta;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestCarga() {
    }

    /**
     * 
     * @param nutarjeta
     * @param nucliente
     */
    public RequestCarga(String nucliente, String nutarjeta) {
        super();
        this.nucliente = nucliente;
        this.nutarjeta = nutarjeta;
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

}
