package com.example.felipearango.appscope.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.activities.Activity_Notificaciones_S;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class NotificacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvTitulo, tvEmpresa, tvEstado;

    public NotificacionHolder(View itemView) {
        super(itemView);
        tvTitulo = (TextView)itemView.findViewById(R.id.tvEmpresa);
        tvEmpresa = (TextView)itemView.findViewById(R.id.tvTrabajo);
        tvEstado = (TextView)itemView.findViewById(R.id.tvEstado);
        itemView.setOnClickListener(this);
    }


    //////////////////////
    /////Acá iría para donde lo mandaría cuando de click
    /////////////////////////
    @Override
    public void onClick(View view) {
        Context context = view.getContext();
       // context.startActivity(new Intent(context,Activity_Notificaciones_S.class));
        Intent  intent=new Intent(new Intent(context,Activity_Notificaciones_S.class));
        Bundle bundle=new Bundle();
        bundle.putString("idJob", tvEmpresa.getText().toString());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void bindNotificacion(Notificacion notificacion){
        tvTitulo.setText(notificacion.getNombreTrabajo());
        tvEmpresa.setText(notificacion.getIdTrabajo());
        tvEstado.setText("Ofertantes: "+notificacion.getNumOfertantes());
    }
}
