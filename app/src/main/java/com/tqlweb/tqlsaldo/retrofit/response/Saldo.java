
package com.tqlweb.tqlsaldo.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Saldo {

    @SerializedName("nucl")
    @Expose
    private String nucl;
    @SerializedName("nutj")
    @Expose
    private String nutj;
    @SerializedName("NIP")
    @Expose
    private String nIP;
    @SerializedName("saldo")
    @Expose
    private String saldo;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Saldo() {
    }

    /**
     * 
     * @param nucl
     * @param nIP
     * @param saldo
     * @param mensaje
     * @param nutj
     */
    public Saldo(String nucl, String nutj, String nIP, String saldo, String mensaje) {
        super();
        this.nucl = nucl;
        this.nutj = nutj;
        this.nIP = nIP;
        this.saldo = saldo;
        this.mensaje = mensaje;
    }

    public String getNucl() {
        return nucl;
    }

    public void setNucl(String nucl) {
        this.nucl = nucl;
    }

    public String getNutj() {
        return nutj;
    }

    public void setNutj(String nutj) {
        this.nutj = nutj;
    }

    public String getNIP() {
        return nIP;
    }

    public void setNIP(String nIP) {
        this.nIP = nIP;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
