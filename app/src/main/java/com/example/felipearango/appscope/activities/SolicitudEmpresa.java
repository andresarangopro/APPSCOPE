package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.EmpresaSolicitud;
import com.example.felipearango.appscope.models.Notificacion;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.RecyclerAdapterNotificaciones;

import java.util.ArrayList;

public class SolicitudEmpresa extends MainActivity {

    private RecyclerAdapterEmpresa mAdapter;
    private RecyclerView mRecyclerSolicitudes;
    private ArrayList<EmpresaSolicitud> solicitud = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_solicitud_empresa, null, false);
        mDrawer.addView(contentView, 0);

        ll = (LinearLayout) findViewById(R.id.llLayout);
        EmpresaSolicitud.setLl(ll);
        EmpresaSolicitud.setCtn(this);
        EmpresaSolicitud n1 = new EmpresaSolicitud("Nombre 1");
        EmpresaSolicitud n2 = new EmpresaSolicitud("Nombre 2");
        EmpresaSolicitud n3 = new EmpresaSolicitud("Nombre 3");
        solicitud.add(n1);
        solicitud.add(n2);
        solicitud.add(n3);

        iniciarRv();
    }

    private void iniciarRv(){
        mRecyclerSolicitudes = (RecyclerView) findViewById(R.id.rv_solicitudes);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerSolicitudes.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterEmpresa(solicitud);
        mRecyclerSolicitudes.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerSolicitudes.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerSolicitudes.addItemDecoration(dividerItemDecoration);
    }
}