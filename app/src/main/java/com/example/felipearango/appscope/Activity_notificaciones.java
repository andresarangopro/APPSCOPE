package com.example.felipearango.appscope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class Activity_notificaciones extends MainActivity {

    private RecyclerAdapterNotificaciones mAdapter;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<Notificacion> notificacion = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones, null, false);
        mDrawer.addView(contentView, 0);

        Notificacion noti = new Notificacion("Trabajo 1", "Empresa 1", "Validado");
        Notificacion noti2 = new Notificacion("Trabajo 2", "Empresa 2", "Validado");
        Notificacion noti3 = new Notificacion("Trabajo 3", "Empresa 3", "Validado");

        notificacion.add(noti);
        notificacion.add(noti2);
        notificacion.add(noti3);

        iniciar();
    }

    private void iniciar(){
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Noti);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterNotificaciones(notificacion);
        mRecyclerAccounts.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
    }
}
