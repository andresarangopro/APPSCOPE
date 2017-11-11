package com.example.felipearango.appscope.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felipearango.appscope.R;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class RecyclerAdapterValidarCuenta extends RecyclerView.Adapter<ValidarCuentaHolder> {

    private ArrayList<ValidarCuenta> mValidarCuenta;

    public RecyclerAdapterValidarCuenta(ArrayList<ValidarCuenta> mValidarCuenta) {
        this.mValidarCuenta = mValidarCuenta;
    }

    @Override
    public ValidarCuentaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_validar, parent, false);
        return new ValidarCuentaHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ValidarCuentaHolder holder, int position) {
        ValidarCuenta validarCuenta = mValidarCuenta.get(position);
        holder.bindNotificacion(validarCuenta);
    }

    @Override
    public int getItemCount() {
        return mValidarCuenta.size();
    }
}
