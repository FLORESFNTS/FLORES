
package com.tqlweb.tqlsaldo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestObtenerSaldo {

    @SerializedName("nucl")
    @Expose
    private String nucl;
    @SerializedName("nutj")
    @Expose
    private String nutj;
    @SerializedName("NIP")
    @Expose
    private String nIP;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestObtenerSaldo() {
    }

    /**
     * 
     * @param password
     * @param nucl
     * @param nIP
     * @param nutj
     * @param username
     */
    public RequestObtenerSaldo(String nucl, String nutj, String nIP, String password, String username) {
        super();
        this.nucl = nucl;
        this.nutj = nutj;
        this.nIP = nIP;
        this.password = password;
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
