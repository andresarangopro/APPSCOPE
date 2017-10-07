package com.example.felipearango.appscope;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Trabajo {

    private String titulo = "";
    private String descripción = "";
    private Object empresa;
    private ArrayList<String> et;

    public Trabajo(String titulo, String descripción, ArrayList<String> etiquetas, Object user) {
        this.titulo = titulo;
        this.descripción = descripción;
        this.empresa = user;
        et = etiquetas;
    }
}
