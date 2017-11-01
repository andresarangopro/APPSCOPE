package com.example.felipearango.appscope;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class ValidarCuentaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvFecha, tvEmpresa;
    private Button aprobar, noAprobar;

    public ValidarCuentaHolder(View itemView) {
        super(itemView);
        tvEmpresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
        tvFecha = (TextView)itemView.findViewById(R.id.tvFecha);
        aprobar = (Button)itemView.findViewById(R.id.btnAprobar);
        aprobar = (Button)itemView.findViewById(R.id.btnNoAprobar);
    }

    @Override
    public void onClick(View view) {

    }

    public void bindNotificacion(ValidarCuenta vCuenta){
        tvEmpresa.setText(vCuenta.getNombreEmpresa());
        tvFecha.setText(vCuenta.getFecha());
    }
}
