package com.example.felipearango.appscope;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Etiquetas {

    private String nombreEtiqueta;
    private ArrayList<Trabajo> trabajo = new ArrayList<>();

    public Etiquetas(String nombreEtiqueta, Trabajo trabajo) {
        this.nombreEtiqueta = nombreEtiqueta;
        this.trabajo.add(trabajo);
    }
}
