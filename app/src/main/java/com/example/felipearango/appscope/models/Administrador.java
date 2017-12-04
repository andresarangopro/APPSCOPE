package com.example.felipearango.appscope.models;

/**
 * Created by Felipe Arango on 29/11/2017.
 */

public class Administrador {

    /////////////////////////
    //Variables
    ////////////////////////

    private String nombre;
    private String correo;
    private String pass;
    private int tipoUser;


    /////////////////////////
    //Constructor
    ////////////////////////

    public Administrador(String nombre, String correo, String pass, int tipoUser) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.tipoUser = tipoUser;
    }



    public Administrador(){}

    //////////////////////
    //Getter and setters
    /////////////////////


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getPass() {
        return pass;
    }
}
