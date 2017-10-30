package com.example.felipearango.appscope;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class NotificacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvTitulo, tvEmpresa, tvEstado;

    public NotificacionHolder(View itemView) {
        super(itemView);
        tvTitulo = (TextView)itemView.findViewById(R.id.tvTitulo);
        tvEmpresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
        tvEstado = (TextView)itemView.findViewById(R.id.tvEstado);
        itemView.setOnClickListener(this);
    }


    //////////////////////
    /////Acá iría para donde lo mandaría cuando de click
    /////////////////////////
    @Override
    public void onClick(View view) {

    }

    public void bindNotificacion(Notificacion notificacion){
        tvTitulo.setText(notificacion.getNombreTrabajo());
        tvEmpresa.setText(notificacion.getNombreEmpresa());
        tvEstado.setText(notificacion.getEstado());
    }
}
