package com.example.felipearango.appscope;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class RecyclerAdapterNotificaciones extends RecyclerView.Adapter<NotificacionHolder> {

    private ArrayList<Notificacion> mNotificacion;

    @Override
    public NotificacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_notificacion, parent, false);
        return new NotificacionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NotificacionHolder holder, int position) {
        Notificacion notificacion = mNotificacion.get(position);
        holder.bindNotificacion(notificacion);
    }

    @Override
    public int getItemCount() {
        return mNotificacion.size();
    }
}
