package com.example.felipearango.appscope;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Etiqueta {

    public String nombreEtiqueta;

    public Etiqueta(String nombreEtiqueta, String nombreT, String Desc) {
        this.nombreEtiqueta = nombreEtiqueta;
        Trabajo trabajo = new Trabajo(nombreT,Desc);
    }


    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }
}
