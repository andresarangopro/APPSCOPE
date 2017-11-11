package com.example.felipearango.appscope.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.felipearango.appscope.R;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class ValidarCuentaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvFecha, tvEmpresa;
    private Button aprobar, noAprobar;
    private int idView;


    public ValidarCuentaHolder(View itemView) {
        super(itemView);
        idView = itemView.getId();
        itemView.setOnClickListener(this);
        tvEmpresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
        tvFecha = (TextView)itemView.findViewById(R.id.tvFecha);
        aprobar = (Button)itemView.findViewById(R.id.btnAprobar);
        aprobar.setOnClickListener(this);
        noAprobar = (Button)itemView.findViewById(R.id.btnNoAprobar);
        noAprobar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /////////////////////////
        //////Acá lo que debe hacer cada botón!
        ////////////////////////
        switch (view.getId()){
            case R.id.btnAprobar:{
                break;
            }
            case R.id.btnNoAprobar:{
                break;
            }
            default:{
                ///////////////////////
                // Acá descarga el NIT
                //////////////////////
            }
        }

    }

    public void bindNotificacion(ValidarCuenta vCuenta){
        tvEmpresa.setText(vCuenta.getNombreEmpresa());
        tvFecha.setText(vCuenta.getFecha());
    }
}
