package com.example.felipearango.appscope.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felipearango.appscope.R;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class RecyclerAdapterEmpresa extends RecyclerView.Adapter<SolicitudAEmpresaHolder> {

    private ArrayList<EmpresaSolicitud> mEmpresaSolicitud;

    public RecyclerAdapterEmpresa(ArrayList<EmpresaSolicitud> mEmpresaSolicitud) {
        this.mEmpresaSolicitud = mEmpresaSolicitud;
    }

    @Override
    public SolicitudAEmpresaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.botones_rv, parent, false);
        return new SolicitudAEmpresaHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SolicitudAEmpresaHolder holder, int position) {
        EmpresaSolicitud empresaSolicitud = mEmpresaSolicitud.get(position);
        holder.bindNotificacion(empresaSolicitud);
    }

    @Override
    public int getItemCount() {
        return mEmpresaSolicitud.size();
    }

}
