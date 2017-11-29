package com.example.felipearango.appscope.models;

/**
 * Created by Felipe Arango on 29/11/2017.
 */

public class Administrador {

    /////////////////////////
    //Variables
    ////////////////////////

    private String nombre;
    private String apellido;
    private String correo;
    private int tipoUser;

    /////////////////////////
    //Constructor
    ////////////////////////

    public Administrador(String nombre, String apellido, String correo, int tipoUser) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.tipoUser = tipoUser;
    }

    //////////////////////
    //Getter and setters
    /////////////////////

    public Administrador(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
}
