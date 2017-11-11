package com.example.felipearango.appscope.models;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Trabajo {

    private String id;
    private int aspirantes;
    private String titulo = "";
    private String descripción = "";
    private String localizacion;
    private String estado;
    private String idEmpresa;

    public Trabajo(String id, int aspirantes, String titulo, String descripción, String localizacion,
                   String estado, String idEmpresa) {
        this.id = id;
        this.aspirantes = aspirantes;
        this.titulo = titulo;
        this.descripción = descripción;
        this.localizacion = localizacion;
        this.estado = estado;
        this.idEmpresa = idEmpresa;
    }

    public Trabajo(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAspirantes() {
        return aspirantes;
    }

    public void setAspirantes(int aspirantes) {
        this.aspirantes = aspirantes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
