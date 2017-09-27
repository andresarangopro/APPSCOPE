package com.example.felipearango.appscope;

import java.util.ArrayList;

/**
 * Created by Felipe Arango on 23/09/2017.
 */

public class Empresa {


    //////////////////////////
    ///Variables de clase
    /////////////////////////
    private String razonSocial;
    private String urlEmpresa;
    private String mail;
    private String estadoCuenta;
    private ArrayList<String> redesSociales = new ArrayList<>();
    /*
    Es importante recalcar que para publicar ofertas de trabajo se requiere el lugar, una descripción del empleo
    y el término del contrato.
     */

    //////////////////////////////
    //////Constructor
    /////////////////////////////

    public Empresa(String razonSocial, String urlEmpresa, String mail, String estadoCuenta, ArrayList<String> redesSociales) {
        this.razonSocial = razonSocial;
        this.urlEmpresa = urlEmpresa;
        this.mail = mail;
        this.estadoCuenta = estadoCuenta;
        this.redesSociales = redesSociales;
    }

    ///////////////////////////
    ///////Getters and Setters
    ///////////////////////////


    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getUrlEmpresa() {
        return urlEmpresa;
    }

    public void setUrlEmpresa(String urlEmpresa) {
        this.urlEmpresa = urlEmpresa;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public ArrayList<String> getRedesSociales() {
        return redesSociales;
    }

    public void setRedesSociales(ArrayList<String> redesSociales) {
        this.redesSociales = redesSociales;
    }
}
