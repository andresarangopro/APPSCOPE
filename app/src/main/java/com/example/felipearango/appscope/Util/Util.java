package com.example.felipearango.appscope.Util;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.app.ActivityCompat.startActivityForResult;



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
    public static final int notificacion_oferta_enEspera = 0;
    public static final int notificacion_oferta_aceptada = 1;
    public static final int notificacion_oferta_rechazada = 2;

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


    public static String castMailToKey(String mail){
        String [] sd = mail.split("@");
        return sd[0];
    }

    public static  boolean validarCorreo(EditText et){
        String mail = getTxt(et);
        try {
            String[] cNMail = mail.split("@");
            if(!cNMail[0].equals("") && !cNMail[1].equals("")){
                String[] com = cNMail[1].split("\\.");
                if(cNMail[1].contains(".") && !com[1].equals("")){
                    return true;
                }
            }
        }catch (Exception e){

        }
        et.setError("Correo errado, no es un correo valido");
        return false;
    }

    public static String parametrizacionEtiqueta(String etiqueta){
        etiqueta = etiqueta.trim();
        String sSubCadena = etiqueta;
        String fisrtLetter = etiqueta;

        fisrtLetter = fisrtLetter.substring(0,1);
        fisrtLetter = fisrtLetter.toUpperCase();
        sSubCadena = sSubCadena.substring(1,sSubCadena.length());
        sSubCadena= sSubCadena.toLowerCase();

        return fisrtLetter+sSubCadena;
    }

    public static String arrayToString(ArrayList<String> list){
        if (list.size() <= 0)return "";
        String txt = "";
        for (int i = 0; i < list.size(); i++) {
            if((i+1) >= list.size()){
                txt +=list.get(i) ;
            }else{
                txt +=list.get(i)+",";
            }
        }
        return txt;
    }

}
