package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class Notificacion {

    ////////////////////////////////
    ////////Variables
    /////////////////////////////////
    private final String nombreTrabajo;
    private final String nombreEmpresa;
    private final String estado;

    /////////////////////////////////
    /////Constructor
    //////////////////////////////////

    public Notificacion(String nombreTrabajo, String nombreEmpresa, String estado) {
        this.nombreTrabajo = nombreTrabajo;
        this.nombreEmpresa = nombreEmpresa;
        this.estado = estado;
    }

    //////////////////////////////////
    /////////Getters
    //////////////////////////////////

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getEstado() {
        return estado;
    }
}
