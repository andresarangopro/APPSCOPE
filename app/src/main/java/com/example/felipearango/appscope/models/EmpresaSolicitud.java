package com.example.felipearango.appscope.models;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class EmpresaSolicitud {

    private String nombre;
    private static Context ctn;
    private static LinearLayout ll;

    public EmpresaSolicitud(String nombre) {
        this.nombre = nombre;
    }

    public static void setCtn(Context ctn) {
        EmpresaSolicitud.ctn = ctn;
    }

    public static void setLl(LinearLayout ll) {
        EmpresaSolicitud.ll = ll;
    }

    public static LinearLayout getLl() {
        return ll;
    }

    public static Context getCtn() {
        return ctn;
    }

    public String getNombre() {
        return nombre;
    }
}
