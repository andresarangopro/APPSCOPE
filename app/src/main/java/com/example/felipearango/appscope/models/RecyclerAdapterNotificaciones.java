package com.example.felipearango.appscope.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.activities.Activity_Notificaciones_S;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.notificacion_oferta_aceptada;
import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class RecyclerAdapterNotificaciones extends RecyclerView.Adapter<RecyclerAdapterNotificaciones.NotificacionHolder> {

    private ArrayList<Notificacion> mNotificacion;
    private Context mContext;


    public RecyclerAdapterNotificaciones(Context context, ArrayList<Notificacion> mNotificacion) {
        this.mNotificacion = mNotificacion;
        mContext = context;
    }

    public static class NotificacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitulo, tvEmpresa, tvEstado;

        public NotificacionHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView)itemView.findViewById(R.id.tvEmpresa);
            tvEmpresa = (TextView)itemView.findViewById(R.id.tvTrabajo);
            tvEstado = (TextView)itemView.findViewById(R.id.tvEstado);
            if(TIPO_USUARIO == 1){
                itemView.setOnClickListener(this);
            }

        }


        //////////////////////
        /////Acá iría para donde lo mandaría cuando de click
        /////////////////////////
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent=new Intent(new Intent(context,Activity_Notificaciones_S.class));
            Bundle bundle=new Bundle();
            bundle.putString("idJob", tvEmpresa.getText().toString());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @Override
    public NotificacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_notificacion, parent, false);
        return new NotificacionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NotificacionHolder holder, int position) {
        Notificacion notificacion = mNotificacion.get(position);
        if(TIPO_USUARIO == usuario_empresa){
            holder.tvTitulo.setText(notificacion.getNombreTrabajo());
            holder.tvEmpresa.setText(notificacion.getIdTrabajo());
            holder.tvEstado.setText("Aspirantes: "+notificacion.getNumOfertantes());
        }else if(TIPO_USUARIO == usuario_corriente){
            holder.tvTitulo.setText(notificacion.getNombreTrabajo());
            holder.tvTitulo.setTextColor((Color.parseColor("#017580")));
            holder.tvEmpresa.setText(notificacion.getNombreEmpresa());
            holder.tvEmpresa.setTextColor((Color.parseColor("#000000")));
            holder.tvEstado.setText("Resultado : "+ (notificacion.getNumOfertantes() == notificacion_oferta_aceptada ? "ACEPTADA":"RECHAZADA"));
        }

    }

    @Override
    public int getItemCount() {
        return mNotificacion.size();
    }
}