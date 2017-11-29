package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class ValidarCuenta {

    private String nombreEmpresa;
    private String fecha;
    private String idEmpresa;

    public ValidarCuenta(String nombreEmpresa, String fecha, String idEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.fecha = fecha;
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
