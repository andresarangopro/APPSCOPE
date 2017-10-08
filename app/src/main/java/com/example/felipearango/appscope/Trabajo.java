package com.example.felipearango.appscope;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sebastian Luna R on 10/7/2017.
 */

public class Trabajo {

    private String titulo = "";
    private String descripción = "";
    private Object empresa;
    private ArrayList<String> et;

    public Trabajo(String titulo, String descripción) {
        this.titulo = titulo;
        this.descripción = descripción;
    }

    @Override
    public String toString() {
        return "Trabajo{" +
                "titulo='" + titulo + '\'' +
                ", descripción='" + descripción + '\'' +
                ", et=" + et +
                '}';
    }
}
