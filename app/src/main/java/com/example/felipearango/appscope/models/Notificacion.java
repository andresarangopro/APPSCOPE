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
    private final String idTrabajo;
    private final String idEmpresa;
    private final String idOfertante;
    private final String nombreOfertante;
    /////////////////////////////////
    /////Constructor
    //////////////////////////////////

    public Notificacion(String nombreTrabajo, String nombreEmpresa, String estado, String idTrabajo,
                        String idEmpresa, String idOfertante, String nombreOfertante) {
        this.nombreTrabajo = nombreTrabajo;
        this.nombreEmpresa = nombreEmpresa;
        this.estado = estado;
        this.idTrabajo = idTrabajo;
        this.idEmpresa = idEmpresa;
        this.idOfertante = idOfertante;
        this.nombreOfertante = nombreOfertante;
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

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public String getIdOfertante() {
        return idOfertante;
    }

    public String getNombreOfertante() {
        return nombreOfertante;
    }
}
