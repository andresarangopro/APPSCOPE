package com.example.felipearango.appscope;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Etiqueta {

    public String nombreEtiqueta;
    private  String idEmpresa;
    private String idTrabajo;

    public Etiqueta(String nombreEtiqueta, String idEmpresa, String idTrabajo) {
        this.nombreEtiqueta = nombreEtiqueta;
        this.idEmpresa = idEmpresa;
        this.idTrabajo = idTrabajo;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }
}