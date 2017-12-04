package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Notificacion;
import com.example.felipearango.appscope.models.RecyclerAdapterNotificaciones;
import com.example.felipearango.appscope.models.Trabajo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_notificaciones extends MainActivity {

    private RecyclerAdapterNotificaciones mAdapter;

    private RecyclerView mRecyclerAccounts;
    private ArrayList<Notificacion> notificacion = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout ll;
    private ArrayList<String> idJobs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones, null, false);
        mDrawer.addView(contentView, 0);
        iniciar();
        inicializatedFireBase();
    }

    private void iniciar(){

        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Noti);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);

        mAdapter = new RecyclerAdapterNotificaciones(this,notificacion);
        mRecyclerAccounts.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
        if(TIPO_USUARIO == usuario_empresa){
            startArrayJobs(this);

        }else if(TIPO_USUARIO == usuario_corriente){
            notifiersUser();
        }

    }

    private void startArrayJobs(final Context context){
        databaseReference.child("Etiqueta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                    for (DataSnapshot etiquetasChild : dataSnapshot.getChildren()) {
                        for (DataSnapshot empresasChild : etiquetasChild.getChildren()) {
                            if (user.getUid().equals(empresasChild.getKey())) {
                                for (DataSnapshot jobsEtiqueta : empresasChild.getChildren()) {
                                    idJobs.add(jobsEtiqueta.getKey());
                                }
                            }
                        }
                    }
                    idJobs = quitarRepetidos(idJobs);
                    startnotifiOferts(context,idJobs);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startnotifiOferts(final Context context,final ArrayList<String> strIdJob){
        databaseReference.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trabajo tb = null;
                for (DataSnapshot jobsChild: dataSnapshot.getChildren()) {
                    for(String idOfJob: strIdJob){
                        if(idOfJob.equals(jobsChild.getKey())){
                            tb = jobsChild.getValue(Trabajo.class);
                            Notificacion noti = new Notificacion(tb.getTitulo(),
                                    tb.getId(),"", jobsChild.child("Ofertas").getChildrenCount());
                            notificacion.add(noti);
                        }
                    }

                }
                mAdapter = new RecyclerAdapterNotificaciones(context,notificacion);
                mRecyclerAccounts.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void notifiersUser(){
        databaseReference.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Trabajo tb = null;
                FirebaseUser user = firebaseAuth.getCurrentUser();
                for (DataSnapshot jobs : dataSnapshot.getChildren()) {
                   if(jobs.child("Ofertas") != null){
                       for (DataSnapshot  ofertantes : jobs.child("Ofertas").getChildren()) {
                           int estado = Integer.parseInt(ofertantes.child("Estado").getValue().toString());
                           if(ofertantes.getKey().equals(user.getUid()) && estado >= 1){
                               tb = jobs.getValue(Trabajo.class);
                               Notificacion noti = new Notificacion(tb.getTitulo(),
                                       tb.getIdEmpresa(), tb.getNameEmpresa(), estado);
                               notificacion.add(noti);
                           }
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
