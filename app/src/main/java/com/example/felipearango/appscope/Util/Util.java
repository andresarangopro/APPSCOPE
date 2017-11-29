package com.example.felipearango.appscope.Util;

import android.widget.EditText;

/**
 * Created by Felipe Arango on 11/11/2017.
 */

public class Util {

    public static final int usuario_corriente = 0;
    public static final int usuario_empresa= 1;
    public static final int usuario_administrador = 2;
    public static final int cuenta_espera_certif = 0;
    public static final int cuenta_no_certificada = 1;
    public static final int cuenta_certificada = 2;

    public static boolean emptyCampMSG(EditText txt, String msg){
        if(emptyWT(txt))txt.setError(msg);
        return emptyWT(txt);
    }

    public static boolean emptyWT(EditText txt){
        return txt.getText().toString().equals("");
    }

    public static String getTxt(EditText txt){
        return emptyWT(txt)? "":txt.getText().toString();
    }
}
