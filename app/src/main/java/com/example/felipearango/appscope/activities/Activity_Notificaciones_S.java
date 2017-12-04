package com.example.felipearango.appscope.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.UsuariosSolicitudEnEM;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.felipearango.appscope.Util.Util.notificacion_oferta_enEspera;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_Notificaciones_S extends MainActivity {

    private RecyclerAdapterEmpresa mAdapterEmp;
    private RecyclerView mRecyclerAccounts;

    private ArrayList<UsuariosSolicitudEnEM> notificaciones = new ArrayList<>();
    private Context context;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout ll;
    private ArrayList<String> idWorkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones_siguiente, null, false);
        mDrawer.addView(contentView, 0);
        ll = (LinearLayout)findViewById(R.id.llLayout);
        iniciar();

    }

    private void iniciar(){
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Noti);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapterEmp = new RecyclerAdapterEmpresa(context, ll, notificaciones);

        context = this;
        mRecyclerAccounts.setAdapter(mAdapterEmp);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
        Bundle b = getIntent().getExtras();
        String index = b.getString("idJob");
        startnotifiOferts(index);
    }
    private void startnotifiOferts(final String strIdJob){
        databaseReference.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[]addList = new String[2];
                for (DataSnapshot ofertChild: dataSnapshot.child(strIdJob).child("Ofertas").getChildren()) {
                    int estado = Integer.parseInt(ofertChild.child("Estado").getValue().toString());
                    if(estado == notificacion_oferta_enEspera){

                        idWorkers.add(ofertChild.getKey());
                    }
                }
                llenarRecycler(strIdJob);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void llenarRecycler(final String strIdJob) {
        databaseReference.child("CorrientsUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String idOfert : idWorkers) {
                    UsuarioCorriente us = dataSnapshot.child(idOfert).getValue(UsuarioCorriente.class);
                    UsuariosSolicitudEnEM emp = new UsuariosSolicitudEnEM(us.getNombre(),us.getApellido(),
                            us.getCelular(),us.getId(),us.getFoto(),strIdJob, us.getAnexos());
                    notificaciones.add(emp);
                }
                mAdapterEmp = new RecyclerAdapterEmpresa(context, ll, notificaciones);
                mRecyclerAccounts.setAdapter(mAdapterEmp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
