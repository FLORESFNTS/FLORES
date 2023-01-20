package com.tqlweb.tqlsaldo.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Estacion {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("Escarga")
    @Expose
    private String escarga;
    @SerializedName("Razon")
    @Expose
    private String razon;
    @SerializedName("Latitud")
    @Expose
    private String latitud;
    @SerializedName("Longitud")
    @Expose
    private String longitud;
    @SerializedName("LinkMaps")
    @Expose
    private String linkMaps;
    @SerializedName("Magna")
    @Expose
    private Boolean magna;
    @SerializedName("Premium")
    @Expose
    private Boolean premium;
    @SerializedName("Diesel")
    @Expose
    private Boolean diesel;
    @SerializedName("Domicilio_Real")
    @Expose
    private String domicilioReal;
    @SerializedName("Colonia")
    @Expose
    private String colonia;
    @SerializedName("Delegacion_Municipio")
    @Expose
    private String delegacionMunicipio;
    @SerializedName("CodigoPostal")
    @Expose
    private String codigoPostal;
    @SerializedName("Ciudad")
    @Expose
    private String ciudad;
    @SerializedName("Telefono")
    @Expose
    private String telefono;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEscarga() {
        return escarga;
    }

    public void setEscarga(String escarga) {
        this.escarga = escarga;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLinkMaps() {
        return linkMaps;
    }

    public void setLinkMaps(String linkMaps) {
        this.linkMaps = linkMaps;
    }

    public Boolean getMagna() {
        return magna;
    }

    public void setMagna(Boolean magna) {
        this.magna = magna;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Boolean getDiesel() {
        return diesel;
    }

    public void setDiesel(Boolean diesel) {
        this.diesel = diesel;
    }

    public String getDomicilioReal() {
        return domicilioReal;
    }

    public void setDomicilioReal(String domicilioReal) {
        this.domicilioReal = domicilioReal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(String delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
