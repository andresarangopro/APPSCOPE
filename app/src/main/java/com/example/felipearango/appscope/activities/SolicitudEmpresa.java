package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.EmpresaSolicitud;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;

import java.util.ArrayList;

public class SolicitudEmpresa extends MainActivity {

    private RecyclerAdapterEmpresa mAdapter;
    private RecyclerView mRecyclerSolicitudes;
    /**
     * Este arraylist será de empresas!!
     */
    private ArrayList<Object> solicitud = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_solicitud_empresa, null, false);
        mDrawer.addView(contentView, 0);

        ll = (LinearLayout) findViewById(R.id.llLayout);

        iniciarRv();
    }

    private void iniciarRv(){
        mRecyclerSolicitudes = (RecyclerView) findViewById(R.id.rv_solicitudes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerSolicitudes.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterEmpresa(SolicitudEmpresa.this, ll , solicitud);
        mRecyclerSolicitudes.setAdapter(mAdapter);

    }
}
