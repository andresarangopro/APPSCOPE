package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class Notificacion {

    ////////////////////////////////
    ////////Variables
    /////////////////////////////////
    private final String nombreTrabajo;
    private final String idTrabajo;
    private final String nombreEmpresa;
    private final long numOfertantes;

    /////////////////////////////////
    /////Constructor
    //////////////////////////////////

    public Notificacion(String nombreTrabajo, String idTrabajo, String nombreEmpresa, long numOfertantes) {
        this.nombreTrabajo = nombreTrabajo;
        this.idTrabajo = idTrabajo;
        this.nombreEmpresa = nombreEmpresa;
        this.numOfertantes = numOfertantes;
    }


    //////////////////////////////////
    /////////Getters
    //////////////////////////////////


    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public long getNumOfertantes() {
        return numOfertantes;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
}
