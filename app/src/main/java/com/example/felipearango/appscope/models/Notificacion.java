package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class Notificacion {

    ////////////////////////////////
    ////////Variables
    /////////////////////////////////
    private final String nombreTrabajo;
    private final String idEmpresa;
    private final String nombreEmpresa;
    private final String idJob;
    private final long estado;
    private  long estadoCalif;
    /////////////////////////////////
    /////Constructor
    //////////////////////////////////

    public Notificacion(String nombreTrabajo, String idEmpresa, String nombreEmpresa,String idJob, long estado, long estadoCalif) {
        this.nombreTrabajo = nombreTrabajo;
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.idJob = idJob;
        this.estado = estado;
        this.estadoCalif =estadoCalif;
    }


    //////////////////////////////////
    /////////Getters
    //////////////////////////////////


    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getIdJob() {
        return idJob;
    }

    public long getEstado() {
        return estado;
    }

    public long getEstadoCalif() {
        return estadoCalif;
    }

    public void setEstadoCalif(long estado){
        this.estadoCalif = estado;
    }
}
