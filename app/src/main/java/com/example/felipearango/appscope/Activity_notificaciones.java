package com.example.felipearango.appscope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Activity_notificaciones extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones, null, false);
        mDrawer.addView(contentView, 0);

        ///////////////////////////////////////////
        ///////Acá van los métodos para agregar el recytcler con el recycler adapter  a el
        /////// linear layout de la interfaz
        //////////////////////////////////////////
    }
}
