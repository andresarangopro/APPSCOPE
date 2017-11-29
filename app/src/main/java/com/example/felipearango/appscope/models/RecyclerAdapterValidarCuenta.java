package com.example.felipearango.appscope.models;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.ManejoUsers;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.cuenta_certificada;
import static com.example.felipearango.appscope.Util.Util.cuenta_no_certificada;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class RecyclerAdapterValidarCuenta extends RecyclerView.Adapter<RecyclerAdapterValidarCuenta.ValidarCuentaHolder> {

    private ArrayList<ValidarCuenta> mValidarCuenta;
    private ManejoUsers mn = new ManejoUsers();

    public static class ValidarCuentaHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        private TextView tvFecha, tvEmpresa;
        private Button aprobar, noAprobar;
        private int idView;

        public ValidarCuentaHolder(View v) {
            super(v);
            idView = itemView.getId();
            tvEmpresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
            tvFecha = (TextView)itemView.findViewById(R.id.tvFecha);
            aprobar = (Button)itemView.findViewById(R.id.btnAprobar);
            noAprobar = (Button)itemView.findViewById(R.id.btnNoAprobar);

        }
    }

    public RecyclerAdapterValidarCuenta(ArrayList<ValidarCuenta> mValidarCuenta) {
        this.mValidarCuenta = mValidarCuenta;
    }

    @Override
    public RecyclerAdapterValidarCuenta.ValidarCuentaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_validar, parent, false);
        return new ValidarCuentaHolder(inflatedView);
    }


    @Override
    public void onBindViewHolder(ValidarCuentaHolder holder, final int position) {
        ValidarCuenta validarCuenta = mValidarCuenta.get(position);
        bindNotificacion(holder,validarCuenta);
        holder.aprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("apr","APROBO");
                mn.inicializatedFireBase();
                mn.updateEstadoEmpresa(mValidarCuenta.get(position).getIdEmpresa(),"certificacion",cuenta_certificada);
            }
        });

        holder.noAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("apr","NO APROBO");
                mn.inicializatedFireBase();
                mn.updateEstadoEmpresa(mValidarCuenta.get(position).getIdEmpresa(),"certificacion",cuenta_no_certificada);
            }
        });
    }

    public void bindNotificacion(ValidarCuentaHolder holder, ValidarCuenta vCuenta){
        holder.tvEmpresa.setText(vCuenta.getNombreEmpresa());
        holder.tvFecha.setText(vCuenta.getFecha());
    }


    @Override
    public int getItemCount() {
        return mValidarCuenta.size();
    }
}
