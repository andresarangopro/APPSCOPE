package com.example.felipearango.appscope.models;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class UsuariosSolicitudEnEM {

    private String nombre;
    private String apellido;
    private String cedula;
    private String id;
    private String image;
    private String idJob;
    private String correo;
    private String frase;
    private String pdfHojaVida;

    public UsuariosSolicitudEnEM(String nombre, String apellido, String cedula, String id,
                                 String image, String idJob,String correo, String frase, String pdfHojaVida) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.id = id;
        this.image = image;
        this.idJob = idJob;
        this.correo = correo;
        this.pdfHojaVida = pdfHojaVida;
        this.frase = frase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPdfHojaVida() {
        return pdfHojaVida;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFrase() {
        return frase;
    }

    public String getCedula() {
        return cedula;
    }
}
