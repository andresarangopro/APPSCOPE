package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class ValidarCuenta {

    private String nombreEmpresa;
    private String fecha;

    public ValidarCuenta(String nombreEmpresa, String fecha) {
        this.nombreEmpresa = nombreEmpresa;
        this.fecha = fecha;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getFecha() {
        return fecha;
    }
}
