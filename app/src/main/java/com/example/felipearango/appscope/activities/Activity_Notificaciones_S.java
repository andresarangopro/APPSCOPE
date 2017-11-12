package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.EmpresaSolicitud;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Notificaciones_S extends MainActivity {

    private RecyclerAdapterEmpresa mAdapterEmp;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<EmpresaSolicitud> notificaciones = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<String> idWorkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones_siguiente, null, false);
        mDrawer.addView(contentView, 0);

        iniciar();
    }

    private void iniciar(){
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Noti);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapterEmp = new RecyclerAdapterEmpresa(notificaciones);

        mRecyclerAccounts.setAdapter(mAdapterEmp);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
        Bundle b = getIntent().getExtras();
        String index = b.getString("idJob");
        startnotifiOferts(index);
    }
    private void startnotifiOferts(final String strIdJob){
        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ofertChild: dataSnapshot.child(strIdJob).child("Ofertas").getChildren()) {
                    idWorkers.add(ofertChild.getKey());
                }
                llenarRecycler();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void llenarRecycler() {
        databaseReference.child("CorrientsUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String idOfert : idWorkers) {
                    UsuarioCorriente us = dataSnapshot.child(idOfert).getValue(UsuarioCorriente.class);
                    EmpresaSolicitud emp = new EmpresaSolicitud(us.getNombre());
                    notificaciones.add(emp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
