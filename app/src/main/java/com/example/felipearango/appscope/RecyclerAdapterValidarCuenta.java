package com.example.felipearango.appscope;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class RecyclerAdapterValidarCuenta extends RecyclerView.Adapter<ValidarCuentaHolder> {

    private ArrayList<ValidarCuenta> mNotificacion;

    @Override
    public ValidarCuentaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_validar, parent, false);
        return new ValidarCuentaHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ValidarCuentaHolder holder, int position) {
        ValidarCuenta validarCuenta = mNotificacion.get(position);
        holder.bindNotificacion(validarCuenta);
    }

    @Override
    public int getItemCount() {
        return mNotificacion.size();
    }
}
