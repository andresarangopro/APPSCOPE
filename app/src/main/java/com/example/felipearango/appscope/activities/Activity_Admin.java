package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.RecyclerAdapterValidarCuenta;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.example.felipearango.appscope.models.ValidarCuenta;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.cuenta_espera_certif;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_Admin extends MainActivity {

    private RecyclerAdapterValidarCuenta mAdapter;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<Empresa> mValidarCuenta = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_admin, null, false);
        mDrawer.addView(contentView, 0);
        listENoCertificadas();
        iniciar();
    }

    private void iniciar(){
        ll = (LinearLayout)findViewById(R.id.llActivity);
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Empresas);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterValidarCuenta(ll,this,mValidarCuenta);
        mRecyclerAccounts.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
    }

    public void listENoCertificadas(){
        databaseReference.child("CorrientsUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objetos : dataSnapshot.getChildren()) {
                    int tipoUser = dataSnapshot.child(objetos.getKey()).getValue(UsuarioCorriente.class).getTipoUser();
                    if(tipoUser == usuario_empresa){
                        Empresa empresa = objetos.getValue(Empresa.class);
                        if(empresa.getCertificacion() == cuenta_espera_certif){
                            mValidarCuenta.add(empresa);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
