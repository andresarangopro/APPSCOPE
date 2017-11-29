package com.example.felipearango.appscope.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Felipe Arango on 23/09/2017.
 */

public class Empresa {


    //////////////////////////
    ///Variables de clase
    /////////////////////////
    private String id;
    private String nombre;
    private String razonSocial;
    private String urlEmpresa;
    private String nit;
    private String mail;
    private String estadoCuenta;
    private String foto;
    private float rating;
    private int tipoUser;
    private int certificacion;
    private ArrayList<String> redesSociales = new ArrayList<>();
    /*
    Es importante recalcar que para publicar ofertas de trabajo se requiere el lugar, una descripción del empleo
    y el término del contrato.
     */

    //////////////////////////////
    //////Constructor
    /////////////////////////////


    public Empresa(String id, String nombre, String razonSocial, String urlEmpresa, String nit,
                   String mail, String estadoCuenta, String foto, float rating, int
                           tipoUser, int certificacion, String redesSociales) {
        this.id = id;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.urlEmpresa = urlEmpresa;
        this.nit = nit;
        this.mail = mail;
        this.estadoCuenta = estadoCuenta;
        this.foto = foto;
        this.rating = rating;
        this.tipoUser = tipoUser;
        this.certificacion = certificacion;
        this.redesSociales = new ArrayList<String>(Arrays.asList(redesSociales.split(" , ")));
    }

    public Empresa(){}

    ///////////////////////////
    ///////Getters and Setters
    ///////////////////////////


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ArrayList<String> getRedesSociales() {
        return redesSociales;
    }

    public void setRedesSociales(ArrayList<String> redesSociales) {
        this.redesSociales = redesSociales;
    }

    public int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(int certificacion) {
        this.certificacion = certificacion;
    }
}
